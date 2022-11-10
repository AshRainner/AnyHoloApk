package com.example.anyholo;

import android.content.Context;
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

import com.example.anyholo.Adapter.LiveAdapter;
import com.example.anyholo.Model.Model;
import com.example.anyholo.Model.MemberView;
import com.example.anyholo.dbcon.DBConRetrofitObject;
import com.example.anyholo.dbcon.DBcon;
import com.example.anyholo.inferface.FavoriteHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFragment extends Fragment implements FavoriteHandle {
    private GridView gridView;
    private LiveAdapter liveAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<MemberView> list;//Grid 뷰에 띄워줄 MemberView를 가진 List
    private ArrayList<MemberView> upcomingList;//방송예정중인 멤버
    private ArrayList<MemberView> noLiveList;//live하지 않는 멤버들만 모아서 정렬할 리스트
    private ArrayList<MemberView> liveList;//live하는 멤버들만 모아서 정렬할 리스트
    private HashMap<String, Boolean> favorite;
    private ArrayList<MemberView> favoriteList;

    public LiveFragment(ArrayList<MemberView> list, HashMap<String, Boolean> favorite) {
        this.list = list;
        this.favorite = favorite;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_fragment, container, false);
        gridView = view.findViewById(R.id.member_gird);
        swipeRefreshLayout = view.findViewById(R.id.memberLayout);
        liveAdapter = new LiveAdapter(this);
        upcomingList = new ArrayList<MemberView>();//방송 예정인 멤버들
        noLiveList = new ArrayList<MemberView>(); // 방송을 하고 있지 않은 멤버들
        liveList = new ArrayList<MemberView>(); // 방송 중인 멤버들*/
        favoriteList = new ArrayList<MemberView>();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemberView m = (MemberView) liveAdapter.getItem(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), MemberIntroActivity.class);
                intent.putExtra("MemberView", m);//누른 그리드 뷰 아이템을 MemberIntroActivity로 전송
                startActivity(intent);
            }
        });
        sortMember();
        liveAdapter.setItems(list, favorite);
        gridView.setAdapter(liveAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread getLiveData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                        DBconnect.getLiveData().enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Model m = response.body();
                                list.clear();
                                list.addAll(m.getMemberList());
                                sortMember();
                                liveAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                gridView.setSelection(0); // 리스트뷰 맨 위로
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Log.d("실패", "실패");
                            }
                        });
                    }
                });
                getLiveData.start();
            }
        });
        return view;
    }

    private void sortMember() {
        favoriteList.clear();
        liveList.clear();
        upcomingList.clear();
        noLiveList.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOnAir().equals("live"))
                liveList.add(list.get(i));
            else if (list.get(i).getOnAir().equals("upcoming"))
                upcomingList.add(list.get(i));
            else
                noLiveList.add(list.get(i));
        }
        sortMember(liveList);
        sortMember(upcomingList);
        sortMember(noLiveList);
        list.clear();
        list.addAll(favoriteList);
        list.addAll(liveList);
        list.addAll(upcomingList);
        list.addAll(noLiveList);
        liveAdapter.notifyDataSetChanged();
    }
    private void sortMember(ArrayList<MemberView> targetList){
        for (int i = 0; i < targetList.size(); i++) {
            if (favorite.get(targetList.get(i).getMemberName())) {
                favoriteList.add(targetList.get(i));
                targetList.remove(i);
                i--;
            }
        }
    }
    /*@Override
    public void onRefresh() {
        new Thread(new Runnable() { // 네트워크 작업은 쓰레드를 이용하여 해야함
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("222.237.255.159", 9091);
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    JSONObject obj = (JSONObject) is.readObject();
                    is.close();
                    socket.close();
                    Gson gson = new Gson();
                    Model m = gson.fromJson(obj.toString(), Model.class); // gson으로 json데이터를 직렬화
                    list.clear();
                    list.addAll(m.getMemberList());
                    sortMember();
                    getActivity().runOnUiThread(new Runnable() { // 메인 스레드 이외에서 바꾸려고하면 오류나서 이걸 써야함
                        @Override
                        public void run() {
                            gridAdapter.notifyDataSetChanged();
                        }
                    });
                    swipeRefreshLayout.setRefreshing(false);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "새로고침 오류", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }).start();
    }*/

    public void search(String keyword, String country) {
        list.clear();
        if (keyword.length() == 0) {
            if (country.equals("전체")) {
                list.addAll(favoriteList);
                list.addAll(liveList);
                list.addAll(upcomingList);
                list.addAll(noLiveList);
            } else if (country.equals("즐겨찾기")) {
                list.addAll(favoriteList);
            } else {
                for (int i = 0; i < favoriteList.size(); i++) {// 값에 따라 검색
                    if (favoriteList.get(i).getCountry().equals(country))
                        list.add(favoriteList.get(i));
                }
                for (int i = 0; i < liveList.size(); i++) {// 값에 따라 검색
                    if (liveList.get(i).getCountry().equals(country))
                        list.add(liveList.get(i));
                }
                for (int i = 0; i < upcomingList.size(); i++) {// 값에 따라 검색
                    if (upcomingList.get(i).getCountry().equals(country))
                        list.add(upcomingList.get(i));
                }
                for (int i = 0; i < noLiveList.size(); i++) {// 값에 따라 검색
                    if (noLiveList.get(i).getCountry().equals(country))
                        list.add(noLiveList.get(i));
                }
            }
        } else {
            for (int i = 0; i < favoriteList.size(); i++) {
                if (country.equals("전체")) {
                    if (favoriteList.get(i).getMemberName().contains(keyword)) {
                        list.add(favoriteList.get(i));
                    }
                } else {
                    if (favoriteList.get(i).getMemberName().contains(keyword) && favoriteList.get(i).getCountry().equals(country)) {
                        list.add(favoriteList.get(i));
                    }
                }
            }
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
            for (int i = 0; i < upcomingList.size(); i++) {
                if (country.equals("전체")) {
                    if (upcomingList.get(i).getMemberName().contains(keyword)) {
                        list.add(upcomingList.get(i));
                    }
                } else {
                    if (upcomingList.get(i).getMemberName().contains(keyword) && upcomingList.get(i).getCountry().equals(country)) {
                        list.add(upcomingList.get(i));
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
        liveAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFavoriteUpdate(String name, boolean values) {
        favorite.replace(name, values);
        Log.d(name, String.valueOf(values));
        File file = new File(getActivity().getApplication().getFilesDir().getAbsolutePath() + "/" + "Favorite.txt");
        file.delete();
        FileOutputStream outputStream;
        try {
            outputStream = getActivity().openFileOutput("Favorite.txt", Context.MODE_PRIVATE);
            for (Map.Entry<String, Boolean> entry : favorite.entrySet()) {
                outputStream.write((entry.getKey() + ":" + entry.getValue()).getBytes());
                outputStream.write("\n".getBytes());
            }
            sortMember();
            Log.d("!@#","업데이트");
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}