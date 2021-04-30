package com.MobileCourse.Models;

import java.util.List;

public class GroupDetail {

    String id;

    String name;

    String timeLineSyncId;

    String avatar;

    List<User> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeLineSyncId() {
        return timeLineSyncId;
    }

    public void setTimeLineSyncId(String timeLineSyncId) {
        this.timeLineSyncId = timeLineSyncId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public GroupDetail(String id, String name, String timeLineSyncId, String avatar, List<User> members) {
        this.id = id;
        this.name = name;
        this.timeLineSyncId = timeLineSyncId;
        this.avatar = avatar;
        this.members = members;
    }
}
