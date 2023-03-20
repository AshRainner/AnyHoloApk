package com.anyholo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
    private String version = "1.0.3";
    private int errorCount = 0;
    private TextView versionText;
    private Boolean update = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        ImageView loadingImage = findViewById(R.id.loadingImage);
        versionText = findViewById(R.id.versionText);
        versionText.setText("Version : "+version);
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
                            Toast.makeText(getApplicationContext(),"최신 버전이 아닙니다", Toast.LENGTH_SHORT).show();
                            update=false;
                        }
                        ArrayList<MemberView> memberList= m.getMemberList();
                        ArrayList<KirinukiView> kirinukiList = m.getVidoes();
                        ArrayList<TweetView> tweetList = m.getTweet();
                        HashMap<String,Boolean> map = checkCache(memberList);
                        intent.putExtra("MemberList", memberList);
                        intent.putExtra("KirinukiList",kirinukiList);
                        intent.putExtra("TweetList",tweetList);
                        intent.putExtra("Favorite",map);
                        intent.putExtra("Update",update);
                        String updateString = m.getUpdateString();
                        intent.putExtra("UpdateString",updateString);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.d("오류",t.toString());
                        errorCount++;
                        if(errorCount<6)
                            call.clone().enqueue(this); //오류날 시 리트라이
                        else
                            Toast.makeText(getApplicationContext(),"로딩 실패 앱을 껏다 켜주세요", Toast.LENGTH_SHORT).show();
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
                BufferedReader br = new BufferedReader(new FileReader(file));
                ArrayList<String> names = new ArrayList<>();
                for(MemberView mv : m)
                    names.add(mv.getMemberName());
                String line = null;
                boolean check = false;
                int i=0;
                while ((line = br.readLine()) != null) {
                    i++;
                    String[] parts = line.split(":");
                    String name = parts[0].trim();
                    if(names.contains(name)) {
                        String favorite = parts[1].trim();
                        if (!name.equals("") && !favorite.equals(""))
                            map.put(name, Boolean.valueOf(favorite));
                    }
                    else{
                        check = true;
                    }
                }
                br.close();
                fis.close();
                if(check||i!=names.size()){//만약 데이터베이스에 수정된 내용이 있으면 수정된 부분만 바꿔서 파일 생성
                    FileOutputStream outputStream;
                    HashMap<String,Boolean> tempMap = new HashMap<String,Boolean>();
                    try {
                        outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        for(MemberView mv : m) {
                            if(map.get(mv.getMemberName())==null) // 새로운 멤버가 추가된거면 원래 값이 없기에 null이 나옴 and 수정된 친구도 null이 나옴
                                tempMap.put(mv.getMemberName(), false);
                            else
                                tempMap.put(mv.getMemberName(), map.get(mv.getMemberName()));
                        }
                        for(Map.Entry<String,Boolean> entry : tempMap.entrySet()){
                            outputStream.write((entry.getKey()+":"+entry.getValue()).getBytes());
                            outputStream.write("\n".getBytes());
                        }
                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return tempMap;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}