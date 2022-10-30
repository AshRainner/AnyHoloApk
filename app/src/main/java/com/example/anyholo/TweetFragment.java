package com.example.anyholo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.anyholo.Adapter.KirinukiAdapter;
import com.example.anyholo.Adapter.TweetAdapter;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.MemberView;
import com.example.anyholo.Model.Model;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.dbcon.DBConRetrofitObject;
import com.example.anyholo.dbcon.DBcon;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetFragment extends Fragment {
    private ListView listView;
    private TweetAdapter TweetAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ArrayList<TweetView> list;
    private ArrayList<TweetView> copyList;
    private int page = 1;
    private final int MAXITEM=50;

    public TweetFragment(ArrayList<TweetView> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_fragment,container,false);
        listView = view.findViewById(R.id.tweet_list);
        swipyRefreshLayout = view.findViewById(R.id.tweetLayout);
        TweetAdapter = new TweetAdapter();
        copyList = new ArrayList<TweetView>();
        for(TweetView x : list){
            copyList.add(x);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TweetView t = (TweetView) TweetAdapter.getItem(i);
                Uri uri = Uri.parse("https://twitter.com/"+t.getUserId()+"/status/"+t.getTweetId());
                Log.d("ASDF : ","https://twitter.com/"+t.getUserId()+"/status/"+t.getTweetId());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("오류 : ","이이잉");
                }
                //Log.d("사이즈 : ",intent.leng)1585103928907087872
                //startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/usadapekora/status/"+t.getTweetId())));
            }
        });
        TweetAdapter.setItems(list);
        listView.setAdapter(TweetAdapter);
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
                Thread getTweetData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                        DBconnect.getTweetData(String.valueOf(page)).enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Model m = response.body();
                                list.clear();
                                copyList.clear();
                                list.addAll(m.getTweet());
                                for(TweetView x : list){
                                    copyList.add(x);
                                }
                                TweetAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Log.d("실패","실패");
                            }
                        });
                    }
                });
                getTweetData.start();
                swipyRefreshLayout.setRefreshing(false);
                listView.setSelection(0); // 리스트뷰 맨 위로
            }
        });
        return view;
    }
}