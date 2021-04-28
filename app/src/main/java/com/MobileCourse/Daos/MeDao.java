package com.MobileCourse.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.MobileCourse.Models.Me;

@Dao
public interface MeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMe(Me me);

    @Query("SELECT * FROM Me LIMIT 1")
    LiveData<Me> getMe();
}
