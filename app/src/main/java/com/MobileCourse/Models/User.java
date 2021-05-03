package com.MobileCourse.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.MobileCourse.Api.Response.UserResponse;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "User")
public class User implements Parcelable {

    @PrimaryKey
    @NotNull
    String id;

    String username;

    String weixinId;

    String timeLineSyncId;

    List<Friend> friends;

    String avatar;

    Long cachedTimestamp;

    public User(String id,String username,String weixinId,String timeLineSyncId,List<Friend> friends,String avatar){
        this.id = id;
        this.username = username;
        this.weixinId = weixinId;
        this.timeLineSyncId = timeLineSyncId;
        this.friends = friends;
        this.avatar = avatar;
        this.cachedTimestamp = cachedTimestamp;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static User fromUserResponse(UserResponse userResponse){
        User user = new User(userResponse.getId(),userResponse.getUsername(),
                userResponse.getWeixinId(),userResponse.getTimeLineSyncId(),
                userResponse.getFriends(),userResponse.getAvatar());
        user.setCachedTimestamp(MiscUtil.getCurrentTimestamp());
        return user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getTimeLineSyncId() {
        return timeLineSyncId;
    }

    public void setTimeLineSyncId(String timeLineSyncId) {
        this.timeLineSyncId = timeLineSyncId;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public Long getCachedTimestamp() {
        return cachedTimestamp;
    }

    public void setCachedTimestamp(Long cachedTimestamp) {
        this.cachedTimestamp = cachedTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(weixinId);
        dest.writeString(timeLineSyncId);
        dest.writeString(avatar);
        if (cachedTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cachedTimestamp);
        }
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        weixinId = in.readString();
        timeLineSyncId = in.readString();
        avatar = in.readString();
        if (in.readByte() == 0) {
            cachedTimestamp = null;
        } else {
            cachedTimestamp = in.readLong();
        }
    }
}
