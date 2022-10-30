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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("호출", String.valueOf(i));
        context = viewGroup.getContext();
        TweetView tweetView = items.get(i);
        ;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tweet_item, viewGroup, false);
        }
        ImageView profileImage = view.findViewById(R.id.tweet_profile_image);
        TextView userName = view.findViewById(R.id.tweet_user_name);
        TextView upTime = view.findViewById(R.id.tweet_uptime);
        TextView content = view.findViewById(R.id.tweet_content);
        ImageView media = view.findViewById(R.id.tweet_media);
        Glide.with(view).load(tweetView.getUserProfileUrl()).circleCrop().into(profileImage);//url를 이용하여 이미지 뷰에 이미지 세팅
        userName.setText(tweetView.getWriteUserName());
        upTime.setText(getTime(tweetView.getWriteDate()));
        content.setText(tweetView.getTweetContent());
        Glide.with(view).load(tweetView.getMediaUrl()).fitCenter().into(media);
        MaterialCardView quotedView = view.findViewById(R.id.quotedView);
        TweetView quoted = null;
        for (int j = 0; j < items.size(); j++) {
            if (items.get(j).getTweetId().equals(
                    tweetView.getNextTweetId()
            )) {
                quoted = items.get(j);
            }
        }
        ImageView quotedProfileImage = view.findViewById(R.id.tweet_quoted_profile_image);
        TextView quotedUserName = view.findViewById(R.id.tweet_quoted_user_name);
        TextView quotedUpTime = view.findViewById(R.id.tweet_quoted_uptime);
        TextView quotedContent = view.findViewById(R.id.tweet_quoted_content);
        ImageView quotedMedia = view.findViewById(R.id.tweet_quoted_media);
        if (quoted != null) {
            Glide.with(view).load(quoted.getUserProfileUrl()).circleCrop().into(quotedProfileImage);//url를 이용하여 이미지 뷰에 이미지 세팅
            quotedUserName.setText(quoted.getWriteUserName());
            quotedUpTime.setText(getTime(quoted.getWriteDate()));
            quotedContent.setText(quoted.getTweetContent());
            Glide.with(view).load(quoted.getMediaUrl()).fitCenter().into(quotedMedia);
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
