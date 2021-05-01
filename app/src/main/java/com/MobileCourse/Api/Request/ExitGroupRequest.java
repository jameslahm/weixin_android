package com.MobileCourse.Api.Request;

public class ExitGroupRequest {
    String groupId;

    public ExitGroupRequest(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
