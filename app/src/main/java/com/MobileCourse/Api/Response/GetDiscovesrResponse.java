package com.MobileCourse.Api.Response;

import com.MobileCourse.Models.Discover;
import com.MobileCourse.Models.Reply;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.UserRepository;

import java.util.List;

public class GetDiscovesrResponse extends CommonResponse {
    List<Discover> discovers;

    public GetDiscovesrResponse(List<Discover> discovers) {
        this.discovers = discovers;
    }

    public List<Discover> getDiscovers() {
        return discovers;
    }

    public void setDiscovers(List<Discover> discovers) {
        this.discovers = discovers;
    }
}
