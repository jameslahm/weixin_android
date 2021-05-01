package com.MobileCourse.Repositorys;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Request.CreateGroupRequest;
import com.MobileCourse.Api.Request.RegisterRequest;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Daos.GroupDao;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.GroupDetail;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.WeixinApplication;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupRepository {
    private static GroupRepository instance;
    private GroupDao groupDao;
    private TimeLineDao timeLineDao;
    private UserDao userDao;

    public static GroupRepository getInstance(Context context){
        if(instance==null){
            instance = new GroupRepository(context);
        }
        return instance;
    }

    private GroupRepository(Context context){
        groupDao = WeiXinDatabase.getInstance(context).getGroupDao();
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
        timeLineDao = WeiXinDatabase.getInstance(context).getTimeLineDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertGroup(GroupDetail groupDetail){
        Group group = Group.fromGroupDetail(groupDetail);
        AppExecutors.getInstance().getDiskIO().execute(()->{
            groupDao.insertGroup(group);
            userDao.insertUsers(groupDetail.getMembers());
        });
    }

    public LiveData<Group> getGroupById(String id){
        return groupDao.getGroupById(id);
    }

    public LiveData<Resource<Group>> createGroup(String name, List<String> members){
        return new NetworkBoundResource<Group, GroupResponse>(
                AppExecutors.getInstance()
        ){
            @NotNull
            @Override
            protected LiveData<Group> loadFromDb() {
                // FIXME elegant way
                return groupDao.getGroupById(name);
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<GroupResponse>> createCall() {
                CreateGroupRequest createGroupRequest = new CreateGroupRequest(name,members);
                return ApiService.getGroupApi().createGroup(createGroupRequest);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveCallResult(@NotNull GroupResponse groupResponse) {
                TimeLine timeLine = TimeLine.fromGroupResponse(groupResponse);
                timeLineDao.insertTimeLine(timeLine);
                groupDao.insertGroup(Group.fromGroupResponse(groupResponse));
            }

            @Override
            protected boolean shouldFetch(@NotNull Group data) {
                return true;
            }
        }.getAsLiveData();
    }
}
