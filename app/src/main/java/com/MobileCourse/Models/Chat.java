package com.MobileCourse.Models;

public class Chat {

    // TimeLineId
    private String id;

    private String nickname; // 昵称
    private String lastSpeak; //最后聊天内容
    private String avatar; // 头像
    private String lastSpeakTime; //最后联络时间

    public Chat(String id,String nickname, String avatar, String lastSpeak, String lastSpeakTime) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.lastSpeak = lastSpeak;
        this.lastSpeakTime = lastSpeakTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLastSpeak() {
        return lastSpeak;
    }

    public String getLastSpeakTime() {
        return lastSpeakTime;
    }

    public String getNickname() {
        return nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLastSpeak(String lastSpeak) {
        this.lastSpeak = lastSpeak;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLastSpeakTime(String lastSpeakTime) {
        this.lastSpeakTime = lastSpeakTime;
    }
}