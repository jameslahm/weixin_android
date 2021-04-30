package com.MobileCourse.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.TimeLine;

import java.util.List;

@Dao
public interface TimeLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimeLine(TimeLine timeLine);

    @Query("SELECT * FROM TimeLine")
    LiveData<List<TimeLine>> getTimeLines();

    @Query("DELETE FROM TimeLine")
    int deleteTimeLines();
}
