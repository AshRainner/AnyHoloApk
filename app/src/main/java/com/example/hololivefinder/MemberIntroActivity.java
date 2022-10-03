package com.example.hololivefinder;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.hololivefinder.MemberModel.MemberView;

public class MemberIntroActivity extends AppCompatActivity {
    private TextView introduceText;
    private TextView translatedText;
    private TextView introMemberName;
    private TextView shortIntroText;
    private ImageView proFileImage;
    private ImageView liveThumbnails;
    private TextView liveTitle;
    private MemberView memberView;
    private ImageButton youtubeLogo;
    private ImageButton twitterLogo;
    private ImageButton hololiveLogo;
    private String[] memberEnName = {//member들의 영어 이름
            "Sora","Roboco","AZKi","Miko","Suisei",//0기생
            "Mel","Rogental","Haato","Fubuki","Matsuri",//1기생
            "Aqua","Shion","Ayame","Choco","Subaru",//2기생
            "Mio","Okayu","Korone",//게이머즈
            "Pekora","Flare","Noel","Marine",//3기생
            "Kanata","Watame","Towa","Luna",//4기생
            "Lamy","Nene","Botan","Polka",//5기생
            "Laplus","Lui","Koyori","Chloe","Iroha",//6기생
            "Calliope","Kiara","Inanis","Gura","Amelia",//EN1기생
            "IRyS",//EN 프로젝트 HOPE
            "Sana","Fauna","Kronii","Mumei","Baelz",//EN2기생
            "Risu","Moona","Iofifteen",//ID1기생
            "Ollie","Anya","Reine",//ID2기생
            "Zeta","Kaela","Kobo"//ID3기생
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_intro);
        shortIntroText = findViewById(R.id.shortIntroText);
        introduceText = findViewById(R.id.introduseText);
        translatedText = findViewById(R.id.translatedText);
        proFileImage = findViewById(R.id.introImageView);
        introduceText.setMovementMethod(new ScrollingMovementMethod());
        translatedText.setMovementMethod(new ScrollingMovementMethod());
        introMemberName = findViewById(R.id.introMemberName);
        liveThumbnails = findViewById(R.id.liveThumbnails);
        liveTitle = findViewById(R.id.liveTitle);
        Intent intent = getIntent();
        memberView = (MemberView) intent.getSerializableExtra("MemberView"); // 클릭한 멤버의 정보를 받아옴
        introduceText.setText(memberView.getIntroduceText());
        introMemberName.setText(memberView.getMemberName());
        Glide.with(this).load(memberView.getImageUrl()).into(proFileImage);
        String s=memberEnName[memberView.getNum()]+"Intro";
        int identifier = getResources().getIdentifier(s,"string",getPackageName());
        shortIntroText.setText(identifier);
        s=memberEnName[memberView.getNum()]+"TranslatedText";
        identifier = getResources().getIdentifier(s,"string",getPackageName()); // 리소스에서 가져옴
        translatedText.setText(identifier);
        if(memberView.getOnAir().equals("live")) { // 생방송중이라면 방송중임을 색을 통해 알려주고 색을 변곃해줌
            liveTitle.setText(memberView.getOnairTitle());
            liveTitle.setBackgroundColor(Color.parseColor("#ff9aa4"));
            Glide.with(this).load(memberView.getOnAirThumnailsUrl()).into(liveThumbnails);
            liveThumbnails.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        liveThumbnails.setOnClickListener(new View.OnClickListener() { // 썸네일 이미지 있는 부분 만약에 클릭한 멤버가 live이면 이미지 클릭시 방송중인 화면으로 이동
            @Override
            public void onClick(View view) {
                if(memberView.getOnAir().equals("live"))
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(memberView.getOnAirViedoUrl())));
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
                Log.d("hololiveUrl",memberView.getHololiveUrl());
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://hololive.hololivepro.com/talents/"+memberView.getHololiveUrl())));
            }
        });
    }
}
