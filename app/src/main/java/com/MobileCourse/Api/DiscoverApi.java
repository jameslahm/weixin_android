package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Request.CreateDiscoverRequest;
import com.MobileCourse.Api.Request.LikeDiscoverRequest;
import com.MobileCourse.Api.Request.ReplyDiscoverRequest;
import com.MobileCourse.Api.Request.UnLikeDiscoverRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.DiscoverResponse;
import com.MobileCourse.Api.Response.GetDiscovesrResponse;
import com.MobileCourse.Models.Discover;
import com.MobileCourse.Repositorys.DiscoverRepository;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DiscoverApi {
    @GET("discover")
    LiveData<ApiResponse<GetDiscovesrResponse>> getDiscovers(

    );

    @POST("discover/like")
    LiveData<ApiResponse<DiscoverResponse>> likeDiscover(
            @Body LikeDiscoverRequest body
            );

    @POST("discover/unlike")
    LiveData<ApiResponse<DiscoverResponse>> unLikeDiscover(
            @Body UnLikeDiscoverRequest body
            );

    @POST("discover/reply")
    LiveData<ApiResponse<DiscoverResponse>> replyDiscover(
            @Body ReplyDiscoverRequest body
            );

    @POST("discover")
    LiveData<ApiResponse<DiscoverResponse>> createDiscover(
            @Body CreateDiscoverRequest body
            );
}
