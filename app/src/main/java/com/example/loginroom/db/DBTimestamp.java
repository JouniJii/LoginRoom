package com.example.loginroom.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = DBUser.class,
        parentColumns = "uid", // primary key in DBUser.class
        childColumns = "userId", // key in this entity
        onDelete = CASCADE))
public class DBTimestamp {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int loginOk;
    public String timestamp;
}
