package com.MobileCourse.Api.Request;

public class UpdateUserRequest {
    String weixinId;
    String username;
    String avatar;
    String password;

    public UpdateUserRequest(String weixinId, String username,String avatar, String password) {
        this.weixinId = weixinId;
        this.username = username;
        this.avatar =avatar;
        this.password = password;
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

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }
}
