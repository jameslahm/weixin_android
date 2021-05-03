package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.TimeLineResponse;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimeLineApi {
    // sync single
    @GET("timelinesaved/single")
    LiveData<ApiResponse<TimeLineResponse>> getTimeLineInSingle(
            @Query("userId") String userId);

    // sync group
    @GET("timelinesaved/group")
    LiveData<ApiResponse<TimeLineResponse>> getTimeLineInGroup(
            @Query("groupId") String id
    );
}
