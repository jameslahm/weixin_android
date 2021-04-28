package com.MobileCourse.Api;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<T extends CommonResponse> implements CallAdapter<T, LiveData<ApiResponse<T>>> {
    Type responseType;

    public LiveDataCallAdapter(Type responseType){
        this.responseType = responseType;
    }

    @Override
    public Type responseType(){
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(final Call<T> call){
        return new LiveData<ApiResponse<T>>(){
            @Override
            protected void onActive(){
                super.onActive();
                final ApiResponse apiResponse = new ApiResponse();
                if(!call.isExecuted()){
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            postValue(apiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
                            postValue(apiResponse.create(t));
                        }
                    });
                }
            }
        };
    }
}
