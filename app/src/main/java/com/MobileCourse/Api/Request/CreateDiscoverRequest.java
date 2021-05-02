package com.MobileCourse.Api.Request;

import android.widget.ListAdapter;

import java.util.List;

public class CreateDiscoverRequest {
    String content;

    List<String> images;

    String video;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    long timestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public CreateDiscoverRequest(String content, List<String> images,String video, long timestamp) {
        this.content = content;
        this.images = images;
        this.video = video;
        this.timestamp = timestamp;
    }
}
