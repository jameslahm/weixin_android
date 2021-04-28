package com.MobileCourse.Api;

import android.content.Context;

import com.MobileCourse.Utils.Constants;
import com.google.gson.Gson;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient client;

    private static void initOkHttpClient(Context context){
        CookieHandler cookieHandler = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client =  new OkHttpClient().newBuilder()
        .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.READ_TIMEOUT,TimeUnit.SECONDS)
        .writeTimeout(Constants.WRITE_TIMEOUT,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true).cookieJar(new JavaNetCookieJar(cookieHandler)).addInterceptor(logging)
        .build();
        ApiService.client = client;
    }

    private static Retrofit retrofit;
    private static UserApi userApi;

    public static void  init(Context context){
        initOkHttpClient(context);
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build();
        userApi = retrofit.create(UserApi.class);
    }

    public static UserApi getUserApi() {
        return userApi;
    }
}
