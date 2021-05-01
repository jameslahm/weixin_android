package com.MobileCourse.Models;

public class Chat {

    // TimeLineId
    private String id;

    private String nickname; // 昵称
    private String lastSpeak; //最后聊天内容
    private String avatar; // 头像
    private String lastSpeakTime; //最后联络时间

    private long unReadCount;

    public Chat(String id,String nickname, String avatar, String lastSpeak, String lastSpeakTime, long unReadCount) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.lastSpeak = lastSpeak;
        this.lastSpeakTime = lastSpeakTime;
        this.unReadCount = unReadCount;
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

    public long getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
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

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastSpeak='" + lastSpeak + '\'' +
                ", avatar='" + avatar + '\'' +
                ", lastSpeakTime='" + lastSpeakTime + '\'' +
                ", unReadCount=" + unReadCount +
                '}';
    }
}