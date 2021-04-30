package com.MobileCourse.WebSocket.Request;


public class CreateMessage extends CommonRequest {
    String content;

    String contentType;

    String messageType;

    String to;

    long timestamp;

    public CreateMessage(String content, String contentType, String messageType, String to, long timestamp) {
        this.content = content;
        this.contentType = contentType;
        this.messageType = messageType;
        this.to = to;
        this.timestamp = timestamp;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
