package com.MobileCourse.Utils;

import androidx.annotation.Nullable;

 public class Constants {
//    public static final String BASE_URL = "http://139.196.81.14:8000";
//    public static final String WS_BASE_URL = "ws://139.196.81.14:7999";
    public static final String UPLOAD_BASE__URL = "http://139.196.81.14:7998";

    public static final String BASE_URL = "http://183.172.207.89:7000";
    public static final String WS_BASE_URL = "ws://183.172.207.89:5200/ws";

    public static final int CONNECTION_TIMEOUT = 10;

    public static final int READ_TIMEOUT = 2;
    public static final int WRITE_TIMEOUT = 2;

    public static final int USER_REFRESH_TIME = 60*60;

    public static final String ACTION_SEND_MESSAGE = "发消息";

    public static final String ACTION_ADD_FRIEND = "添加好友";

    public static final String BIZ_TYPE = "bizType";

    public static final class ContentType {
        public static final String TEXT = "TEXT";
        public static final String AUDIO = "AUDIO";
        public static final String VIDEO = "VIDEO";
        public static final String IMAGE = "IMAGE";
        public static final String LOCATION = "LOCATION";
    }

    public static final class MessageType {
        public static final String APPLICATION = "APPLICATION";
        public static final String SINGLE = "SINGLE";
        public static final String GROUP = "GROUP";
        public static final String INVITE = "INVITE";
        public static final String CONFIRM = "CONFIRM";
    }

    public static final String CHANNEL_ID = "channel";

    public static final String NOTIFICATION_TITLE = "WeChat";

    public static final String NOTIFICATION_CONTENT = "You have received a new message";

    public static int NOTIFICATION_ID = 0;

    public static final String MESSAGE_USER_LOGIN  ="USER_LOGIN";

    // MessageAdapter Types
     public static final int SEND = 0x10;
     public static final int RECEIVE = 0x0;

     public static final int TEXT = 0x1;
     public static final int AUDIO = 0x2;
     public static final int VIDEO = 0x3;
     public static final int IMAGE = 0x4;
     public static final int LOCATION = 0x5;

     public static final String GROUP_RESPONSE_CONTNET = "I have start a new Group";
}


