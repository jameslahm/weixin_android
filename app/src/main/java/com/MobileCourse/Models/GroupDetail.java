package com.MobileCourse.Models;

import java.util.List;

public class GroupDetail {

    String id;

    String name;

    String timeLineSavedId;

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

    public String getTimeLineSavedId() {
        return timeLineSavedId;
    }

    public void setTimeLineSavedId(String timeLineSavedId) {
        this.timeLineSavedId = timeLineSavedId;
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
        this.timeLineSavedId = timeLineSyncId;
        this.avatar = avatar;
        this.members = members;
    }

    public static GroupDetail fromGroupAndMembers(Group group, List<User> members){
        return new GroupDetail(
                group.id,
                group.name,
                group.timeLineSavedId,
                group.avatar,
                members
        );
    }
}
