package com.anyholo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anyholo.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TweetAdapter extends BaseAdapter {
    ArrayList<com.anyholo.Model.TweetView> items = new ArrayList<>();
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

    public void setItems(ArrayList<com.anyholo.Model.TweetView> list) {
        items = list;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        com.anyholo.Model.TweetView tweetView = items.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tweet_layout, viewGroup, false);
        TweetView mainTweet = view.findViewById(R.id.main_tweet_view);
        TweetView nextTweet = view.findViewById(R.id.quoted_tweet_view);
        MaterialCardView cardView = view.findViewById(R.id.quotedLayout);
        TextView retweetUser = view.findViewById(R.id.retweet_user);
        com.anyholo.Model.TweetView quotedTweet = null;
        mainTweet.setValues(tweetView);
        ((ViewGroup) view).removeView(cardView);
        ((ViewGroup) view).removeView(retweetUser);
        if (tweetView.getTweetType().equals("QUOTED")) {
            mainTweet.setValues(tweetView);
            if (tweetView.getPrevTweet() != null)
                quotedTweet = tweetView.getPrevTweet();
            else
                for (com.anyholo.Model.TweetView t : items)
                    if (tweetView.getPrevTweetId().equals(t.getTweetId())) {
                        quotedTweet = t;
                        break;
                    }
        } else if (tweetView.getTweetType().equals("RETWEETED")) {
            ((ViewGroup) view).addView(retweetUser);
            com.anyholo.Model.TweetView retweet = null;
            for (com.anyholo.Model.TweetView t : items) {
                if (tweetView.getPrevTweetId().equals(t.getTweetId()))
                    retweet = t;
            }
            if (retweet == null)
                retweet = tweetView.getPrevTweet();
            if (retweet != null) {
                if (retweet.getTweetType().equals("QUOTED")) {
                    if (retweet.getPrevTweetId() != null)
                        quotedTweet = retweet.getPrevTweet();
                    else
                        for (com.anyholo.Model.TweetView t : items)
                            if (retweet.getPrevTweetId().equals(t.getTweetId())) {
                                quotedTweet = t;
                                break;
                            }
                }
                retweetUser.setText(tweetView.getWriteUserName()+"님이 리트윗 하였습니다.");
                tweetView = retweet;
            }
        }
        mainTweet.setValues(tweetView);
        if (quotedTweet != null) {
            ((ViewGroup) view).addView(cardView);
            nextTweet.setValues(quotedTweet);
        }
        return view;
    }
}
