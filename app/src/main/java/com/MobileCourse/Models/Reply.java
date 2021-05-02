package com.MobileCourse.Models;

public class Reply {
    User user;
    String content;
    long timestamp;

    public Reply(User user, String content, long timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
