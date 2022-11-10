package com.example.anyholo.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TweetView extends RelativeLayout {
    private RelativeLayout tweetMain;
    private ImageView profileImage;
    private TextView userName;
    private TextView upTime;
    private TextView content;
    private MaterialCardView mediaView;
    private LinearLayout[] mediaDetailLayout = new LinearLayout[2];
    private ImageView[] media = new ImageView[4];
    private final int marginValue=3;

    public TweetView(Context context) {
        super(context);
    }

    public TweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context,attrs);
    }

    public TweetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TweetView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tweet_item,this);
        tweetMain = findViewById(R.id.test_main);
        profileImage = findViewById(R.id.tweet_profile_image);
        userName = findViewById(R.id.tweet_user_name);
        upTime = findViewById(R.id.tweet_uptime);
        content = findViewById(R.id.tweet_content);
        mediaView = findViewById(R.id.tweet_media_view);
        media[0] = findViewById(R.id.tweet_media);
        media[1] = findViewById(R.id.tweet_media2);
        media[2] = findViewById(R.id.tweet_media3);
        media[3] = findViewById(R.id.tweet_media4);
        mediaDetailLayout[0] = findViewById(R.id.media_detail_layout1);
        mediaDetailLayout[1] = findViewById(R.id.media_detail_layout2);
        LayoutParams params = (LayoutParams) profileImage.getLayoutParams();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TestView);
        int size = typedArray.getInteger(R.styleable.TestView_profileImageSize,0);
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,getResources().getDisplayMetrics());
        params.height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,getResources().getDisplayMetrics());
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,typedArray.getInteger(R.styleable.TestView_profileImageMarginLeft,0),getResources().getDisplayMetrics());
        profileImage.setLayoutParams(params);
        params = (LayoutParams) mediaView.getLayoutParams();
        params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,typedArray.getInteger(R.styleable.TestView_mediaMarginRight,0),getResources().getDisplayMetrics());
        mediaView.setLayoutParams(params);
        params = (LayoutParams) upTime.getLayoutParams();
        int a = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,typedArray.getInteger(R.styleable.TestView_upTimeMarginRight,0),getResources().getDisplayMetrics());
        params.rightMargin=a;
        upTime.setLayoutParams(params);
    }
    public void setValues(com.example.anyholo.Model.TweetView tweetView){
        Glide.with(this).load(tweetView.getUserProfileUrl()).circleCrop().into(profileImage);
        userName.setText(tweetView.getWriteUserName());
        upTime.setText(getTime(tweetView.getWriteDate()));
        content.setText(tweetView.getTweetContent());
        ((ViewGroup) tweetMain).removeView(mediaView);
        ((ViewGroup) mediaDetailLayout[0].getParent()).removeView(mediaDetailLayout[1]);
        if(tweetView.getMediaUrl()!=null) {
            ((ViewGroup) tweetMain).addView(mediaView);
            String urls[] = tweetView.getMediaUrl().split(";");
            ((ViewGroup) mediaDetailLayout[0]).removeView(media[0]);
            ((ViewGroup) mediaDetailLayout[1]).removeView(media[1]);
            ((ViewGroup) mediaDetailLayout[1]).removeView(media[2]);
            ((ViewGroup) mediaDetailLayout[0]).removeView(media[3]);
            if(urls.length>1)
                ((ViewGroup) mediaDetailLayout[0].getParent()).addView(mediaDetailLayout[1]);
            //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());
            LinearLayout.LayoutParams imageParams;
            switch (urls.length){
                case 1:
                    Glide.with(getContext().getApplicationContext())
                            .asBitmap()
                            .load(urls[0])
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap bitmap,
                                                            Transition<? super Bitmap> transition) {
                                    int w = bitmap.getWidth();
                                    int h = bitmap.getHeight();
                                    if(w<=h) {
                                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mediaView.getLayoutParams();
                                        params.height= LayoutParams.WRAP_CONTENT;
                                        mediaView.setLayoutParams(params);
                                    }
                                }
                            });
                    ((ViewGroup) mediaDetailLayout[0]).addView(media[0]);
                    break;
                case 2:
                    imageParams = (LinearLayout.LayoutParams) media[0].getLayoutParams();
                    imageParams.rightMargin=marginValue;
                    media[0].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[1].getLayoutParams();
                    imageParams.leftMargin=marginValue;
                    media[1].setLayoutParams(imageParams);
                    ((ViewGroup) mediaDetailLayout[0]).addView(media[0]);
                    ((ViewGroup) mediaDetailLayout[1]).addView(media[1]);
                    break;
                case 3:
                    imageParams = (LinearLayout.LayoutParams) media[0].getLayoutParams();
                    imageParams.rightMargin=marginValue;
                    media[0].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[1].getLayoutParams();
                    imageParams.leftMargin=marginValue;
                    imageParams.bottomMargin=marginValue;
                    media[1].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[2].getLayoutParams();
                    imageParams.leftMargin=marginValue;
                    imageParams.topMargin=marginValue;
                    media[2].setLayoutParams(imageParams);
                    ((ViewGroup) mediaDetailLayout[0]).addView(media[0]);
                    ((ViewGroup) mediaDetailLayout[1]).addView(media[1]);
                    ((ViewGroup) mediaDetailLayout[1]).addView(media[2]);
                    //3개일 시 화면 구성
                    //1 2
                    //1 3
                    break;
                case 4:
                    imageParams = (LinearLayout.LayoutParams) media[0].getLayoutParams();
                    imageParams.rightMargin=marginValue;
                    imageParams.bottomMargin=marginValue;
                    media[0].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[1].getLayoutParams();
                    imageParams.leftMargin=marginValue;
                    imageParams.bottomMargin=marginValue;
                    media[1].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[2].getLayoutParams();
                    imageParams.rightMargin=marginValue;
                    imageParams.topMargin=marginValue;
                    media[2].setLayoutParams(imageParams);
                    imageParams = (LinearLayout.LayoutParams) media[2].getLayoutParams();
                    imageParams.leftMargin=marginValue;
                    imageParams.topMargin=marginValue;
                    media[3].setLayoutParams(imageParams);
                    ((ViewGroup) mediaDetailLayout[0]).addView(media[0]);//1
                    ((ViewGroup) mediaDetailLayout[1]).addView(media[1]);//2
                    ((ViewGroup) mediaDetailLayout[1]).addView(media[2]);//4
                    ((ViewGroup) mediaDetailLayout[0]).addView(media[3]);//3 일부러 순서 바꿈
                    //4개일 시 화면 구성
                    //1 2
                    //3 4
            }
            for (int i = 0; i < urls.length; i++) {
                Glide.with(this).load(urls[i]).fitCenter().into(media[i]);
            }
        }
    }
    private String getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Calendar uptime = Calendar.getInstance();
            uptime.setTime(sdf.parse(time));
            Calendar onedayafter = Calendar.getInstance();
            onedayafter.setTime(sdf.parse(time));
            onedayafter.add(Calendar.DAY_OF_MONTH, +1);
            Calendar now = Calendar.getInstance();
            //now.add(Calendar.HOUR, 9);
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
