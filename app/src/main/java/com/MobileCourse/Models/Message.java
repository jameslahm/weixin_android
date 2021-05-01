package com.MobileCourse.Models;

import android.widget.inline.InlineContentView;

import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Repositorys.GroupRepository;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;

public class Message {

    String id;

    String content;

    String contentType;

    String messageType;

    long timestamp;

    String from;

    String to;



    public Message(String content,String contentType,
                   String messageType,long timestamp,
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
        long timestamp = inviteInToGroupMessage.timestamp;
        String from = inviteInToGroupMessage.from;
        Message message = new Message(
                content,contentType,messageType,timestamp,from,inviteInToGroupMessage.to
        );
        return message;
    }

    public static Message fromGroupResponse(GroupResponse groupResponse){
        String content = Constants.GROUP_RESPONSE_CONTNET;
        String contentType = Constants.ContentType.TEXT;
        String messageType = Constants.MessageType.GROUP;
        long timestamp = groupResponse.getTime();
        // FIXME
        String from = groupResponse.getMembers().get(0).getId();

        Message message = new Message(
                content,contentType,messageType,timestamp,from,groupResponse.getId()
        );
        return message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
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


    public static Message fromApplication(Application application) {
        String content = application.getContent();
        String contentType = Constants.ContentType.TEXT;
        String messageType = Constants.MessageType.SINGLE;
        long timestamp = MiscUtil.getCurrentTimestamp();
        String from = application.getFrom();
        Message message = new Message(
                content,
                contentType,
                messageType,
                timestamp,
                from,
                application.to
        );
        return message;
    }
}

