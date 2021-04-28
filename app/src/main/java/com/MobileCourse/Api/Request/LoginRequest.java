package com.MobileCourse.Api.Request;

public class LoginRequest {
    String weixinId;
    String password;

    public LoginRequest(String weixinId, String password) {
        this.weixinId = weixinId;
        this.password = password;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
