package com.MobileCourse.Api.Request;

public class DeleteFriendRequest {
    String friendId;

    public DeleteFriendRequest(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
