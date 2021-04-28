package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Models.User;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET("user")
    LiveData<ApiResponse<UserResponse>> getUserByWeixinId(
            @Query("weixinId") String weixinId
    );


}
