package com.example.anyholo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anyholo.Model.TweetView;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

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
        private TestView nextTweet;
        private MaterialCardView cardView;
        private TextView retweetUser;
        private TextView retweetText;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        TweetView tweetView = items.get(i);
        ViewHolder viewHolder;
        Log.d("응 어쩔껀데", "어쩔:");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.testlayout, viewGroup, false);
        TestView mainTweet = view.findViewById(R.id.testLayout);
        TestView nextTweet = view.findViewById(R.id.quotedLayout);
        MaterialCardView cardView = view.findViewById(R.id.tquotedView);
        TextView retweetUser = view.findViewById(R.id.retest_user);
        TextView retweetText = view.findViewById(R.id.retest_text);
        TweetView quotedTweet = null;
        mainTweet.setValues(tweetView);
        ((ViewGroup) view).removeView(cardView);
        ((ViewGroup) view).removeView(retweetUser);
        ((ViewGroup) view).removeView(retweetText);
        if (tweetView.getTweetType().equals("QUOTED")) {
            mainTweet.setValues(tweetView);
            if (tweetView.getPrevTweet() != null)
                quotedTweet = tweetView.getPrevTweet();
            else
                for (TweetView t : items)
                    if (tweetView.getPrevTweetId().equals(t.getTweetId())) {
                        quotedTweet = t;
                        break;
                    }
        } else if (tweetView.getTweetType().equals("RETWEETED")) {
            ((ViewGroup) view).addView(retweetUser);
            ((ViewGroup) view).addView(retweetText);
            TweetView retweet = null;
            for (TweetView t : items) {
                if (tweetView.getPrevTweetId().equals(t.getTweetId()))
                    retweet = t;
            }
            if (retweet == null)
                retweet = tweetView.getPrevTweet();
            if (retweet.getTweetType().equals("QUOTED")) {
                if (retweet.getPrevTweetId() != null)
                    quotedTweet = retweet.getPrevTweet();
                else
                    for (TweetView t : items)
                        if (retweet.getPrevTweetId().equals(t.getTweetId())) {
                            quotedTweet = t;
                            break;
                        }
            }
            retweetUser.setText(tweetView.getWriteUserName());
            tweetView=retweet;
        }
        mainTweet.setValues(tweetView);
        if(quotedTweet!=null) {
            ((ViewGroup) view).addView(cardView);
            nextTweet.setValues(quotedTweet);
        }

        return view;
    }
}
