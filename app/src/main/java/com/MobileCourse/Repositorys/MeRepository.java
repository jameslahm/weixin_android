package com.MobileCourse.Repositorys;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.User;
import com.MobileCourse.Utils.AppExecutors;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class MeRepository {
    private static String tag = "MeRepository";

    private MeDao meDao;
    private UserDao userDao;

    private static MeRepository instance;

    private MediatorLiveData<User> userLiveData = new MediatorLiveData<>();

    public static MeRepository getInstance(Context context){
        if(instance==null){
            instance = new MeRepository(context);
        }
        return instance;
    }

    public MeRepository(Context context){
        meDao = WeiXinDatabase.getInstance(context).getMeDao();
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
        init();
    }

    void init(){
        LiveData<Me> meLiveData = meDao.getMe();
        userLiveData.addSource(meLiveData,(me -> {
            if(me!=null){
                userLiveData.addSource(userDao.getUserById(me.getId()),(user -> {
                    userLiveData.setValue(user);
                }));
            } else {
                userLiveData.setValue(null);
            }
        }));
    }

    public LiveData<User> getMe(){
        return userLiveData;
    }

    public void deleteMe() {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            meDao.deleteMe();
        });
    }
}
