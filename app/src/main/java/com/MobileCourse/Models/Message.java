package com.MobileCourse.Models;

import android.widget.inline.InlineContentView;

public class Message {

    String id;

    String content;

    String contentType;

    String messageType;

    int timestamp;

    String from;

    String to;


    public Message(String content,String contentType,
                   String messageType,int timestamp,
                   String from,String to){
        this.content = content;
        this.contentType = contentType;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
    }

    public static Message fromInviteInToGroupMessage(InviteInToGroupMessage inviteInToGroupMessage){
        String content = inviteInToGroupMessage.content;
        String contentType = inviteInToGroupMessage.contentType;
        String messageType = inviteInToGroupMessage.messageType;
        int timestamp = inviteInToGroupMessage.timestamp;
        String from = inviteInToGroupMessage.from;
        String to = inviteInToGroupMessage.to;
        Message message = new Message(
                content,contentType,messageType,timestamp,from,to
        );
        return message;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }
}

