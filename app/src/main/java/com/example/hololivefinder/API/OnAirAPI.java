package com.example.hololivefinder.API;

import com.example.hololivefinder.OnAirModel.OnAirModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OnAirAPI { //라이브 관련 정보 가져오는 API
    //https://www.googleapis.com/youtube/v3/videos?part=snippet&id=비디오 아이디들&fields=items&key=API키
    @GET("videos")
    Call<OnAirModel> getOnAir
    (@Query("part") String part,
     @Query("id") String videoId,
     @Query("fields") String fields,
     @Query("key") String key);
}
