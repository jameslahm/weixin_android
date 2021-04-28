package com.MobileCourse.Database;

import androidx.room.TypeConverter;

import com.MobileCourse.Models.Friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Friend> fromString(String value){
        Type listType = new TypeToken<List<Friend>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Friend> friends){
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        return json;
    }
}
