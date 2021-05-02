package com.MobileCourse.Api.Request;

public class UnLikeDiscoverRequest {
    String discoverId;

    public String getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(String discoverId) {
        this.discoverId = discoverId;
    }

    public UnLikeDiscoverRequest(String discoverId) {
        this.discoverId = discoverId;
    }
}
