package com.MobileCourse.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Utils.AppExecutors;

import java.sql.Time;
import java.util.List;

public class TimeLineRepository {
    private static String tag = "TimeLineRepository";

    private MeDao meDao;
    private UserDao userDao;
    private TimeLineDao timeLineDao;

    private static TimeLineRepository instance;

    public static TimeLineRepository getInstance(Context context){
        if(instance==null){
            instance = new TimeLineRepository(context);
        }
        return instance;
    }

    public TimeLineRepository(Context context){
        meDao = WeiXinDatabase.getInstance(context).getMeDao();
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
        timeLineDao = WeiXinDatabase.getInstance(context).getTimeLineDao();
        init();
    }

    void init(){

    }

    public LiveData<List<TimeLine>> getTimeLines(){
        return timeLineDao.getTimeLines();
    }

    public void insertMessage(TimeLine timeLine,Message message){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLine.getMessages().add(message);
            timeLineDao.insertTimeLine(timeLine);
        });
    }

    public void insertTimeLine(TimeLine timeLine){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLineDao.insertTimeLine(timeLine);
        });
    }

    public LiveData<TimeLine> getTimeLine(String id){
        return timeLineDao.getTimeLine(id);
    }

    public void updateLastCheckTimestamp(String timeLineId,long timestamp){
        timeLineDao.updateLastCheckTimestamp(timeLineId,timestamp);
    }
}
