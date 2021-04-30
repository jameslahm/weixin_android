package com.MobileCourse.Repositorys;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Daos.ApplicationDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Application;
import com.MobileCourse.Models.ApplicationMessage;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.WeixinApplication;

import java.util.List;

public class ApplicationRepository {
    private static ApplicationRepository instance;

    private ApplicationDao applicationDao;
    private UserDao userDao;

    public static ApplicationRepository getInstance(Context context){
        if(instance==null){
            instance = new ApplicationRepository(context);
        }
        return instance;
    }

    private ApplicationRepository(Context context){
        applicationDao = WeiXinDatabase.getInstance(context).getApplicationDao();
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
    }

    public void insertApplication(ApplicationMessage applicationMessage){
        String from = applicationMessage.getFrom();
        String content = applicationMessage.getContent();
        long timestamp = applicationMessage.getTimestamp();
        Application application = new Application(
                from,
                applicationMessage.getUser().getUsername(),
                content,
                applicationMessage.getUser().getAvatar(),
                timestamp,
                true,
                applicationMessage.getTo()
        );
        applicationDao.insertApplication(application);
        userDao.insertUser(applicationMessage.getUser());
    }

    public LiveData<List<Application>> getApplications(){
        return applicationDao.getApplications();
    }

    public void updateRead(){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            applicationDao.updateRead();
        });
    }
}
