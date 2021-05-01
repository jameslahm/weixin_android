package com.MobileCourse.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.MobileCourse.Api.Response.GroupResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Entity(tableName = "Group")
public class Group {
    @PrimaryKey
    @NotNull
    String id;

    String name;

    String timeLineSavedId;

    String avatar;

    List<String> members;

    public Group(@NotNull String id, String name, String timeLineSavedId, String avatar, List<String> members) {
        this.id = id;
        this.name = name;
        this.timeLineSavedId = timeLineSavedId;
        this.avatar = avatar;
        this.members = members;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Group fromGroupDetail(GroupDetail groupDetail){
        String id = groupDetail.getId();
        String name = groupDetail.getName();
        String timeLineSyncId =groupDetail.getTimeLineSavedId();
        String avatar = groupDetail.getAvatar();
        List<String> members = groupDetail.getMembers().stream().map((user)->{
            return user.getId();
        }).collect(Collectors.toList());
        return new Group(id,name,timeLineSyncId,avatar,members);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Group fromGroupResponse(GroupResponse groupResponse){
        String id = groupResponse.getId();
        String name = groupResponse.getName();
        String timeLineSavedId = groupResponse.getTimeLineSavedId();
        String avatar = groupResponse.getAvatar();
        List<String> members = groupResponse.getMembers().stream().map((user)->{
            return user.getId();
        }).collect(Collectors.toList());
        return new Group(id,name,timeLineSavedId,avatar,members);
    }

    public String getTimeLineSavedId() {
        return timeLineSavedId;
    }

    public void setTimeLineSavedId(String timeLineSavedId) {
        this.timeLineSavedId = timeLineSavedId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
