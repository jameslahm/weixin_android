package com.MobileCourse.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.MobileCourse.Models.Application;

import java.util.List;

@Dao
public interface ApplicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApplication(Application application);

    @Delete
    void deleteApplication(Application application);

    @Query("SELECT * FROM Application")
    LiveData<List<Application>> getApplications();

    @Query("UPDATE Application SET unRead = 0")
    void updateRead();
}
