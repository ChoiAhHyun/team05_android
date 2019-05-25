package com.yapp14th.yappapp.common;

import com.yapp14th.yappapp.model.GroupDetailResData;
import com.yapp14th.yappapp.model.GroupInfoResData;
import com.yapp14th.yappapp.model.GroupRequestBody;
import com.yapp14th.yappapp.model.NearGroupRequestBody;
import com.yapp14th.yappapp.model.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("login/join/userId")
    Call<SuccessResponse> ConfirmUserId (
            @Query("userId") String id
    );

    @POST("meet/meetId/near")
    Call<GroupInfoResData> GetNearGroupDatas (@Body GroupRequestBody body);

    @POST("meet/scheduled")
    Call<GroupInfoResData> GetRealTimeGroupDatas (@Body GroupRequestBody body);

    @GET("meet/detail")
    Call<GroupDetailResData> GetGroupDetailDatas ( @Query("meetId") Integer meetId );
}
