package com.MobileCourse.Api;


import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UploadResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApi {
    @Headers({"Authorization:jcnabckydusja"})
    @Multipart
    @POST("upload")
    LiveData<ApiResponse<UploadResponse>> uploadFile(
            @Part MultipartBody.Part file
            );
}
