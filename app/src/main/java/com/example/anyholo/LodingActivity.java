package com.example.anyholo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.TweetView;
import com.example.anyholo.OnAirModel.Default;
import com.example.anyholo.dbcon.DBConRetrofitObject;
import com.example.anyholo.Model.Model;
import com.example.anyholo.Model.MemberView;
import com.example.anyholo.dbcon.DBcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LodingActivity extends AppCompatActivity {
    private String fileName = "Favorite.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        ImageView loadingImage = findViewById(R.id.lodingImage);
        Glide.with(this).load(R.drawable.loding).into(loadingImage);

        Handler handler = new Handler();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        Thread Loading = new Thread(new Runnable() {
            @Override
            public void run() {
                DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                DBconnect.getData().enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model m = response.body();
                        ArrayList<MemberView> memberList= m.getMemberList();
                        ArrayList<KirinukiView> kirinukiList = m.getVidoes();
                        ArrayList<TweetView> tweetList = m.getTweet();
                        HashMap<String,Boolean> map = checkCache(memberList);
                        intent.putExtra("MemberList", memberList);
                        intent.putExtra("KirinukiList",kirinukiList);
                        intent.putExtra("TweetList",tweetList);
                        intent.putExtra("Favorite",map);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.d("실패","실패");
                    }
                });
            }
        });
        Loading.start();
    }
    private void tweetListSort(ArrayList<TweetView> tweetList){
        ArrayList<TweetView> defaultList = new ArrayList<TweetView>();
        ArrayList<TweetView> repliedList = new ArrayList<TweetView>();
        ArrayList<TweetView> retweetedList = new ArrayList<TweetView>();
        Collections.reverse(tweetList);
        for(TweetView t : tweetList) {
            if (t.getTweetType().equals("DEFAULT")||t.getTweetType().equals("QUOTED")) {
                defaultList.add(t);
            }
            else if(t.getTweetType().equals("REPLIED_TO")){
                repliedList.add(t);
            }
            else{
                retweetedList.add(t);
            }
        }
        Collections.reverse(defaultList);
        for(int i=0;i<defaultList.size();i++){
            for(int j=0;j<repliedList.size();j++){
                if(defaultList.get(i).getTweetId().equals(repliedList.get(j).getPrevTweetId())){
                    defaultList.add(i+1,repliedList.get(j));
                    repliedList.remove(j);
                    break;
                }
            }
        }
        for(int i=0;i<defaultList.size();i++){
            for(int j=0;j<retweetedList.size();j++){
                if(defaultList.get(i).getTweetId().equals(retweetedList.get(j).getPrevTweetId())){
                    String s = defaultList.get(i).getRetweetUser();
                    if(s==null) {
                        defaultList.get(i).setRetweetUser(retweetedList.get(j).getWriteUserName());
                        retweetedList.remove(j);
                        break;
                    }
                    else {
                        defaultList.get(i).setRetweetUser(defaultList.get(i).getRetweetUser() + "," + retweetedList.get(j).getWriteUserName());
                        retweetedList.remove(j);
                        break;
                    }
                }
            }
        }
        tweetList.clear();
        tweetList.addAll(defaultList);
    }
    private HashMap<String, Boolean> checkCache(ArrayList<MemberView> m){
        File file = new File(getApplication().getFilesDir().getAbsolutePath(),fileName);
        HashMap<String,Boolean> map = new HashMap<String,Boolean>();
        if(!file.exists()) {//파일이 없음
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                for(MemberView mv : m)
                    map.put(mv.getMemberName(),false);
                for(Map.Entry<String,Boolean> entry : map.entrySet()){
                    outputStream.write((entry.getKey()+":"+entry.getValue()).getBytes());
                    outputStream.write("\n".getBytes());
                }
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                FileInputStream fis = new FileInputStream(getApplication().getFilesDir().getAbsolutePath() + "/" + fileName);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    String name = parts[0].trim();
                    String favorite = parts[1].trim();
                    if (!name.equals("") && !favorite.equals(""))
                        map.put(name, Boolean.valueOf(favorite));
                }
                br.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}