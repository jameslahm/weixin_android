package com.MobileCourse.Models;

public class InviteInToGroupMessage extends Message {
    Group group;

    public InviteInToGroupMessage(String content, String contentType, String messageType, int timestamp, String from, String to) {
        super(content, contentType, messageType, timestamp, from, to);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
