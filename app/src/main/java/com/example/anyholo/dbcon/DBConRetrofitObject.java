package com.example.anyholo.dbcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBConRetrofitObject {
    private static final String BASE_URL = "http://192.168.35.225:8081";
    private static Retrofit retrofit;
    private DBConRetrofitObject(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static Retrofit getInstance() { //불러낸 값을 retrofit에 담음
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))//gson으로 파싱
                    .build();
        }
        return retrofit;
    }
}
