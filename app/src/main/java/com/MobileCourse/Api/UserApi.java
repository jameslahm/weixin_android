package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Request.ConfirmAddFriendRequest;
import com.MobileCourse.Api.Request.DeleteFriendRequest;
import com.MobileCourse.Api.Request.LoginRequest;
import com.MobileCourse.Api.Request.RegisterRequest;
import com.MobileCourse.Api.Request.UpdateUserRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;
import com.MobileCourse.Api.Response.MeUserResponse;
import com.MobileCourse.Api.Response.UserResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {

    @GET("user")
    LiveData<ApiResponse<UserResponse>> getUserByWeixinId(
            @Query("weixinId") String weixinId
    );

    @POST("user/register")
    LiveData<ApiResponse<UserResponse>> register(
            @Body RegisterRequest body
            );

    @POST("user/login")
    LiveData<ApiResponse<MeUserResponse>> login(
            @Body LoginRequest body
            );

    @PUT("user")
    LiveData<ApiResponse<UserResponse>> updateUser(
            @Body UpdateUserRequest body
            );

    @POST("user/addfriend")
    LiveData<ApiResponse<UserResponse>> confirmAddFriend(
            @Body ConfirmAddFriendRequest body
    );

    @POST("user/deletefriend")
    LiveData<ApiResponse<UserResponse>> deleteFriend(
            @Body DeleteFriendRequest body
            );
}
