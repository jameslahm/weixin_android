package com.MobileCourse.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

// 申请成为好友消息
@Entity(tableName = "Application")
public class Application {

    @PrimaryKey
    @NotNull
    String from;
    String content;
    String avatar;
    long timestamp;
    String username;

    boolean unRead;

    @NotNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NotNull String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isUnRead() {
        return unRead;
    }

    public void setUnRead(boolean unRead) {
        this.unRead = unRead;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Application(@NotNull String from,String username, String content,String avatar, long timestamp, boolean unRead) {
        this.from = from;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
        this.avatar = avatar;
        this.unRead =unRead;
    }
}
