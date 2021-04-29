package com.MobileCourse.Api.Response;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.MobileCourse.Models.Friend;
import com.MobileCourse.Repositorys.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse extends CommonResponse {
    String username;
    String id;
    String weixinId;

    List<Friend> friends;
    String timeLineSyncId;

    String avatar;

    public UserResponse(String username, String id, String weixinId, List<Friend> friends, String timeLineSyncId, String avatar) {
        this.username = username;
        this.id = id;
        this.weixinId = weixinId;
        this.friends = friends;
        this.timeLineSyncId = timeLineSyncId;
        this.avatar = avatar;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static UserResponse fromMeUserResponse(MeUserResponse meUserResponse){
        List<Friend> friends = meUserResponse.friends.stream().map((friendDetail -> {
            return friendDetail.getFriend();
        })).collect(Collectors.toList());
        return new UserResponse(meUserResponse.username,meUserResponse.id,meUserResponse.weixinId,
                friends,meUserResponse.timeLineSyncId,meUserResponse.avatar);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getTimeLineSyncId() {
        return timeLineSyncId;
    }

    public void setTimeLineSyncId(String timeLineSyncId) {
        this.timeLineSyncId = timeLineSyncId;
    }
}
