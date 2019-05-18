package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("login/join/userId")
    Call<SuccessResponse> ConfirmUserId (
            @Query("userId") String id
    );

    @FormUrlEncoded
    @POST("meet/detail")
    Call<MakeResponse> makeMeeting (@Field("name") String name,
                                    @Field("date") String datetime,
                                    @Field("location") String location,
                                    @Field("latitude") Double	latitude,
                                    @Field("longitude") Double	longitude,
                                    @Field("explanation") String explanation,
                                    @Field("personNum") int personNumMax,
                                    @Field("list") List<String> list,
                                    @Field("keyword") String keyword
    );
}
