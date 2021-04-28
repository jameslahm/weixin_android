package com.MobileCourse.Utils;

import android.view.View;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class EventListenerUtil {

    public static void addViewsListener(Fragment fragment, int viewId, View.OnClickListener onClickListener) {
        View view = fragment.getView().findViewById(viewId);
        view.setOnClickListener(onClickListener);
    }

    public static void addViewsListener(Fragment fragment, int[] viewIds, View.OnClickListener onClickListener) {
        for (int viewId : viewIds) {
            View view = fragment.getView().findViewById(viewId);
            view.setOnClickListener(onClickListener);
        }
    }


}
