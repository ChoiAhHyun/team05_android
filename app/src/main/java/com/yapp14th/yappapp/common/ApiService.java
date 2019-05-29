package com.yapp14th.yappapp.common;

import com.google.gson.JsonObject;
import com.yapp14th.yappapp.model.MakeModel;
import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.MypageInterestModel;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.model.UserModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST("meet/detail")
    Call<MakeResponse> makeMeeting (
            @Body MakeModel makeModel
    );

    @Multipart
    @POST("meet/image")
    Call<SuccessResponse> uploadMeetImage(
            @Part MultipartBody.Part meetImg, @Query("meetId") String meetId
    );

    @GET("mypage/user/")
    Call<JsonObject> getUserInfo(
            @Query("userId") String userId
    );

    @GET("mypage/myMeet/")
    Call<JsonObject> myMeet(
            @Query("userId") String userId, @Query("latitude") Double latitude, @Query("longitude") Double longitude
    );

    @GET("mypage/meetHistory/")
    Call<JsonObject> meetHistory(
            @Query("userId") String userId, @Query("latitude") Double latitude, @Query("longitude") Double longitude
    );

    @POST("login/withdraw")
    Call<SuccessResponse> Withdraw(
            @Body HashMap<String, String> withdraw
    );

    @POST("interest/modify")
    Call<SuccessResponse> modifyInterest(
            @Body MypageInterestModel mypageInterestModel
            );

    @PUT("login/join/removeImage/")
    Call<SuccessResponse> removeUserImage(
            @Query("userId") String userId
    );
}