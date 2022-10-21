package com.example.anyholo;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.anyholo.Adapter.KirinukiAdapter;
import com.example.anyholo.Model.KirinukiView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class KirinukiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private KirinukiAdapter kirinukiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<KirinukiView> list;
    private ArrayList<KirinukiView> copyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("키리누키 프래그먼트","시작");
        View view = inflater.inflate(R.layout.kirinuki_fragment,container,false);
        listView = view.findViewById(R.id.kirinuki_list);
        swipeRefreshLayout = view.findViewById(R.id.kirinukirLayout);
        kirinukiAdapter = new KirinukiAdapter();
        list = (ArrayList<KirinukiView>) getArguments().getSerializable("KirinukiList");
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
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("start실행", "실행");
    }
    @Override
    public void onRefresh() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                    swipeRefreshLayout.setRefreshing(false);
            }
        }).start();
    }
    public void search(String keyword, String country) {
        if(list!=null) {
            list.clear();
            if (keyword.length() == 0) {
                if (country.equals("전체")) {
                    list.addAll(copyList);
                }
                else {
                    list.addAll(countrySearh(country));
                }
            } else {
                Log.d("여기옴","?");
                for (int i = 0; i < copyList.size(); i++) {
                    if (country.equals("전체")) {
                        if (copyList.get(i).getTag().contains(keyword)||copyList.get(i).getVideoTitle().contains(keyword)) {
                            list.add(copyList.get(i));
                        }
                    } else {
                        ArrayList<KirinukiView> tempList = countrySearh(country);
                        for(KirinukiView k : tempList)
                            if(k.getTag().contains(keyword)||k.getVideoTitle().contains(keyword))
                                list.add(k);
                    }
                }
            }
            kirinukiAdapter.notifyDataSetChanged();
        }
    }
    public ArrayList<KirinukiView> countrySearh(String country){
        ArrayList<KirinukiView> tempList = new ArrayList<KirinukiView>();
        ArrayList<String> nameList = new ArrayList<String>();
        if(country.equals("JP"))
            for (String s : getResources().getString(R.string.JPName).split(","))
                nameList.add(s);
        else if(country.equals("EN"))
            for (String s : getResources().getString(R.string.ENName).split(","))
                nameList.add(s);
        else
            for (String s : getResources().getString(R.string.IDName).split(","))
                nameList.add(s);
            for(KirinukiView k : copyList)
                for(String s : nameList)
                    if(k.getTag().contains(s)) {
                        tempList.add(k);
                        break;
                    }
        return tempList;
    }
}