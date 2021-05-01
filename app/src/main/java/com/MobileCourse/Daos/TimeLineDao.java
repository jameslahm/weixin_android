package com.MobileCourse.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("SELECT * FROM TimeLine WHERE id = :id LIMIT 1")
    LiveData<TimeLine> getTimeLine(String id);

    @Query("UPDATE TimeLine SET lastCheckTimestamp = :timestamp WHERE id = :timeLineId")
    void updateLastCheckTimestamp(String timeLineId,long timestamp);

    @Query("DELETE FROM TimeLine WHERE id = :id")
    void deleteTimeLine(String id);
}
