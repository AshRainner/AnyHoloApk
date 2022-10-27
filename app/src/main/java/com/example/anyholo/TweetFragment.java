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
import com.example.anyholo.Adapter.TweetAdapter;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.TweetView;

import java.util.ArrayList;

public class TweetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private TweetAdapter TweetAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<TweetView> list;
    private ArrayList<TweetView> copyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("트위터","시작");
        View view = inflater.inflate(R.layout.tweet_fragment,container,false);
        listView = view.findViewById(R.id.tweet_list);
        swipeRefreshLayout = view.findViewById(R.id.tweetLayout);
        TweetAdapter = new TweetAdapter();
        list = (ArrayList<TweetView>) getArguments().getSerializable("TweetList");
        copyList = new ArrayList<TweetView>();
        for(TweetView x : list){
            copyList.add(x);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TweetView k = (TweetView)TweetAdapter.getItem(i);
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.naver.com")));
            }
        });
        TweetAdapter.setItems(list);
        listView.setAdapter(TweetAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
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