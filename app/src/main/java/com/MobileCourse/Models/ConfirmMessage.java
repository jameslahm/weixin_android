package com.MobileCourse.Models;

public class ConfirmMessage extends Message {
    User user;

    public ConfirmMessage(String content, String contentType, String messageType, int timestamp, String from, String to) {
        super(content, contentType, messageType, timestamp, from, to);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
