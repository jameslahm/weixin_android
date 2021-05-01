package com.MobileCourse.Repositorys;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Request.ConfirmAddFriendRequest;
import com.MobileCourse.Api.Request.LoginRequest;
import com.MobileCourse.Api.Request.RegisterRequest;
import com.MobileCourse.Api.Request.UpdateUserRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;
import com.MobileCourse.Api.Response.MeUserResponse;
import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.WebSocket.MessageApi;
import com.MobileCourse.WebSocket.Request.CreateMessage;
import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.User;
import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.WebSocket.MessageService;
import com.MobileCourse.WebSocket.Request.LoginMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    private static UserRepository instance;
    private UserDao userDao;
    private MeDao meDao;

    public static UserRepository getInstance(Context context){
        if(instance==null){
            instance = new UserRepository(context);
        }
        return instance;
    }

    private UserRepository(Context context){
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
        meDao = WeiXinDatabase.getInstance(context).getMeDao();
    }

    public LiveData<Resource<User>> getUserByWeixinId(final String weixinId){
        return new NetworkBoundResource<User, UserResponse>(AppExecutors.getInstance()) {
            @NotNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.getUserByWeixinId(weixinId);
            }

            @Override
            protected boolean shouldFetch(@NotNull User user) {
                if(user==null){
                    return true;
                }
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

    public LiveData<Resource<User>> register(String weixinId,String username,String password){
        return new NetworkBoundResource<User,UserResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<User> loadFromDb() {

                return userDao.getUserByWeixinId(weixinId);
            }

            @Override
            protected boolean shouldFetch(@NotNull User data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                RegisterRequest registerRequest = new RegisterRequest(weixinId,username,password);
                return ApiService.getUserApi().register(registerRequest);
            }

            @Override
            protected void saveCallResult(@NotNull UserResponse userResponse) {
                userDao.insertUser(User.fromUserResponse(userResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> login(String weixinId,String password){
        MessageService.getInstance().getMessageApi().sendLoginMessage(new LoginMessage(
                weixinId,password
        ));

        return new NetworkBoundResource<User, MeUserResponse>(AppExecutors.getInstance()){

            @NotNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.getUserByWeixinId(weixinId);
            }

            @Override
            protected boolean shouldFetch(@NotNull User data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<MeUserResponse>> createCall() {
                LoginRequest loginRequest = new LoginRequest(weixinId,password);
                return ApiService.getUserApi().login(loginRequest);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveCallResult(@NotNull MeUserResponse meUserResponse) {
                userDao.insertUser(User.fromUserResponse(UserResponse.fromMeUserResponse(meUserResponse)));
                List<User> users = meUserResponse.getFriends().stream().map(friendDetail -> {
                    return friendDetail.getUser();
                }).collect(Collectors.toList());
                userDao.insertUsers(users);
                meDao.insertMe(new Me(meUserResponse.getId()));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> updateUser(final String id,final String weixinId,final String username){
        return new NetworkBoundResource<User, UserResponse>(AppExecutors.getInstance()) {
            @NotNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.getUserById(id);
            }

            @Override
            protected boolean shouldFetch(@NotNull User data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                return ApiService.getUserApi().updateUser(new UpdateUserRequest(weixinId,username));
            }

            @Override
            protected void saveCallResult(@NotNull UserResponse userResponse) {
                userDao.insertUser(User.fromUserResponse(userResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<User>>> getUsersByIds(List<String> ids){
        return new NetworkBoundResource<List<User>, CommonResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return userDao.getUsersByIds(ids);
            }

            @Override
            protected boolean shouldFetch(@NotNull List<User> data) {
                return false;
            }

        }.getAsLiveData();
    }

    public void applyAddFriend(String friendId, String content){
        CreateMessage createMessage = new CreateMessage(
            content, Constants.ContentType.TEXT,Constants.MessageType.APPLICATION,
                friendId,MiscUtil.getCurrentTimestamp()
        );
        MessageService.getInstance().getMessageApi().sendMessage(createMessage);
    }

    public void confirmAddFriend(String friendId){
        ApiService.getUserApi().confirmAddFriend(new ConfirmAddFriendRequest(friendId));
    }

    public LiveData<User> getUserById(String id){
        return userDao.getUserById(id);
    }

    public void insertUser(User user){
        userDao.insertUser(user);
    }

    public void insertUsers(List<User> users){
        userDao.insertUsers(users);
    }
}
