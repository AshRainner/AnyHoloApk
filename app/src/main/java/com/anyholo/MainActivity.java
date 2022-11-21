package com.anyholo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.anyholo.Adapter.CustomViewPagerAdapter;
import com.anyholo.Model.KirinukiView;
import com.anyholo.Model.MemberView;
import com.anyholo.Model.TweetView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    /*
    이 어플리케이션의 제작권은 이건이 가지고 있습니다.
    무단 전제 및 도용 시 저작권 법에 의해 처벌 받을 수 있습니다.
    제작자 : 인하공업 전문 대학 2학년 이건
     */
    private String fileName = "Favorite.txt";
    private EditText search;
    private ViewPager2 viewPager;
    private LiveFragment liveFragment;
    private TweetFragment tweetFragment;
    private KirinukiFragment kirinukiFragment;
    private CustomViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private HashMap<String, Boolean> map;
    ArrayList<MemberView> memberlist;
    ArrayList<KirinukiView> kirinukiList;
    ArrayList<TweetView> tweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search_bar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);
        Intent intent = getIntent();
        memberlist = (ArrayList<MemberView>) intent.getSerializableExtra("MemberList");
        kirinukiList = (ArrayList<KirinukiView>) intent.getSerializableExtra("KirinukiList");
        map = (HashMap<String, Boolean>) intent.getSerializableExtra("Favorite");
        tweetList = (ArrayList<TweetView>) intent.getSerializableExtra("TweetList");
        pagerAdapter = new CustomViewPagerAdapter(this);
        createFragment();
        reduceDragSensitivity(6);//민감도 수정
        pagerAdapter.addFragment(liveFragment);
        pagerAdapter.addFragment(tweetFragment);
        pagerAdapter.addFragment(kirinukiFragment);
        viewPager.setOffscreenPageLimit(2);//이걸 넣어줘야 미리 로딩함
        viewPager.setAdapter(pagerAdapter);
        //Log.d("view Pager size",String.valueOf(pagerAdapter.getItemCount()));
        //viewPager.setCurrentItem(pagerAdapter.getItemCount());
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        Spinner countrySpinner = findViewById(R.id.spinner);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.country_spinner,getResources().getStringArray(R.array.country)){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position,convertView,parent);
                ((TextView)v).setTextColor(Color.parseColor("#000000"));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.country_spinner);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//스피너 클릭해서 아이템 클릭하면 서치 메서드 실행
                liveFragment.setCountry(countrySpinner.getItemAtPosition(i).toString());

                if (countrySpinner.getItemAtPosition(i).toString().equals("즐겨찾기")) {
                    liveFragment.setKeyword(search.getText().toString());
                    kirinukiFragment.setKeyword(getFavoriteMember().replace("미코","사쿠라").replace("라미","유키하나"));
                    tweetFragment.setKeyword(getFavoriteMember());
                }
                else{
                    liveFragment.setKeyword(search.getText().toString());
                    tweetFragment.setKeyword(search.getText().toString());
                    kirinukiFragment.setKeyword(search.getText().toString().replace("미코","사쿠라").replace("라미","유키하나"));
                }
                liveFragment.search();
                tweetFragment.setCountry(countrySpinner.getItemAtPosition(i).toString());
                tweetFragment.setPage(1);
                tweetFragment.getJsonData();
                kirinukiFragment.setCountry(countrySpinner.getItemAtPosition(i).toString());
                kirinukiFragment.setPage(1);
                kirinukiFragment.getJsonData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i == EditorInfo.IME_ACTION_SEARCH)) {
                    liveFragment.setCountry(countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());

                    if (countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString().equals("즐겨찾기")) {
                        liveFragment.setKeyword(search.getText().toString());
                        kirinukiFragment.setKeyword(getFavoriteMember().replace("미코","사쿠라").replace("라미","유키하나"));
                        tweetFragment.setKeyword(getFavoriteMember());
                    }
                    else{
                        liveFragment.setKeyword(search.getText().toString());
                        tweetFragment.setKeyword(search.getText().toString());
                        //Log.d("내용",search.getText().toString().replace("미코","사쿠라").replace("라미","유키하나"));
                        kirinukiFragment.setKeyword(search.getText().toString().replace("미코","사쿠라").replace("라미","유키하나"));
                    }
                    liveFragment.search();
                    tweetFragment.setCountry(countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());
                    tweetFragment.setPage(1);
                    tweetFragment.getJsonData();
                    kirinukiFragment.setCountry(countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());
                    kirinukiFragment.setPage(1);
                    kirinukiFragment.getJsonData();
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    return true;
                } else {
                    return true;
                }
            }
        });
    }
    private void reduceDragSensitivity(int sensitivity) {
        try {
            Field ff = ViewPager2.class.getDeclaredField("mRecyclerView") ;
            ff.setAccessible(true);
            RecyclerView recyclerView =  (RecyclerView) ff.get(viewPager);
            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop") ;
            touchSlopField.setAccessible(true);
            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView,touchSlop * sensitivity);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void createFragment() {
        liveFragment = new LiveFragment(memberlist, map);
        tweetFragment = new TweetFragment(tweetList);
        kirinukiFragment = new KirinukiFragment(kirinukiList);
    }
    private String getFavoriteMember(){
        File file = new File(getApplication().getFilesDir().getAbsolutePath(),fileName);
            try {
                FileInputStream fis = new FileInputStream(getApplication().getFilesDir().getAbsolutePath() + "/" + fileName);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String favoriteMember="";
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    String name = parts[0].trim();
                    String favorite = parts[1].trim();
                    if (!name.equals("") && !favorite.equals(""))
                        if(favorite.equals("true"))
                            favoriteMember+=name+",";
                }
                br.close();
                fis.close();
                if(favoriteMember.equals(""))
                    return favoriteMember;
                return favoriteMember.substring(0,favoriteMember.length()-1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "";
    }
}