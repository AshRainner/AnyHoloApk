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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anyholo.Adapter.TweetAdapter;
import com.anyholo.Model.Model;
import com.anyholo.Model.TweetView;
import com.anyholo.dbcon.DBConRetrofitObject;
import com.anyholo.dbcon.DBcon;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

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
    private TweetAdapter tweetAdapter;
    private String country;
    private String keyword;
    private int errorCount = 0;
    public TweetFragment(ArrayList<TweetView> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_fragment, container, false);
        listView = view.findViewById(R.id.tweet_list);
        swipyRefreshLayout = view.findViewById(R.id.tweetLayout);
        swipyRefreshLayout.setDistanceToTriggerSync(80);
        tweetAdapter = new TweetAdapter();
        copyList = new ArrayList<TweetView>();
        for (TweetView x : list) {
            copyList.add(x);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TweetView t = (TweetView) tweetAdapter.getItem(i);
                TweetView t = (TweetView) tweetAdapter.getItem(i);
                Uri uri = Uri.parse("https://twitter.com/" + t.getUserId() + "/status/" + t.getTweetId());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("오류 : ", "이이잉");
                }
            }
        });
        tweetAdapter.setItems(list);
        listView.setAdapter(tweetAdapter);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    if (page > 1)
                        page--;
                } else {
                    if (page >= 1)
                        page++;
                }
                getJsonData();
            }
        });
        return view;
    }

    public void getJsonData() {
        Thread getKirinukiData = new Thread(new Runnable() {
            @Override
            public void run() {
                DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                DBconnect.getTweetData(String.valueOf(page), country, keyword).enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        errorCount=0;
                        Model m = response.body();
                        list.clear();
                        copyList.clear();
                        list.addAll(m.getTweet());
                        tweetAdapter.notifyDataSetChanged();
                        swipyRefreshLayout.setRefreshing(false);
                        listView.setSelection(0);// 리스트뷰 맨 위로
                    }
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        errorCount++;
                        if(errorCount<6)
                            call.clone().enqueue(this); //오류날 시 리트라이
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            swipyRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
        getKirinukiData.start();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPage(int page) {
        this.page = page;
    }
}