package com.MobileCourse.WebSocket;

import com.MobileCourse.Models.Message;
import com.tinder.scarlet.Scarlet;
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter;
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory;
import com.tinder.scarlet.websocket.okhttp.OkHttpClientUtils;

import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MessageService {
    private static MessageService instance;
    private MessageApi messageApi;

    public static MessageService getInstance(){
        if(instance==null){
            instance = new MessageService();
        }
        return instance;
    }

    private MessageService(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        MessageApi client = new Scarlet.Builder()
                .webSocketFactory(OkHttpClientUtils.newWebSocketFactory(okHttpClient, "ws://echo.websocket.org"))
                .addMessageAdapterFactory(new GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(new RxJava2StreamAdapterFactory())
                .build().create(MessageApi.class);
    }

    public MessageApi getMessageApi(){
        return this.messageApi;
    }

}
