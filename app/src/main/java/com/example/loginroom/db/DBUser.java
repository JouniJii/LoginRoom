package com.example.loginroom.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DBUser {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String username;
    public String password;
}
