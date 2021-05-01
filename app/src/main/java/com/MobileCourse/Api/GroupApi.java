package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Request.CreateGroupRequest;
import com.MobileCourse.Api.Request.ExitGroupRequest;
import com.MobileCourse.Api.Request.InviteInToGroupRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Models.GroupDetail;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupApi {
    @POST("group")
    LiveData<ApiResponse<GroupResponse>> createGroup(
            @Body CreateGroupRequest body
            );

    @POST("group/exit")
    LiveData<ApiResponse<CommonResponse>> exitGroup(
            @Body ExitGroupRequest body
            );


    @POST("group/invite")
    LiveData<ApiResponse<GroupResponse>> inviteInToGroup(
            @Body InviteInToGroupRequest body
    );
}
