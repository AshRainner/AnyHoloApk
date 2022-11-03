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
import com.example.anyholo.Adapter.TestAdapter;
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
    //private TweetAdapter tweetAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ArrayList<TweetView> list;
    private ArrayList<TweetView> copyList;
    private int page = 1;
    private final int MAXITEM=10;
    private TestAdapter testAdapter;

    public TweetFragment(ArrayList<TweetView> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_fragment,container,false);
        listView = view.findViewById(R.id.tweet_list);
        swipyRefreshLayout = view.findViewById(R.id.tweetLayout);
        //tweetAdapter = new TweetAdapter();
        testAdapter = new TestAdapter();
        copyList = new ArrayList<TweetView>();
        for(TweetView x : list){
            copyList.add(x);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TweetView t = (TweetView) tweetAdapter.getItem(i);
                TweetView t = (TweetView) testAdapter.getItem(i);
                if(t.getTweetType().equals("RETWEETED"))
                    t=t.getPrevTweet();
                Uri uri = Uri.parse("https://twitter.com/"+t.getUserId()+"/status/"+t.getTweetId());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("오류 : ","이이잉");
                }
            }
        });
        testAdapter.setItems(list);
        listView.setAdapter(testAdapter);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction==SwipyRefreshLayoutDirection.TOP) {
                    if(page>1)
                        page--;
                }
                else {
                    if(page>=1)
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
                                //tweetAdapter.notifyDataSetChanged();
                                Log.d("페이지 : ",String.valueOf(page));
                                testAdapter.notifyDataSetChanged();
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