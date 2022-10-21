package com.example.anyholo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.anyholo.Adapter.CustomViewPagerAdapter;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.MemberView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    /*
    이 어플리케이션의 제작권은 이건이 가지고 있습니다.
    무단 전제 및 도용 시 저작권 법에 의해 처벌 받을 수 있습니다.
    제작자 : 인하공업 전문 대학 2학년 이건
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText search;
    private ImageButton menuBtn;
    private ViewPager2 viewPager;
    private LiveFragment liveFragment;
    private test_fragment testFragment;
    private KirinukiFragment kirinukiFragment;
    private CustomViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private HashMap<String,Boolean> map;
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
        ArrayList<MemberView> memberlist = (ArrayList<MemberView>) intent.getSerializableExtra("MemberList");
        ArrayList<KirinukiView> kirinukiList = (ArrayList<KirinukiView>) intent.getSerializableExtra("KirinukiList");
        map = (HashMap<String, Boolean>) intent.getSerializableExtra("Favorite");
        Log.d("소라",String.valueOf(map.get("소라")));
        bundle.putSerializable("MemberList",memberlist);
        bundle.putSerializable("Favorite",map);
        bundle.putSerializable("KirinukiList",kirinukiList);
        pagerAdapter = new CustomViewPagerAdapter(this);
        createFragment();
        liveFragment.setArguments(bundle);
        kirinukiFragment.setArguments(bundle);
        pagerAdapter.addFragment(liveFragment);
        pagerAdapter.addFragment(testFragment);
        pagerAdapter.addFragment(kirinukiFragment);
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
                liveFragment.search(search.getText().toString(), countrySpinner.getItemAtPosition(i).toString());
                kirinukiFragment.search(search.getText().toString(), countrySpinner.getItemAtPosition(i).toString());
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
                liveFragment.search(search.getText().toString(), countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());
                kirinukiFragment.search(search.getText().toString(), countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString());
            }
        });
    }
    private void createFragment(){
        liveFragment = new LiveFragment();
        Log.d("liveFragmet생성","!");
        testFragment = new test_fragment();
        Log.d("testFragmet생성","!");
        kirinukiFragment = new KirinukiFragment();
        Log.d("kirinukiFragmet생성","!");
    }
}