package com.MobileCourse.Api.Request;

public class RegisterRequest {
    String weixinId;
    String username;
    String password;

    public RegisterRequest(String weixinId, String username, String password) {
        this.weixinId = weixinId;
        this.username = username;
        this.password = password;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
