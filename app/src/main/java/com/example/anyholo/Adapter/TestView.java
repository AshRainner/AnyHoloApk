package com.example.anyholo.Adapter;

import static android.widget.GridLayout.LayoutParams.*;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestView extends RelativeLayout {
    private RelativeLayout tweetMain;
    private ImageView profileImage;
    private TextView userName;
    private TextView upTime;
    private TextView content;
    private MaterialCardView mediaView;
    private LinearLayout[] mediaDetailLayout = new LinearLayout[2];
    private ImageView[] media = new ImageView[4];
    private final int marginValue=3;

    public TestView(Context context) {
        super(context);
        Log.d("TestView context","");
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context,attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("TestView context attrs def","");
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d("TestView context attr def def","");
    }


    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.test_item,this);
        tweetMain = findViewById(R.id.test_main);
        profileImage = findViewById(R.id.test_profile_image);
        userName = findViewById(R.id.test_user_name);
        upTime = findViewById(R.id.test_uptime);
        content = findViewById(R.id.test_content);
        mediaView = findViewById(R.id.test_media_view);
        media[0] = findViewById(R.id.test_media);
        media[1] = findViewById(R.id.test_media2);
        media[2] = findViewById(R.id.test_media3);
        media[3] = findViewById(R.id.test_media4);
        mediaDetailLayout[0] = findViewById(R.id.tmedia_detail_layout1);
        mediaDetailLayout[1] = findViewById(R.id.tmedia_detail_layout2);
    }
    public void setValues(TweetView tweetView){
        Glide.with(this).load(tweetView.getUserProfileUrl()).circleCrop().into(profileImage);
        userName.setText(tweetView.getWriteUserName());
        upTime.setText(getTime(tweetView.getWriteDate()));
        content.setText(tweetView.getTweetContent());
        ((ViewGroup) tweetMain).removeView(mediaView);
        ((ViewGroup) mediaDetailLayout[0].getParent()).removeView(mediaDetailLayout[1]);
        if(tweetView.getMediaUrl()!=null) {
            ((ViewGroup) tweetMain).addView(mediaView);
            String urls[] = tweetView.getMediaUrl().split(";");
            LinearLayout.LayoutParams[] margin = new LinearLayout.LayoutParams[4];
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
                                    Log.d(String.valueOf(w),String.valueOf(h));
                                    if(w<=h) {
                                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mediaView.getLayoutParams();
                                        params.height= LayoutParams.WRAP_CONTENT;
                                        mediaView.setLayoutParams(params);
                                        Log.d(String.valueOf(params.height),String.valueOf(params.width));
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
                Log.d("url : ",urls[i]);
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
