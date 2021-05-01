package com.MobileCourse.Api.Request;

import java.util.List;

public class InviteInToGroupRequest {
    String groupId;

    List<String> friendIds;

    public InviteInToGroupRequest(String groupId, List<String> friends) {
        this.groupId = groupId;
        this.friendIds = friends;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriends(List<String> friendIds) {
        this.friendIds = friendIds;
    }
}
