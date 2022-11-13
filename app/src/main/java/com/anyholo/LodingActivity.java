package com.anyholo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.anyholo.Model.KirinukiView;
import com.anyholo.Model.TweetView;
import com.anyholo.dbcon.DBConRetrofitObject;
import com.anyholo.Model.Model;
import com.anyholo.Model.MemberView;
import com.anyholo.dbcon.DBcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LodingActivity extends AppCompatActivity {
    private String fileName = "Favorite.txt";
    private String version = "1.0.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        ImageView loadingImage = findViewById(R.id.loadingImage);
        Glide.with(this).load(R.drawable.loading).into(loadingImage);

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
                        if(version.equals(m.getVersion())){
                            Log.d("버전 같음","ㅇㅇ");
                        }
                        else{
                            Log.d("버전 다름","ㅇㅇ");
                        }
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
                                Toast.makeText(getApplicationContext(),"로딩 실패 앱을 껐다 켜주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        Loading.start();
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
                Log.d("경로",getApplication().getFilesDir().getAbsolutePath() + "/" + fileName);
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