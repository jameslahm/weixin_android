package com.MobileCourse.Utils;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

public class AppExecutors {
    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if(instance==null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private final Executor diskIO = Executors.newSingleThreadExecutor();

    private final Executor mainThread = new MainThreadExecutor();

    public Executor getDiskIO(){
        return this.diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NotNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
