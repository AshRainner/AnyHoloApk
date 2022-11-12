package com.anyholo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.anyholo.Adapter.KirinukiAdapter;
import com.anyholo.Model.KirinukiView;
import com.anyholo.Model.Model;
import com.anyholo.dbcon.DBConRetrofitObject;
import com.anyholo.dbcon.DBcon;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KirinukiFragment extends Fragment {
    private ListView listView;
    private KirinukiAdapter kirinukiAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ArrayList<KirinukiView> list;
    private ArrayList<KirinukiView> copyList;
    private int page=1;
    private String country="전체";
    private String keyword="";
    private final int MAXITEM=10;
    public KirinukiFragment(ArrayList<KirinukiView> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                getJsonData();
            }
        });
        return view;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public void setPage(int page){
        this.page=page;
    }
    public void getJsonData(){
        Thread getKirinukiData = new Thread(new Runnable() {
            @Override
            public void run() {
                DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                DBconnect.getKirinukiData(String.valueOf(page),country,keyword).enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model m = response.body();
                        list.clear();
                        copyList.clear();
                        list.addAll(m.getVidoes());
                        kirinukiAdapter.notifyDataSetChanged();
                        swipyRefreshLayout.setRefreshing(false);
                        listView.setSelection(0);// 리스트뷰 맨 위로
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