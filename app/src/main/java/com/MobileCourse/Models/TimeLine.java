package com.MobileCourse.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Api.Response.TimeLineResponse;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "TimeLine")
public class TimeLine {

    @PrimaryKey
    @NotNull
    String id;

    // username if Single
    // groupname if Group
    String name;

    // lastSpeak
    String lastSpeak;

    String avatar;

    String lastSpeakTime;

    long lastCheckTimestamp;

    // Only SINGLE or GROUP
    String messageType;

    List<Message> messages;

    public long getLastCheckTimestamp() {
        return lastCheckTimestamp;
    }

    public void setLastCheckTimestamp(long lastCheckTimestamp) {
        this.lastCheckTimestamp = lastCheckTimestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public TimeLine(@NotNull String id, String name, String lastSpeak, String avatar, String lastSpeakTime, long lastCheckTimestamp ,
                    String messageType, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.lastSpeak = lastSpeak;
        this.avatar = avatar;
        this.lastSpeakTime = lastSpeakTime;
        this.lastCheckTimestamp = lastCheckTimestamp;
        this.messageType  = messageType;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSpeak() {
        return lastSpeak;
    }

    public void setLastSpeak(String lastSpeak) {
        this.lastSpeak = lastSpeak;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLastSpeakTime() {
        return lastSpeakTime;
    }

    public void setLastSpeakTime(String lastSpeakTime) {
        this.lastSpeakTime = lastSpeakTime;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }



    public static TimeLine fromInviteInToGroupMessage(InviteInToGroupMessage inviteInToGroupMessage){
        String id = inviteInToGroupMessage.group.getId();
        String name = inviteInToGroupMessage.group.name;
        String lastSpeak = inviteInToGroupMessage.content;
        String avatar = inviteInToGroupMessage.group.avatar;
        String lastSpeakTime = MiscUtil.formatTimestamp(inviteInToGroupMessage.timestamp);
        List<Message> messages = new ArrayList<>();
//        messages.add(Message.fromInviteInToGroupMessage(inviteInToGroupMessage));
        TimeLine timeLine = new TimeLine(
                id,name,lastSpeak,avatar,lastSpeakTime,inviteInToGroupMessage.timestamp,
                Constants.MessageType.GROUP,messages
        );
        return timeLine;
    }

    public static TimeLine fromGroupResponse(GroupResponse groupResponse){
        String id = groupResponse.getId();
        String name = groupResponse.getName();
        String lastSpeak = Constants.GROUP_RESPONSE_CONTNET;
        String avatar = groupResponse.getAvatar();
        String lastSpeakTime = MiscUtil.formatTimestamp(groupResponse.getTime());
        List<Message> messages = new ArrayList<>();

//        messages.add(Message.fromGroupResponse(groupResponse));

        TimeLine timeLine = new TimeLine(
                id,name,lastSpeak,avatar,lastSpeakTime,groupResponse.getTime(),
                Constants.MessageType.GROUP,messages
        );
        return timeLine;
    }

    public static TimeLine fromApplication(Application application){
        List<Message> messages = new ArrayList<>();
//        messages.add(Message.fromApplication(application));

        long timestamp = MiscUtil.getCurrentTimestamp();
        String time = MiscUtil.formatTimestamp(timestamp);
        TimeLine timeLine = new TimeLine(application.getFrom(),
                application.getUsername(),
                application.getContent(),
                application.getAvatar(),
                time,
                timestamp,
                Constants.MessageType.SINGLE,
                messages
        );
        return timeLine;
    }


    public static TimeLine fromConfirmMessage(ConfirmMessage message){
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        TimeLine timeLine = new TimeLine(
                message.getFrom(),
                message.getUser().getUsername(),
                message.getContent(),
                message.getUser().getAvatar(),
                MiscUtil.formatTimestamp(message.getTimestamp()),
                message.getTimestamp()-1,
                Constants.MessageType.SINGLE,
                messages
        );
        return timeLine;
    }

    public static TimeLine fromTimeLineSingleResponse(User user,TimeLineResponse response){
        List<Message> messages = response.getMessages();
        String lastSpeak;
        String lastSpeakTime;

        if(messages.size()>1){
            Message lastMessage = messages.get(messages.size()-1);
            lastSpeak = lastMessage.getContent();
            lastSpeakTime  = MiscUtil.formatTimestamp(lastMessage.getTimestamp());
        } else {
            lastSpeak = "";
            lastSpeakTime = MiscUtil.formatTimestamp(MiscUtil.getCurrentTimestamp());
        }

        TimeLine timeLine = new TimeLine(
                user.getId(),
                user.getUsername(),
                lastSpeak,
                user.getAvatar(),
                lastSpeakTime,
                MiscUtil.getCurrentTimestamp(),
                Constants.MessageType.SINGLE,
                messages);

        return timeLine;
    }

    public static TimeLine fromTimeLineGroupResponse(Group group,TimeLineResponse response){
        List<Message> messages = response.getMessages();
        String lastSpeak;
        String lastSpeakTime;

        if(messages.size()>1){
            Message lastMessage = messages.get(messages.size()-1);
            lastSpeak = lastMessage.getContent();
            lastSpeakTime  = MiscUtil.formatTimestamp(lastMessage.getTimestamp());
        } else {
            lastSpeak = "";
            lastSpeakTime = MiscUtil.formatTimestamp(MiscUtil.getCurrentTimestamp());
        }

        TimeLine timeLine = new TimeLine(
                group.getId(),
                group.getName(),
                lastSpeak,
                group.getAvatar(),
                lastSpeakTime,
                MiscUtil.getCurrentTimestamp(),
                Constants.MessageType.GROUP,
                messages);

        return timeLine;
    }

}
