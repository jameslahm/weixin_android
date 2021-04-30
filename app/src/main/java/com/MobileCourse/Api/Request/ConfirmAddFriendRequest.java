package com.MobileCourse.Api.Request;

public class ConfirmAddFriendRequest {
    String friendId;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public ConfirmAddFriendRequest(String friendId) {
        this.friendId = friendId;
    }
}
