package com.MobileCourse.Api.Response;

public class UploadResponse extends CommonResponse {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UploadResponse(String url) {
        this.url = url;
    }
}
