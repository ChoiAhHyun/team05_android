package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.model.UserModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @GET("login/join/check")
    Call<SuccessResponse> ConfirmIdAndUserNick(
            @Query("userId") String id, @Query("userNick") String userNick
    );

    @POST("login/join")
    Call<SuccessResponse> SignUp(
            @Body UserModel userModel
    );

    @POST("login/login")
    Call<SuccessResponse> Login(
            @Body HashMap<String, String> login
    );

    @Multipart
    @POST("login/join/uploadImage")
    Call<SuccessResponse> uploadImage(
            @Part MultipartBody.Part userImg, @Query("userId") String userId
    );

    @POST("meet/client-token")
    Call<SuccessResponse> sendToken(
            @Body HashMap<String, String> token
    );

    @FormUrlEncoded
    @POST("meet/detail")
    Call<MakeResponse> makeMeeting (
            @Field("name") String name, @Field("date") String datetime, @Field("location") String location,
            @Field("latitude") Double	latitude,
            @Field("longitude") Double	longitude,
            @Field("explanation") String explanation,
            @Field("personNum") int personNumMax,
            @Field("list") List<String> list,
            @Field("keyword") String keyword
    );
}
