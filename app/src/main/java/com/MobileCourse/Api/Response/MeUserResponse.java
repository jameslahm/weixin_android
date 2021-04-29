package com.MobileCourse.Api.Response;

import com.MobileCourse.Models.Friend;

import java.util.List;

public class MeUserResponse extends CommonResponse {
    String username;
    String id;
    String weixinId;

    List<Friend.FriendDetail> friends;
    String timeLineSyncId;

    String avatar;

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

    public List<Friend.FriendDetail> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend.FriendDetail> friends) {
        this.friends = friends;
    }

    public String getTimeLineSyncId() {
        return timeLineSyncId;
    }

    public void setTimeLineSyncId(String timeLineSyncId) {
        this.timeLineSyncId = timeLineSyncId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MeUserResponse(String username, String id, String weixinId, List<Friend.FriendDetail> friends, String timeLineSyncId, String avatar) {
        this.username = username;
        this.id = id;
        this.weixinId = weixinId;
        this.friends = friends;
        this.timeLineSyncId = timeLineSyncId;
        this.avatar = avatar;
    }
}
