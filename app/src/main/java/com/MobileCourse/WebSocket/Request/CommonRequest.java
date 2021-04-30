package com.MobileCourse.WebSocket.Request;

public class CommonRequest {
    String bizType = "MESSAGE_CREATE";

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
