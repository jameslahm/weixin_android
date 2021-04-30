package com.MobileCourse.Models;

public class InviteInToGroupMessage extends Message {
    GroupDetail group;

    public InviteInToGroupMessage(String content, String contentType, String messageType, int timestamp, String from, String to) {
        super(content, contentType, messageType, timestamp, from, to);
    }

    public GroupDetail getGroup() {
        return group;
    }

    public void setGroupDetail(GroupDetail group) {
        this.group = group;
    }
}
