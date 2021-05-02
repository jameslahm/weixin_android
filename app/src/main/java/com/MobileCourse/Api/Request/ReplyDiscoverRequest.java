package com.MobileCourse.Api.Request;

public class ReplyDiscoverRequest {
    String discoverId;

    String content;

    long timestamp;

    public String getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(String discoverId) {
        this.discoverId = discoverId;
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

    public ReplyDiscoverRequest(String discoverId, String content, long timestamp) {
        this.discoverId = discoverId;
        this.content = content;
        this.timestamp = timestamp;
    }
}
