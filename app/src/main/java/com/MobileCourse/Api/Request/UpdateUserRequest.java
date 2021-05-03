package com.MobileCourse.Api.Request;

public class UpdateUserRequest {
    String weixinId;
    String username;
    String avatar;

    public UpdateUserRequest(String weixinId, String username,String avatar) {
        this.weixinId = weixinId;
        this.username = username;
        this.avatar =avatar;
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
