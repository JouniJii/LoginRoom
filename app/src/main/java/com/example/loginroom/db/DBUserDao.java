package com.example.loginroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.loginroom.db.DBUser;

import java.util.List;

@Dao
public interface DBUserDao {

    @Query("SELECT COUNT(uid) FROM DBUser WHERE username = :name")
    int getUserCount(String name);
//    LiveData<Integer> getUserCount(String name);

    @Query("SELECT uid FROM DBUser WHERE username = :name")
    int getUserId(String name);

    @Query("SELECT uid FROM DBUser WHERE username = :name AND password = :pass")
    int loginUser(String name, String pass);

    @Query("SELECT * " +
            "FROM DBTimestamp " +
            "WHERE userId = :uid")
    public LiveData<List<DBTimestamp>> getLogins(int uid);

    @Insert
    void insertUser(DBUser dbUser);

    @Delete
    void deleteUser(DBUser dbUser);
}
