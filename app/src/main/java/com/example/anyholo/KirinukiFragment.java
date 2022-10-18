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

import java.util.ArrayList;

public class KirinukiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private KirinukiAdapter kirinukiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<KirinukiView> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("시작","시작");
        View view = inflater.inflate(R.layout.kirinuki_fragment,container,false);
        listView = view.findViewById(R.id.kirinuki_list);
        swipeRefreshLayout = view.findViewById(R.id.kirinukirLayout);
        kirinukiAdapter = new KirinukiAdapter();
        list = (ArrayList<KirinukiView>) getArguments().getSerializable("KirinukiList");
        Log.d("잘옴?",list.get(0).getVideoTitle());
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
}