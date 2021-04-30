package com.MobileCourse.Repositorys;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.MobileCourse.Daos.GroupDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.GroupDetail;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.WeixinApplication;

public class GroupRepository {
    private static GroupRepository instance;
    private GroupDao groupDao;
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

}
