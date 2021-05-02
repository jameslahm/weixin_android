package com.MobileCourse.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.MobileCourse.Models.Discover;

import java.util.List;

@Dao
public interface DiscoverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDiscover(Discover discover);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDiscovers(List<Discover> discovers);

    @Query("SELECT * FROM Discover ORDER BY timestamp DESC")
    LiveData<List<Discover>> getAllDiscovers();

    @Query("SELECT * FROM Discover WHERE id = :id")
    LiveData<Discover> getDiscoverById(String id);
}
