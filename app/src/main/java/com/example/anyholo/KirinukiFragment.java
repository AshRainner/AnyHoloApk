package com.example.anyholo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.anyholo.Adapter.KirinukiAdapter;
import com.example.anyholo.Adapter.TweetAdapter;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.Model;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.dbcon.DBConRetrofitObject;
import com.example.anyholo.dbcon.DBcon;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class KirinukiFragment extends Fragment {
    private ListView listView;
    private KirinukiAdapter kirinukiAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ArrayList<KirinukiView> list;
    private ArrayList<KirinukiView> copyList;
    private int page=1;
    private final int MAXITEM=10;
    public KirinukiFragment(ArrayList<KirinukiView> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("키리누키 프래그먼트","시작");
        View view = inflater.inflate(R.layout.kirinuki_fragment,container,false);
        listView = view.findViewById(R.id.kirinuki_list);
        swipyRefreshLayout = view.findViewById(R.id.kirinukirLayout);
        kirinukiAdapter = new KirinukiAdapter();
        //list = (ArrayList<KirinukiView>) getArguments().getSerializable("KirinukiList");
        copyList = new ArrayList<KirinukiView>();
        for(KirinukiView x : list){
            copyList.add(x);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                KirinukiView k = (KirinukiView)kirinukiAdapter.getItem(i);
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://youtube.com/watch?v="+k.getYoutubeUrl())));
            }
        });
        kirinukiAdapter.setItems(list);
        listView.setAdapter(kirinukiAdapter);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction==SwipyRefreshLayoutDirection.TOP) {
                    if(page>1)
                        page--;
                }
                else {
                    if(page>=1&&list.size()==MAXITEM)
                        page++;
                }
                Thread getKirinukiData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                        DBconnect.getKirinukiData(String.valueOf(page)).enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Model m = response.body();
                                list.clear();
                                copyList.clear();
                                list.addAll(m.getVidoes());
                                for(KirinukiView x : list){
                                    copyList.add(x);
                                }
                                kirinukiAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Log.d("실패","실패");
                            }
                        });
                    }
                });
                getKirinukiData.start();
                swipyRefreshLayout.setRefreshing(false);
                listView.setSelection(0); // 리스트뷰 맨 위로
            }
        });
        return view;
    }
    public void search(String keyword, String country) {
        if(list!=null) {
            list.clear();
            if (keyword.length() == 0) {
                if (country.equals("전체")) {
                    list.addAll(copyList);
                }
                else {
                    countrySearch(country);
                }
            } else {
                Log.d("여기옴","?");
                for (int i = 0; i < copyList.size(); i++) {
                    if (country.equals("전체")) {
                        if (copyList.get(i).getTag().contains(keyword)||copyList.get(i).getVideoTitle().contains(keyword)) {
                            list.add(copyList.get(i));
                        }
                    } else {
                        countrySearch(country);
                    }
                }
            }
            kirinukiAdapter.notifyDataSetChanged();
        }
    }
    public void countrySearch(String country){
        ArrayList<KirinukiView> tempList = new ArrayList<KirinukiView>();
        Thread getKirinukiData = new Thread(new Runnable() {
            @Override
            public void run() {
                DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                DBconnect.getKirinukiData(String.valueOf(page), country).enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model m = response.body();
                        list.clear();
                        Log.d("asdf",country);
                        copyList.clear();
                        list.addAll(m.getVidoes());
                        for(KirinukiView x : list){
                            copyList.add(x);
                        }
                        kirinukiAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.d("실패","실패");
                    }
                });
            }
        });
        getKirinukiData.start();
    }
}