package com.MobileCourse.Database;

import androidx.room.TypeConverter;

import com.MobileCourse.Models.Friend;
import com.MobileCourse.Models.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Friend> fromStringToFriends(String value){
        Type listType = new TypeToken<List<Friend>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromFriendsToString(List<Friend> friends){
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        return json;
    }

    @TypeConverter
    public static List<String> fromStringToList(String value){
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromListToString(List<String> members){
        Gson gson = new Gson();
        String json = gson.toJson(members);
        return json;
    }

    @TypeConverter
    public static List<Message> fromStringToMessages(String value){
        Type listType = new TypeToken<List<Message>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromMessagesToString(List<Message> messages){
        Gson gson = new Gson();
        String json = gson.toJson(messages);
        return json;
    }
}
