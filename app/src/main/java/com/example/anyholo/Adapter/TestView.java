package com.example.anyholo.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

public class TestView extends RelativeLayout {
    private RelativeLayout tweetMain;
    private TextView retweetText;
    private ImageView profileImage;
    private TextView userName;
    private TextView upTime;
    private TextView content;
    private MaterialCardView mediaView;
    private ImageView[] media = new ImageView[4];
    public TestView(Context context) {
        super(context);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.test_item,this);
        tweetMain = findViewById(R.id.test_main);
        retweetText = findViewById(R.id.retest_text);
        profileImage = findViewById(R.id.test_profile_image);
        userName = findViewById(R.id.test_user_name);
        upTime = findViewById(R.id.test_uptime);
        content = findViewById(R.id.test_content);
        mediaView = findViewById(R.id.test_media_view);
        media[0] = findViewById(R.id.test_media);
        media[1] = findViewById(R.id.test_media2);
        media[2] = findViewById(R.id.test_media3);
        media[3] = findViewById(R.id.test_media4);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TestView);
            retweetText.setText(a.getString(R.styleable.TestView_userName));
            Glide.with(this).load(a.getString(R.styleable.TestView_profileImageUrl)).circleCrop().into(profileImage);
            userName.setText(a.getString(R.styleable.TestView_userName));
            upTime.setText(a.getString(R.styleable.TestView_upTime));
            content.setText(a.getString(R.styleable.TestView_tweetContent));
            String urls[] = a.getString(R.styleable.TestView_mediaUrl).split(";");
            for(int i=0;i<urls.length;i++)
                Glide.with(this).load(urls[i]).circleCrop().into(media[i]);
            a.recycle(); // 이용이 끝났으면 recycle() 호출
        }
    }
    public void setValues(TweetView tweetView){
        retweetText.setText(tweetView.getRetweetText());
        Glide.with(this).load(tweetView.getUserProfileUrl()).circleCrop().into(profileImage);
        userName.setText(tweetView.getWriteUserName());
        upTime.setText(tweetView.getWriteDate());
        content.setText(tweetView.getTweetContent());
        ((ViewGroup) tweetMain).removeView(mediaView);
        if(tweetView.getMediaUrl()!=null) {
            ((ViewGroup) tweetMain).addView(mediaView);
            String urls[] = tweetView.getMediaUrl().split(";");
            for (int i = 0; i < urls.length; i++)
                Glide.with(this).load(urls[i]).circleCrop().into(media[i]);
        }
    }
}
