package com.MobileCourse.Models;

public class Friend {
    String id;
    String timeLineSavedId;
    String nickName;

    public static class FriendDetail {
        Friend friend;
        User user;

        public FriendDetail(Friend friend, User user) {
            this.friend = friend;
            this.user = user;
        }

        public Friend getFriend() {
            return friend;
        }

        public void setFriend(Friend friend) {
            this.friend = friend;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeLineSavedId() {
        return timeLineSavedId;
    }

    public void setTimeLineSavedId(String timeLineSavedId) {
        this.timeLineSavedId = timeLineSavedId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
