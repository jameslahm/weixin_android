package com.MobileCourse.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.MobileCourse.Daos.ApplicationDao;
import com.MobileCourse.Daos.DiscoverDao;
import com.MobileCourse.Daos.GroupDao;
import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Models.Application;
import com.MobileCourse.Models.Discover;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;



@Database(entities = {User.class, Me.class, TimeLine.class, Application.class, Group.class, Discover.class},version = 8)
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
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract UserDao getUserDao();
    public abstract MeDao getMeDao();
    public abstract TimeLineDao getTimeLineDao();
    public abstract ApplicationDao getApplicationDao();
    public abstract GroupDao getGroupDao();
    public abstract DiscoverDao getDiscoverDao();
}
