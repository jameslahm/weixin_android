package com.MobileCourse.Daos;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.MobileCourse.Models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE weixinId = :weixinId")
    LiveData<User> getUserByWeixinId(String weixinId);

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    LiveData<User> getUserById(String id);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();
}
