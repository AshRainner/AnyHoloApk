package com.anyholo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.anyholo.Model.MemberView;

public class MemberIntroActivity extends AppCompatActivity {
    private TextView translatedText;
    private TextView introText;
    private TextView nameText;
    private ImageView proFileImage;
    private ImageView liveThumbnails;
    private TextView liveTitle;
    private MemberView memberView;
    private ImageButton youtubeLogo;
    private ImageButton twitterLogo;
    private ImageButton hololiveLogo;
    private IntroPopupDialog introPopupDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_intro);
        introText = findViewById(R.id.itroText);
        nameText = findViewById(R.id.nameText);
        translatedText = findViewById(R.id.translatedText);
        proFileImage = findViewById(R.id.introImageView);
        translatedText.setMovementMethod(new ScrollingMovementMethod());
        liveThumbnails = findViewById(R.id.liveThumbnails);
        liveTitle = findViewById(R.id.liveTitle);
        Intent intent = getIntent();
        memberView = (MemberView) intent.getSerializableExtra("MemberView"); // 클릭한 멤버의 정보를 받아옴
        Glide.with(this).load(memberView.getProfileUrl()).into(proFileImage);
        String s=memberView.getEnName()+"Name";
        int identifier = getResources().getIdentifier(s,"string",getPackageName());
        nameText.setText(identifier);
        s = memberView.getEnName() + "Intro";
        identifier = getResources().getIdentifier(s,"string",getPackageName());
        introText.setText(identifier);
        s=memberView.getEnName()+"TranslatedText";
        identifier = getResources().getIdentifier(s,"string",getPackageName()); // 리소스에서 가져옴
        translatedText.setText(identifier);
        if(memberView.getOnAir().equals("live")||memberView.getOnAir().equals("upcoming")) { // 생방송 및 방송예정이라면 방송중임을 색을 통해 알려주고 색을 변곃해줌
            liveTitle.setText(memberView.getOnairTitle());
            if(memberView.getOnAir().equals("live"))
                liveTitle.setBackgroundColor(Color.parseColor("#ff9aa4"));
            else
                liveTitle.setBackgroundColor(Color.parseColor("#bad1ff"));
            Glide.with(this).load(memberView.getOnAirThumnailsUrl()).into(liveThumbnails);
            liveThumbnails.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        liveThumbnails.setOnClickListener(new View.OnClickListener() { // 썸네일 이미지 있는 부분 만약에 클릭한 멤버가 live이면 이미지 클릭시 방송중인 화면으로 이동
            @Override
            public void onClick(View view) {
                if(memberView.getOnAir().equals("live")||memberView.getOnAir().equals("upcoming"))
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(memberView.getOnAirVideoUrl())));
                else{
                    return ;
                }
            }
        });
        youtubeLogo = findViewById(R.id.youtubeLogo);
        youtubeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//멤버의 채널로 이동
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://youtube.com/channel/"+memberView.getChannelId())));
            }
        });
        twitterLogo = findViewById(R.id.twitterLogo);
        twitterLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//멤버의 트위터로 이동
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/"+memberView.getTwitterurl())));
            }
        });
        hololiveLogo = findViewById(R.id.hololiveLogo);
        hololiveLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //멤버의 홀로라이브 소개문으로 이동
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://hololive.hololivepro.com/talents/"+memberView.getHololiveUrl())));
            }
        });
        introPopupDialog = new IntroPopupDialog(this,translatedText.getText().toString());
        translatedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introPopupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                introPopupDialog.show();
            }
        });
    }
}
