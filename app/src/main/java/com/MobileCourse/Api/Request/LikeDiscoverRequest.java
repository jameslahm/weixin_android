package com.MobileCourse.Api.Request;

public class LikeDiscoverRequest {
    String discoverId;

    public String getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(String discoverId) {
        this.discoverId = discoverId;
    }

    public LikeDiscoverRequest(String discoverId) {
        this.discoverId = discoverId;
    }
}
