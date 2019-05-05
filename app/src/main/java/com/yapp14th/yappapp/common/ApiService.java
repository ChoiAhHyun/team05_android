package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.SuccessResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("login/join/check")
    Call<SuccessResponse> ConfirmIdAndUserNick (
            @Query("userId") String id, @Query("userNick") String userNick
    );


}
