package com.example.hololivefinder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hololivefinder.Adapter.CustomViewPagerAdapter;
import com.example.hololivefinder.MemberModel.MemberModel;
import com.example.hololivefinder.MemberModel.MemberView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    /*
    이 어플리케이션의 제작권은 이건이 가지고 있습니다.
    무단 전제 및 도용 시 저작권 법에 의해 처벌 받을 수 있습니다.
    제작자 : 인하공업 전문 대학 2학년 이건
     */
    //private final String API_KEY = "AIzaSyADYWApy4Z5VvjrCc2j5BCtRX2VjX2zIvs"; // 발급받은 API 키값
    /*private String[] channelId = { // 멤버들의 유튜브 채널 ID
            "UCp6993wxpyDPHUpavwDFqgg","UCDqI2jOz0weumE8s7paEk6g","UC0TXe_LYZ4scaW2XMyi5_kw","UC-hM6YJuNYVAmUWxeIr9FeA","UC5CwaMl1eIgY8h02uZw7u8A",//0기생
            "UCD8HOxPs4Xvsm8H0ZxXGiBw","UCFTLzh12_nrtzqBPsTCqenA","UC1CfXB_kRs3C-zaeTG3oGyg","UCdn5BQ06XqgXoAxIhbqw5Rg","UCQ0UDLQCjY0rmuxCDE38FGg",//1기생
            "UC1opHUrw8rvnsadT-iGp7Cg","UCXTpFs_3PqI41qX2d9tL2Rw","UC7fk0CB07ly8oSl0aqKkqFg","UC1suqwovbL1kzsoaZgFZLKg","UCvzGlP9oQwU--Y0r9id_jnA",//2기생
            "UCp-5t9SrOQwXMU7iIjQfARg","UCvaTdHTWBGv3MKj3KVqJVCw","UChAnqc_AY5_I3Px5dig3X1Q",//게이머즈
            "UC1DCedRgGHBdm81E1llLhOQ","UCvInZx9h3jC2JzsIzoOebWg","UCdyqAaZDKHXg4Ahi7VENThQ","UCCzUftO8KOVkV4wQG1vkUvg",//3기생
            "UCZlDXzGoo7d44bwdNObFacg","UCqm3BQLlJfvkTsX_hvm0UmA","UC1uv2Oq6kNxgATlCiez59hw","UCa9Y57gfeY0Zro_noHRVrnw",//4기생
            "UCFKOVgVbGmX65RxO3EtH3iw","UCAWSyEs_Io8MtpY3m-zqILA","UCUKD-uaobj9jiqB-VXt71mA","UCK9V2B22uJYu3N7eR_BT9QA",//5기생
            "UCENwRMx5Yh42zWpzURebzTw","UCs9_O1tRPMQTHQ-N_L6FU2g","UC6eWCld0KwmyHFbAqK3V-Rw","UCIBY1ollUsauvVi4hW4cumw","UC_vMYWcDjmfdpH6r4TTn1MQ",//6기생
            "UCL_qhgtOy0dy1Agp8vkySQg","UCHsx4Hqa-1ORjQTh9TYDhww","UCMwGHR0BTZuLsmjY_NT5Pwg","UCoSrY_IQQVpmIRZ9Xf-y93g","UCyl1z3jo3XHR1riLFKG5UAg",//EN1기생
            "UC8rcEBzJSleTkf_-agPM20g",//EN 프로젝트 HOPE
            "UCsUj0dszADCGbF3gNrQEuSQ","UCO_aKKYxn4tvrqPjcTzZ6EQ","UCmbs8T6MWqUHP1tIQvSgKrg","UC3n5uGu18FoCy23ggWWp8tA","UCgmPnx-EEeOrZSg5Tiw7ZRQ",//EN2기
            "UCOyYb1c43VlX9rc_lT6NKQw","UCP0BspO_AMEe3aQqqpo89Dg","UCAoy6rzhSf4ydcYjJw3WoVg",//ID1기생
            "UCYz_5n-uDuChHtLo7My1HnQ","UC727SQYUvx5pDDGQpTICNWg","UChgTyjG-pdNvxxhdsXfHQ5Q",//ID2기생
            "UCTvHWSfBZgtxE4sILOaurIQ","UCZLZ8Jjx_RN2CXloOmgTHVg","UCjLEmnpCNeisMxy134KPwWw"//ID3기생
    };
    private String[] memberNameT={//멤버들의 채널 제목에 있는 이름
            "Sora","Roboco","AZKi","Miko","Suisei",//0기생
            "夜空","アキロゼ","HAACHAMA","フブキ","Matsuri",//1기생
            "Aqua","Shion","Ayame","Choco","Subaru",//2기생
            "Mio","Okayu","Korone",//게이머즈
            "Pekora","Flare","Noel","Marine",//3기생
            "Kanata","Watame","Towa","Luna",//4기생
            "Lamy","Nene","Botan","Polka",//5기생
            "Laplus","Lui","Koyori","Chloe","Iroha",//6기생
            "Calliope","Kiara","Ina'nis","Gura","Amelia",//EN1기생
            "IRyS",//EN 프로젝트 HOPE
            "Sana","Fauna","Kronii","Mumei","Baelz",//EN2기생
            "Risu","Moona","Iofifteen",//ID1기생
            "Ollie","Anya","Reine",//ID2기생
            "Zeta","Kaela","Kobo"//ID3기생
    };
    private String[] KRName={//멤버들의 한국식 이름
            "소라","로보코","아즈키","미코","스이세이",
            "멜","아키로젠탈","하아토","후부키","마츠리",
            "아쿠아","시온","아야메","쵸코","스바루",
            "미오","오카유","코로네",
            "페코라","후레아","노엘","마린",
            "카나타","와타메","토와","루나",
            "라미","네네","보탄","폴카",
            "라플라스","루이","코요리","클로에","이로하",
            "칼리오페","키아라","이나니스","구라","아멜리아",
            "아이리스",
            "사나","파우나","크로니","무메이","벨즈",
            "리스","무나","이오피프틴",
            "올리","아냐","레이네",
            "제타","카엘라","코보"
    };
    private String[] twitterUrl = {//각 멤버들의 트위터 아이디
            "tokino_sora","robocosan","AZKi_VDiVA","sakuramiko35","suisei_hosimati",
            "yozoramel","akirosenthal","akaihaato","shirakamifubuki","natsuiromatsuri",
            "minatoaqua","murasakishionch","nakiriayame","yuzukichococh","oozorasubaru",
            "ookamimio","nekomataokayu","inugamikorone",
            "usadapekora","shiranuiflare","shiroganenoel","houshoumarine",
            "amanekanatach","tsunomakiwatame","tokoyamitowa","himemoriluna",
            "yukihanalamy","momosuzunene","shishirobotan","omarupolka",
            "LaplusDarknesss","takanelui","hakuikoyori","sakamatachloe","kazamairohach",
            "moricalliope","takanashikiara","ninomaeinanis","gawrgura","watsonameliaEN",
            "irys_en",
            "tsukumosana","ceresfauna","ourokronii","nanashimumei_en","hakosbaelz",
            "ayunda_risu","moonahoshinova","airaniiofifteen",
            "kureijiollie","anyamelfissa","pavoliareine",
            "vestiazeta","kaelakovalskia","kobokanaeru"
    };
    private String[] hololiveUrl = {//멤버들의 홀로라이브 사이트 소개 url
            "tokino-sora","roboco-san","azki","sakuramiko","hoshimachi-suisei",
            "yozora-mel","aki-rosenthal","akai-haato","shirakami-fubuki","natsuiro-matsuri",
            "minato-aqua","murasaki-shion","nakiri-ayame","yuzuki-choco","oozora-subaru",
            "ookami-mio","nekomata-okayu","inugami-korone",
            "usada-pekora","shiranui-flare","shirogane-noel","houshou-marine",
            "amane-kanata","tsunomaki-watame","tokoyami-towa","himemori-luna",
            "yukihana-lamy","momosuzu-nene","shishiro-botan","omaru-polka",
            "la-darknesss","takane-lui","hakui-koyori","sakamata-chloe","kazama-iroha",
            "mori-calliope","takanashi-kiara","ninomae-inanis","gawr-gura","watson-amelia",
            "irys",
            "tsukumo-sana","ceres-fauna","ouro-kronii","nanashi-mumei","hakos-baelz",
            "ayunda-risu","moona-hoshinova","airani-iofifteen",
            "kureiji-ollie","anya-melfissa","pavolia-reine",
            "vestia-zeta","kaela-kovalskia","kobo-kanaeru"
    };*/
    /*private ArrayList<MemberView> list;//Grid 뷰에 띄워줄 MemberView를 가진 List
    private ArrayList<MemberView> noLiveList;//live하지 않는 멤버들만 모아서 정렬할 리스트
    private ArrayList<MemberView> liveList;//live하는 멤버들만 모아서 정렬할 리스트*/
    private SwipeRefreshLayout swipeRefreshLayout;
    /*private GridView gridView;
    private GridAdapter gridAdapter;*/
    private EditText search;
    private ImageButton menuBtn;
    private ViewPager2 viewPager;
    private LiveFragment liveFragment;
    private test_fragment testFragment;
    private CustomViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search_bar);
        menuBtn = findViewById(R.id.menu_btn);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        ArrayList<MemberView> list = (ArrayList<MemberView>) intent.getSerializableExtra("MemberList");
        bundle.putSerializable("MemberList",list);
        pagerAdapter = new CustomViewPagerAdapter(this);
        createFragment();
        liveFragment.setArguments(bundle);
        pagerAdapter.addFragment(liveFragment);
        pagerAdapter.addFragment(testFragment);
        viewPager.setAdapter(pagerAdapter);

        Spinner countrySpinner = findViewById(R.id.spinner);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("생방송");
                        break;
                    case 1:
                        tab.setText("트윗");
                        break;
                    case 2:
                        tab.setText("키리누키");
                }
            }
        }).attach();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country,//String Array 설정
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.country_spinner);

        countrySpinner.setAdapter(adapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//스피너 클릭해서 아이템 클릭하면 서치 메서드 실행
                //search(search.getText().toString(), countrySpinner.getItemAtPosition(i).toString());
                liveFragment.onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//menuBtn을 누르면 팝업 메뉴가 나오게 설정
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {//누른 아이템 번호에 따라 실행되게 함
                        if (menuItem.getItemId() == R.id.menu1)
                            Toast.makeText(MainActivity.this, "앱 버전 0.0.1", Toast.LENGTH_SHORT).show();
                        else if (menuItem.getItemId() == R.id.menu2)
                            Toast.makeText(MainActivity.this, "인하공전 201945046이건", Toast.LENGTH_SHORT).show();
                        else {
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/channel/UCOPaYsI-TnBk0qxoAy_rjXA")));
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {//검색창에 텍스트가 업데이트 될 때마다 작동
                //search(search.getText().toString(), countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());
                liveFragment.onResume();
            }
        });
    }
    private void createFragment(){
        liveFragment = new LiveFragment();
        Log.d("liveFragmet생성","!");
        testFragment = new test_fragment();
        Log.d("testFragmet생성","!");
    }
    /*private void search(String keyword, String country) {
        list.clear();
        if (keyword.length() == 0) {
            if (country.equals("전체")) {
                list.addAll(liveList);
                list.addAll(noLiveList);
            } else {
                for (int i = 0; i < liveList.size(); i++) {// 값에 따라 검색
                    if (liveList.get(i).getCountry().equals(country))
                        list.add(liveList.get(i));
                }
                for (int i = 0; i < noLiveList.size(); i++) {// 값에 따라 검색
                    if (noLiveList.get(i).getCountry().equals(country))
                        list.add(noLiveList.get(i));
                }
            }
        } else {
            for (int i = 0; i < liveList.size(); i++) {
                if (country.equals("전체")) {
                    if (liveList.get(i).getMemberName().contains(keyword)) {
                        list.add(liveList.get(i));
                    }
                } else {
                    if (liveList.get(i).getMemberName().contains(keyword) && liveList.get(i).getCountry().equals(country)) {
                        list.add(liveList.get(i));
                    }
                }
            }
            for (int i = 0; i < noLiveList.size(); i++) {
                if (country.equals("전체")) {
                    if (noLiveList.get(i).getMemberName().contains(keyword)) {
                        list.add(noLiveList.get(i));
                    }
                } else {
                    if (noLiveList.get(i).getMemberName().contains(keyword) && noLiveList.get(i).getCountry().equals(country)) {
                        list.add(noLiveList.get(i));
                    }
                }
            }
        }
        gridAdapter.notifyDataSetChanged();
    }*/
}