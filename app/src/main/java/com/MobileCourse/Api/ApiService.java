package com.MobileCourse.Api;

import android.content.Context;

import com.MobileCourse.Utils.Constants;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient client;

    private static void initOkHttpClient(Context context){
        CookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client =  new OkHttpClient().newBuilder()
        .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.READ_TIMEOUT,TimeUnit.SECONDS)
        .writeTimeout(Constants.WRITE_TIMEOUT,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true).cookieJar(cookieJar).addInterceptor(logging)
        .build();
        ApiService.client = client;
    }

    private static Retrofit retrofit;
    private static UserApi userApi;
    private static GroupApi groupApi;

    public static void  init(Context context){
        initOkHttpClient(context);
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build();
        userApi = retrofit.create(UserApi.class);
        groupApi = retrofit.create(GroupApi.class);
    }

    public static UserApi getUserApi() {
        return userApi;
    }

    public static GroupApi getGroupApi() {
        return groupApi;
    }
}
