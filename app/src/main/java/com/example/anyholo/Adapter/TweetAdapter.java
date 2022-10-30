package com.example.anyholo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TweetAdapter extends BaseAdapter {
    ArrayList<TweetView> items = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItems(ArrayList<TweetView> list) {
        items = list;
    }

    public class ViewHolder {
        private RelativeLayout tweetMain;
        private ImageView profileImage;
        private TextView userName;
        private TextView upTime;
        private TextView content;
        private ImageView media;
        private MaterialCardView quotedView;
        private ImageView quotedProfileImage;
        private TextView quotedUserName;
        private TextView quotedUpTime;
        private TextView quotedContent;
        private ImageView quotedMedia;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        TweetView tweetView = items.get(i);
        View view = convertView;
        ViewHolder viewHolder;
        Log.d("호출 : ",String.valueOf(i));
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tweet_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tweetMain = view.findViewById(R.id.tweet_main);
            viewHolder.profileImage = view.findViewById(R.id.tweet_profile_image);
            viewHolder.userName = view.findViewById(R.id.tweet_user_name);
            viewHolder.upTime = view.findViewById(R.id.tweet_uptime);
            viewHolder.content = view.findViewById(R.id.tweet_content);
            viewHolder.media = view.findViewById(R.id.tweet_media);
            viewHolder.quotedView = view.findViewById(R.id.quotedView);
            viewHolder.quotedProfileImage = view.findViewById(R.id.tweet_quoted_profile_image);
            viewHolder.quotedUserName = view.findViewById(R.id.tweet_quoted_user_name);
            viewHolder.quotedUpTime = view.findViewById(R.id.tweet_quoted_uptime);
            viewHolder.quotedContent = view.findViewById(R.id.tweet_quoted_content);
            viewHolder.quotedMedia = view.findViewById(R.id.tweet_quoted_media);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(view).load(tweetView.getUserProfileUrl()).circleCrop().into(viewHolder.profileImage);//url를 이용하여 이미지 뷰에 이미지 세팅
        viewHolder.userName.setText(tweetView.getWriteUserName());
        viewHolder.upTime.setText(getTime(tweetView.getWriteDate()));
        viewHolder.content.setText(tweetView.getTweetContent());
        ((ViewGroup)viewHolder.tweetMain).removeView(viewHolder.quotedView);
        Glide.with(view).load(tweetView.getMediaUrl()).fitCenter().into(viewHolder.media);
        if(tweetView.getTweetType().equals("QUOTED")){
            ((ViewGroup)viewHolder.tweetMain).addView(viewHolder.quotedView);
            //Log.d("결과 : ",viewHolder.quotedView.toString());
            TweetView t = null;
            for(int j=0;j<items.size();j++){
                if(items.get(j).getTweetId().equals(tweetView.getNextTweetId())){
                    t=items.get(j);
                }
            }
            Glide.with(view).load(t.getUserProfileUrl()).circleCrop().into(viewHolder.quotedProfileImage);//url를 이용하여 이미지 뷰에 이미지 세팅
            viewHolder.quotedUserName.setText(t.getWriteUserName());
            viewHolder.quotedUpTime.setText(getTime(t.getWriteDate()));
            viewHolder.quotedContent.setText(t.getTweetContent());
            Glide.with(view).load(t.getMediaUrl()).fitCenter().into(viewHolder.quotedMedia);

        }else{
            viewHolder.quotedContent.setText("디폴트");
            viewHolder.quotedMedia.setImageDrawable(null);
        }
        return view;
    }

    public String getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Calendar uptime = Calendar.getInstance();
            uptime.setTime(sdf.parse(time));
            Calendar onedayafter = Calendar.getInstance();
            onedayafter.setTime(sdf.parse(time));
            onedayafter.add(Calendar.DAY_OF_MONTH, +1);
            Calendar now = Calendar.getInstance();
            //한국이라 9시간 더해줘야함
            now.add(Calendar.HOUR, 9);
            if (now.before(onedayafter)) { // 현재시간은 언제나 업로드 타임보다 앞이라 하루 뒤 시간을 넘었는지만 체크하면됨
                long diffSec = (now.getTimeInMillis() - uptime.getTimeInMillis()) / 1000;
                long diffHour = diffSec / (60 * 60);
                if (diffHour != 0) {
                    return diffHour + "시간 전";
                }
                long diffMinute = diffSec / (60);
                return diffMinute + "분 전";
            }
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            String result = sdf.format(d);
            result = result.substring(5, result.length());
            result = result.replace("-", "월 ");
            result += "일";
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
