package com.example.anyholo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.anyholo.Adapter.GridAdapter;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.Model;
import com.example.anyholo.Model.MemberView;
import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LiveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private GridView gridView;
    private GridAdapter gridAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<MemberView> list;//Grid 뷰에 띄워줄 MemberView를 가진 List
    private ArrayList<MemberView> upcominglist;
    private ArrayList<MemberView> noLiveList;//live하지 않는 멤버들만 모아서 정렬할 리스트
    private ArrayList<MemberView> liveList;//live하는 멤버들만 모아서 정렬할 리스트
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_fragment,container,false);
        gridView = view.findViewById(R.id.member_gird);
        swipeRefreshLayout = view.findViewById(R.id.memberLayout);
        gridAdapter = new GridAdapter();
        list = (ArrayList<MemberView>) getArguments().getSerializable("MemberList");
        upcominglist = new ArrayList<MemberView>();//방송 예정인 멤버들
        noLiveList = new ArrayList<MemberView>(); // 방송을 하고 있지 않은 멤버들
        liveList = new ArrayList<MemberView>(); // 방송 중인 멤버들*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemberView m = (MemberView) gridAdapter.getItem(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), MemberIntroActivity.class);
                intent.putExtra("MemberView", m);//누른 그리드 뷰 아이템을 MemberIntroActivity로 전송
                startActivity(intent);
            }
        });
        assortMember();
        gridAdapter.setItems(list);
        gridView.setAdapter(gridAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume실행", "실행");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("start실행", "실행");
    }

    private void assortMember(){
        liveList.clear();
        noLiveList.clear();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOnAir().equals("live"))
                liveList.add(list.get(i));
            else if(list.get(i).getOnAir().equals("upcoming"))
                upcominglist.add(list.get(i));
            else
                noLiveList.add(list.get(i));
        }
        list.clear();
        list.addAll(liveList);
        list.addAll(upcominglist);
        list.addAll(noLiveList);
    }
    @Override
    public void onRefresh() {
        new Thread(new Runnable(){ // 네트워크 작업은 쓰레드를 이용하여 해야함
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("222.237.255.159",9091);
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    JSONObject obj = (JSONObject) is.readObject();
                    is.close();
                    socket.close();
                    Gson gson = new Gson();
                    Model m = gson.fromJson(obj.toString(), Model.class); // gson으로 json데이터를 직렬화
                    list.clear();
                    list.addAll(m.getMemberList());
                    assortMember();
                    getActivity().runOnUiThread(new Runnable() { // 메인 스레드 이외에서 바꾸려고하면 오류나서 이걸 써야함
                        @Override
                        public void run() {
                            gridAdapter.notifyDataSetChanged();
                        }
                    });
                    swipeRefreshLayout.setRefreshing(false);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "새고초림 오류", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }).start();
    }
    public void search(String keyword, String country) {
        list.clear();
        if (keyword.length() == 0) {
            if (country.equals("전체")) {
                list.addAll(liveList);
                list.addAll(noLiveList);
            } else {
                for (int i = 0; i < liveList.size(); i++) {// 값에 따라 검색
                    if (liveList.get(i).getCountry().equals(country))
                        list.add(liveList.get(i));
                }
                for (int i = 0; i < noLiveList.size(); i++) {// 값에 따라 검색
                    if (noLiveList.get(i).getCountry().equals(country))
                        list.add(noLiveList.get(i));
                }
            }
        } else {
            for (int i = 0; i < liveList.size(); i++) {
                if (country.equals("전체")) {
                    if (liveList.get(i).getMemberName().contains(keyword)) {
                        list.add(liveList.get(i));
                    }
                } else {
                    if (liveList.get(i).getMemberName().contains(keyword) && liveList.get(i).getCountry().equals(country)) {
                        list.add(liveList.get(i));
                    }
                }
            }
            for (int i = 0; i < noLiveList.size(); i++) {
                if (country.equals("전체")) {
                    if (noLiveList.get(i).getMemberName().contains(keyword)) {
                        list.add(noLiveList.get(i));
                    }
                } else {
                    if (noLiveList.get(i).getMemberName().contains(keyword) && noLiveList.get(i).getCountry().equals(country)) {
                        list.add(noLiveList.get(i));
                    }
                }
            }
        }
        gridAdapter.notifyDataSetChanged();
    }
}