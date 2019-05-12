package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("login/join/userId")
    Call<SuccessResponse> ConfirmUserId (
            @Query("userId") String id
    );

    @GET("meet/meetId/near")
    Call<GroupInfoResData> GetNearGroups (
            @Query("myLongitude") Double myLongitude,
            @Query("myLatitude") Double myLatitude
    );

}
