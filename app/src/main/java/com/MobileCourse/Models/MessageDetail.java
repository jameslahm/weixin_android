package com.MobileCourse.Models;

public class MessageDetail {
    String content;

    String contentType;

    long timestamp;

    boolean isSend;

    String avatar;

    String username;

    public MessageDetail(String content, String contentType, long timestamp, boolean isSend, String avatar, String username) {
        this.content = content;
        this.contentType = contentType;
        this.timestamp = timestamp;
        this.isSend = isSend;
        this.avatar = avatar;
        this.username = username;
    }

    public static MessageDetail fromMessageAndUser(Message message,User me, User target){
        String content = message.content;
        String contentType = message.contentType;
        long timestamp = message.timestamp;
        boolean isSend = (message.getFrom() .equals(me.getId()));
        String avatar = isSend ? me.getAvatar() : target.getAvatar();
        String username = isSend ? me.getUsername():target.getUsername();
        return new MessageDetail(content,contentType,timestamp,isSend,avatar,username);
    }

    public static MessageDetail fromMessageAndGroup(Message message,User me, Group target){
        String content = message.content;
        String contentType = message.contentType;
        long timestamp = message.timestamp;
        boolean isSend = (message.getFrom().equals(me.getId()));
        String avatar = isSend ? me.getAvatar() : target.getAvatar();
        String username = isSend ? me.getUsername():target.getName();
        return new MessageDetail(content,contentType,timestamp,isSend,avatar,username);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
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
}
