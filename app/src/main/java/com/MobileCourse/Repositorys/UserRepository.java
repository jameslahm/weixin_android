package com.MobileCourse.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.User;
import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

public class UserRepository {
    private static UserRepository instance;
    private UserDao userDao;

    public static UserRepository getInstance(Context context){
        if(instance==null){
            instance = new UserRepository(context);
        }
        return instance;
    }

    private UserRepository(Context context){
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
    }

    public LiveData<Resource<User>> searchUserApi(final String weixinId){
        return new NetworkBoundResource<User, UserResponse>(AppExecutors.getInstance()) {
            @NotNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.getUserByWeixinId(weixinId);
            }

            @Override
            protected boolean shouldFetch(@NotNull User user) {
                Long currentTimeStamp = MiscUtil.getCurrentTimestamp();
                if((currentTimeStamp-user.getCachedTimestamp())> Constants.USER_REFRESH_TIME){
                    return true;
                }else {
                    return false;
                }
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                return ApiService.getUserApi().getUserByWeixinId(weixinId);
            }

            @Override
            protected void saveCallResult(@NotNull UserResponse userResponse) {
                userDao.insertUser(User.fromUserResponse(userResponse));
            }
        }.getAsLiveData();
    }
}
