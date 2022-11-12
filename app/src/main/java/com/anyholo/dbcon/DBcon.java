package com.anyholo.dbcon;

import com.anyholo.Model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBcon {
    @GET("/dbcon")
    Call<Model> getData();
    @GET("/dbcon/Live")
    Call<Model> getLiveData();
    @GET("/dbcon/Tweet")
    Call<Model> getTweetData(@Query("Page") String page,@Query("Country") String country,@Query("Keyword")String keyword);
    @GET("/dbcon/Kirinuki")
    Call<Model> getKirinukiData(@Query("Page") String page,@Query("Country") String country,@Query("Keyword") String keyword);
}
