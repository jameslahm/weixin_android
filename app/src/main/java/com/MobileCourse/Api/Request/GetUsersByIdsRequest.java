package com.MobileCourse.Api.Request;

import java.util.List;

public class GetUsersByIdsRequest {
    List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public GetUsersByIdsRequest(List<String> ids) {
        this.ids = ids;
    }
}
