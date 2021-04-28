package com.MobileCourse.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Models.User;


@Database(entities = {User.class},version = 1)
@TypeConverters({Converters.class})
public abstract class WeiXinDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "weixin_db";

    static WeiXinDatabase instance;

    public static WeiXinDatabase getInstance(final Context context){
        if(instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WeiXinDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract UserDao getUserDao();

}
