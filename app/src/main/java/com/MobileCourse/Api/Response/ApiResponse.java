package com.MobileCourse.Api.Response;


import retrofit2.Response;

public class ApiResponse<T extends CommonResponse> {
    public ApiErrorResponse<T> create(Throwable error){
        return new ApiErrorResponse<T>(error.getMessage());
    }

    public ApiResponse<T> create(Response<T> response){
        if(response.isSuccessful()){
            T body = response.body();
            if(body.isSuccess()){
                return new ApiSuccessResponse<>(body);
            } else {
                return new ApiErrorResponse<>(body.getMsg());
            }
        } else {
            return new ApiErrorResponse<>("Unknown Error");
        }
    }

    public static class ApiErrorResponse<T extends CommonResponse> extends ApiResponse<T> {
        private String errorMessage;
        ApiErrorResponse(String errorMessage){
            this.errorMessage = errorMessage;
        }
        public String getErrorMessage(){
            return this.errorMessage;
        }
    }

    public static class ApiSuccessResponse<T extends CommonResponse> extends ApiResponse<T> {
        T body;
        ApiSuccessResponse(T body){this.body = body;};
        public T getBody(){
            return this.body;
        }
    }


}
