package com.MobileCourse.Api.Request;

import java.util.List;

public class CreateGroupRequest {
    String name;
    List<String> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public CreateGroupRequest(String name, List<String> members) {
        this.name = name;
        this.members = members;
    }
}
