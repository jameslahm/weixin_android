package com.MobileCourse.Api.Response;

import com.MobileCourse.Models.User;
import com.MobileCourse.WebSocket.Request.CommonRequest;

import java.util.List;

public class GroupResponse extends CommonResponse {
    String id;

    String name;

    String avatar;

    List<UserResponse> members;

    String timeLineSavedId;

    public String getTimeLineSavedId() {
        return timeLineSavedId;
    }

    public void setTimeLineSavedId(String timeLineSavedId) {
        this.timeLineSavedId = timeLineSavedId;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<UserResponse> getMembers() {
        return members;
    }

    public void setMembers(List<UserResponse> members) {
        this.members = members;
    }

    public GroupResponse(String id, String name, String avatar, List<UserResponse> members) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.members = members;
    }
}
