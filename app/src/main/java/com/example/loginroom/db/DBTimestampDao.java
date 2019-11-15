package com.example.loginroom.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.loginroom.db.DBTimestamp;

import java.util.List;

@Dao
public interface DBTimestampDao {

    @Query("SELECT * FROM DBTimestamp WHERE userId = :uid")
    List<DBTimestamp> getTimesByUid(int uid);

    @Insert
    void insertTimestamp(DBTimestamp dbTimestamp);

    @Delete
    void deleteTimestamp(DBTimestamp dbTimestamp);
}
