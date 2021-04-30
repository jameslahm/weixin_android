package com.MobileCourse.WebSocket.Request;

import com.MobileCourse.Utils.Constants;

public class LoginMessage extends CommonRequest {
    String weixinId;

    String password;

    public LoginMessage(String weixinId, String password) {
        this.weixinId = weixinId;
        this.password = password;
        this.bizType = Constants.MESSAGE_USER_LOGIN;
    }
}
