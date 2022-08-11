package com.example.hololivefinder.API;

import  com.example.hololivefinder.ProfileModel.ProfileModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileAPI { //프로필가져오는 API
    //검색 예
    //https://www.googleapis.com/youtube/v3/channels?part=snippet&id=채널 ID&fields=items&key=API 키
    @GET("channels")
    Call<ProfileModel> getProfile
    (@Query("part") String part,
     @Query("id") String channelId,
     @Query("fields") String fields,
     @Query("key") String key);
}
