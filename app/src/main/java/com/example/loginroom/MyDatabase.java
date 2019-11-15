package com.example.loginroom;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.loginroom.db.DBTimestamp;
import com.example.loginroom.db.DBTimestampDao;
import com.example.loginroom.db.DBUser;
import com.example.loginroom.db.DBUserDao;

@androidx.room.Database(entities = {DBUser.class, DBTimestamp.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract DBUserDao dbUserDao();
    public abstract DBTimestampDao dbTimestampDao();


}
