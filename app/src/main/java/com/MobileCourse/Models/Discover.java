package com.MobileCourse.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.MobileCourse.Api.Response.DiscoverResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Discover")
public class Discover {

    @PrimaryKey
    @NotNull
    String id;

    User sender;

    String content; // 文字

    long timestamp; // 发布时间

    List<String> images; // 图片

    String video;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    List<User> likesBy;

    List<Reply> replies;

    public List<User> getLikesBy() {
        return likesBy;
    }

    public void setLikesBy(List<User> likesBy) {
        this.likesBy = likesBy;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Discover(@NotNull String id, User sender, String content,String video ,long timestamp, List<String> images, List<User> likesBy, List<Reply> replies) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.images = images;
        this.likesBy = likesBy;
        this.replies = replies;
        this.video = video;
    }

    public static Discover fromDiscoverResponse(DiscoverResponse discoverResponse){
        return new Discover(
                discoverResponse.getId(),
                discoverResponse.getSender(),
                discoverResponse.getContent(),
                discoverResponse.getVideo(),
                discoverResponse.getTimestamp(),
                discoverResponse.getImages(),
                discoverResponse.getLikesBy(),
                discoverResponse.getReplies()
        );
    }
}
