package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Request.LoginRequest;
import com.MobileCourse.Api.Request.RegisterRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Models.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @GET("user")
    LiveData<ApiResponse<UserResponse>> getUserByWeixinId(
            @Query("weixinId") String weixinId
    );

    @POST("user")
    LiveData<ApiResponse<UserResponse>> register(
            @Body RegisterRequest body
            );

    @POST("user")
    LiveData<ApiResponse<UserResponse>> login(
            @Body LoginRequest body
            );
}
