package com.example.anyholo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.anyholo.dbcon.DBConRetrofitObject;
import com.example.anyholo.MemberModel.MemberModel;
import com.example.anyholo.MemberModel.MemberView;
import com.example.anyholo.dbcon.DBcon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LodingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        ImageView loadingImage = findViewById(R.id.lodingImage);
        Glide.with(this).load(R.drawable.loding).into(loadingImage);

        Handler handler = new Handler();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        Thread getData = new Thread(new Runnable() {
            @Override
            public void run() {
                DBcon DBconnect = DBConRetrofitObject.getInstance().create(DBcon.class);
                DBconnect.getData().enqueue(new Callback<MemberModel>() {
                    @Override
                    public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                        MemberModel m = response.body();
                        ArrayList<MemberView> memberList= m.getMemberList();
                        intent.putExtra("MemberList", memberList);
                    }

                    @Override
                    public void onFailure(Call<MemberModel> call, Throwable t) {
                        Log.d("실패","실패");
                    }
                });
            }
        });
        getData.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}