package com.example.anyholo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestAdapter extends BaseAdapter {
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
        private TestView mainTweet;
        private TestView quotedTweet;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        TweetView tweetView = items.get(i);
        View view = convertView;
        ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.testlayout, viewGroup, false);
        TestView testView = view.findViewById(R.id.testLayout);
        TestView testView2 = view.findViewById(R.id.quotedLayout);
        MaterialCardView cardView = view.findViewById(R.id.tquotedView);

        ((ViewGroup) view).removeView(cardView);
        if(tweetView.getTweetType().equals("QUOTED")) {
            ((ViewGroup) view).addView(cardView);
            testView2.setValues(tweetView.getNextTweet());
        }
        testView.setValues(tweetView);

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
