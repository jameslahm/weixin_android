package com.MobileCourse.Api.Request;

public class UpdateUserRequest {
    String weixinId;
    String username;

    public UpdateUserRequest(String weixinId, String username) {
        this.weixinId = weixinId;
        this.username = username;
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
