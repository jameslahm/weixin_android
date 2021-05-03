package com.MobileCourse.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.TimeLineResponse;
import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

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
            timeLine.setLastSpeakTime(MiscUtil.formatTimestamp(message.getTimestamp()));
            timeLine.setLastSpeak(message.getContent());
            timeLine.getMessages().add(message);
            timeLineDao.insertTimeLine(timeLine);
        });
    }

    public void insertTimeLine(TimeLine timeLine){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLineDao.insertTimeLine(timeLine);
        });
    }

    // isSync only true when sync messages;
    public LiveData<Resource<TimeLine>> syncTimeLineInSingle(User user){
        return new NetworkBoundResource<TimeLine, TimeLineResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<TimeLine> loadFromDb() {
                return timeLineDao.getTimeLine(user.getId());
            }

            @Override
            protected boolean shouldFetch(@NotNull TimeLine data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<TimeLineResponse>> createCall() {
                return ApiService.getTimeLineApi().getTimeLineInSingle(user.getId());
            }

            @Override
            protected void saveCallResult(@NotNull TimeLineResponse timeLineResponse) {
                super.saveCallResult(timeLineResponse);
                // User case
                timeLineDao.insertTimeLine(TimeLine.fromTimeLineSingleResponse(user,timeLineResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<TimeLine>> syncTimeLineInGroup(Group group){
        return new NetworkBoundResource<TimeLine, TimeLineResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<TimeLine> loadFromDb() {
                return timeLineDao.getTimeLine(group.getId());
            }

            @Override
            protected boolean shouldFetch(@NotNull TimeLine data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<TimeLineResponse>> createCall() {
                return ApiService.getTimeLineApi().getTimeLineInGroup(group.getId());
            }

            @Override
            protected void saveCallResult(@NotNull TimeLineResponse timeLineResponse) {
                super.saveCallResult(timeLineResponse);
                // User case
                timeLineDao.insertTimeLine(TimeLine.fromTimeLineGroupResponse(group,timeLineResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<TimeLine> getTimeLineById(String id){
        return timeLineDao.getTimeLine(id);
    }

    public void updateLastCheckTimestamp(String timeLineId,long timestamp){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLineDao.updateLastCheckTimestamp(timeLineId,timestamp);
        });
    }

    public void deleteTimeLine(String timeLineId){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLineDao.deleteTimeLine(timeLineId);
        });
    }

    public void deleteTimeLineMessages(String id){
        AppExecutors.getInstance().getDiskIO().execute(()->{
            timeLineDao.deleteTimeLineMessages(id);
        });
    }
}
