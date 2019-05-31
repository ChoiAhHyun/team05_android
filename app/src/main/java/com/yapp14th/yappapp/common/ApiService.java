package com.yapp14th.yappapp.common;

import com.google.gson.JsonObject;
import com.yapp14th.yappapp.model.MakeModel;
import com.yapp14th.yappapp.model.MakeResponse;
import com.yapp14th.yappapp.model.MeetingDeleteBody;
import com.yapp14th.yappapp.model.MeetingDetailReqModel;
import com.yapp14th.yappapp.model.MypageInterestModel;
import com.yapp14th.yappapp.model.AlarmResponse;
import com.yapp14th.yappapp.model.GroupDetailResData;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.GroupRequestBody;
import com.yapp14th.yappapp.model.NoticeCommentResData;
import com.yapp14th.yappapp.model.NoticeInfoResData;
import com.yapp14th.yappapp.model.SuccessResponse;
import com.yapp14th.yappapp.model.UserIdModel;
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
    Call<AlarmResponse> GetAlarmDatas(@Body UserIdModel userIdModel);

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
    Call<GroupInfoResData> myMeet(
            @Query("userId") String userId, @Query("latitude") Double latitude, @Query("longitude") Double longitude
    );

    @GET("mypage/meetHistory/")
    Call<GroupInfoResData> meetHistory(
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

    @PUT("meet/attendant")
    Call<SuccessResponse> EndMeeting(@Query("meetId") Integer meetId);

    @POST("meet/attendant")
    Call<SuccessResponse> ApplyOnMeeting(@Body MeetingDetailReqModel reqModel);

    @POST("meet/attendant/cancel")
    Call<SuccessResponse> CancelParticipateInMeeting(@Body MeetingDetailReqModel reqModel);

    @GET("notice/comment/view")
    Call<NoticeCommentResData> GetNoticeCommentDatas(@Query("meetId") Integer meetid);

    @POST("meet/meetId")
    Call<SuccessResponse> DeleteMeeting(@Body MeetingDeleteBody meetingDeleteBody);

    @GET("meet/keyword")
    Call<GroupInfoResData> getSearchResultData (
        @Query(encoded = true, value = "userId") String userId, @Query("keyword") String keyword, @Query("longitude") Double longitude, @Query("latitude") Double latitude, @Query("page") int page
    );

    @GET("meet/dsearch")
    Call<GroupInfoResData> getSearchDefaultData (
        @Query(encoded = true, value = "userId") String userId, @Query("latitude") Double latitude, @Query("longitude") Double longitude, @Query("distancebool") int distancebool, @Query("page") int page
    );
}