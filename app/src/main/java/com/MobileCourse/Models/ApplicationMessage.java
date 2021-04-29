package com.MobileCourse.Models;

public class ApplicationMessage extends Message {
    User user;

    public ApplicationMessage(String content, String contentType, String messageType, int timestamp, String from, String to) {
        super(content, contentType, messageType, timestamp, from, to);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
