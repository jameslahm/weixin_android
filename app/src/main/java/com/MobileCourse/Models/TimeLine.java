package com.MobileCourse.Models;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    String to;

    List<Message> messages;

    public TimeLine(@NotNull String id, String name, String lastSpeak, String avatar, String lastSpeakTime, String to, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.lastSpeak = lastSpeak;
        this.avatar = avatar;
        this.lastSpeakTime = lastSpeakTime;
        this.to = to;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static TimeLine fromInviteInToGroupMessage(InviteInToGroupMessage inviteInToGroupMessage){
        String id = inviteInToGroupMessage.group.timeLineSyncId;
        String name = inviteInToGroupMessage.group.name;
        String lastSpeak = inviteInToGroupMessage.content;
        String avatar = inviteInToGroupMessage.group.avatar;
        String lastSpeakTime = MiscUtil.formatTimestamp(inviteInToGroupMessage.timestamp);
        String to = inviteInToGroupMessage.to;
        List<Message> messages = new ArrayList<>();
        messages.add(Message.fromInviteInToGroupMessage(inviteInToGroupMessage));
        TimeLine timeLine = new TimeLine(
                id,name,lastSpeak,avatar,lastSpeakTime,to,messages
        );
        return timeLine;
    }

}
