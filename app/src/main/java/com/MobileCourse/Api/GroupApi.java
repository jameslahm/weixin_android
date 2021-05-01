package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Request.CreateGroupRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Models.GroupDetail;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupApi {
    @POST("group")
    LiveData<ApiResponse<GroupResponse>> createGroup(
            @Body CreateGroupRequest body
            );
}
