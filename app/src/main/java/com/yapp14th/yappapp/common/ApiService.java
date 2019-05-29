package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.AlarmResponse;
import com.yapp14th.yappapp.model.GroupDetailResData;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.GroupRequestBody;
import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.NoticeCommentResData;
import com.yapp14th.yappapp.model.NoticeInfoResData;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.model.UserModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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

    @POST("meet/meetId/near")
    Call<GroupInfoResData> GetNearGroupDatas (@Body GroupRequestBody body);

    @POST("meet/scheduled")
    Call<GroupInfoResData> GetRealTimeGroupDatas (@Body GroupRequestBody body);

    @GET("meet/detail")
    Call<GroupDetailResData> GetGroupDetailDatas ( @Query("meetId") Integer meetId );

    @GET("notice/view/")
    Call<NoticeInfoResData> GetNoticeDatas(@Query("meetId") Integer meetId);

    @POST("meet/client-token")
    Call<SuccessResponse> sendToken(
            @Body HashMap<String, String> token
    );

    @POST("meet/alarm")
    Call<AlarmResponse> GetAlarmDatas(@Body String userId);

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

    @GET("notice/comment/view")
    Call<NoticeCommentResData> GetNoticeCommentDatas(@Query("meetId") Integer meetid);

    @GET("meet/keyword")
    Call<GroupInfoResData> getSearchResultData (
        @Query("userId") String userId, @Query("keyword") String keyword, @Query("longitude") Double longitude, @Query("latitude") Double latitude, @Query("page") int page
    );

    @GET("meet/dsearch")
    Call<GroupInfoResData> getSearchDefaultData (
        @Query("userId") String userId, @Query("latitude") Double latitude, @Query("longitude") Double longitude, @Query("distancebool") int distancebool, @Query("page") int page
    );
}
