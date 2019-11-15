package com.example.loginroom.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.loginroom.MyDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBRepo {

    private static volatile MyDatabase INSTANCE;
    public static final String DB_NAME = "tietokanta.db";

    public DBRepo(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MyDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
    }

    public void insertUser(String user,
                           String pass) {

        DBUser dbUser = new DBUser();
        dbUser.username = user;
        dbUser.password = pass;
/*
        note.setTitle(title);
        note.setDescription(description);
        note.setCreatedAt(AppUtils.getCurrentDateTime());
        note.setModifiedAt(AppUtils.getCurrentDateTime());
        note.setEncrypt(encrypt);


        if(encrypt) {
            note.setPassword(AppUtils.generateHash(password));
        } else note.setPassword(null);
*/
        insertUser(dbUser);
    }

    public void insertUser(final DBUser user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                INSTANCE.dbUserDao().insertUser(user);
                return null;
            }
        }.execute();
    }

    public List<DBTimestamp> getTimesByUid (final int uid) {
        return INSTANCE.dbTimestampDao().getTimesByUid(uid);
    }
/*
    public void updateTask(final Note note) {
        note.setModifiedAt(AppUtils.getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final int id) {
        final LiveData<Note> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteTask(note);
                return null;
            }
        }.execute();
    }


 */
/*
    public LiveData<Integer> getUserCountByName(String name) {
        return INSTANCE.dbUserDao().getUserCount(name);
    }
 */
    public int getUserCountByName(String name) {
        return INSTANCE.dbUserDao().getUserCount(name);

    }

    public int loginUser(String name, String pass) {
        int uid = INSTANCE.dbUserDao().getUserId(name);
        int ok = INSTANCE.dbUserDao().loginUser(name, pass);

        DBTimestamp dbTimestamp = new DBTimestamp();
        Date currentTime = Calendar.getInstance().getTime();
        dbTimestamp.timestamp = currentTime.toString();
        dbTimestamp.userId = uid;

        Log.i("OMA", "loginUser " +uid +" ok: " +ok +" " + dbTimestamp.timestamp);

        if (uid >0 && ok ==0) { // login failed
            dbTimestamp.loginOk = 0;
            INSTANCE.dbTimestampDao().insertTimestamp(dbTimestamp);
        }

        if (ok > 0) {
            dbTimestamp.loginOk = 1;
            INSTANCE.dbTimestampDao().insertTimestamp(dbTimestamp);
        }

        return ok;
    }


    /*
    public LiveData<Note> getTask(int id) {
        return noteDatabase.daoAccess().getTask(id);
    }

    public LiveData<List<Note>> getTasks() {
        return noteDatabase.daoAccess().fetchAllTasks();
    }

     */
}

