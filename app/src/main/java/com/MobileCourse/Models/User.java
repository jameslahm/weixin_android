package com.MobileCourse.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = "User")
public class User {

    @PrimaryKey
    @NotNull
    String id;

    String username;

    String weixinId;

    String timeLineSyncId;

    List<Friend> friends;

    Long cachedTimestamp;

    public User(String id,String username,String weixinId,String timeLineSyncId,List<Friend> friends){
        this.id = id;
        this.username = username;
        this.weixinId = weixinId;
        this.timeLineSyncId = timeLineSyncId;
        this.friends = friends;
        this.cachedTimestamp = cachedTimestamp;
    }

    public static User fromUserResponse(UserResponse userResponse){
        User user = new User(userResponse.getId(),userResponse.getUsername(),
                userResponse.getWeixinId(),userResponse.getTimeLineSyncId(),
                userResponse.getFriends());
        user.setCachedTimestamp(MiscUtil.getCurrentTimestamp());
        return user;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getTimeLineSyncId() {
        return timeLineSyncId;
    }

    public void setTimeLineSyncId(String timeLineSyncId) {
        this.timeLineSyncId = timeLineSyncId;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public Long getCachedTimestamp() {
        return cachedTimestamp;
    }

    public void setCachedTimestamp(Long cachedTimestamp) {
        this.cachedTimestamp = cachedTimestamp;
    }
}
