package com.raimzhanov.mvvmlesson.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.raimzhanov.mvvmlesson.pojo.Person;

@Database(entities = {Person.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "person.db";
    private static AppDataBase dataBase;
    private static final Object LOCK = new Object();

    public static AppDataBase getInstance(Context context){
        synchronized (LOCK){
            if (dataBase == null){
dataBase = Room.databaseBuilder(context,AppDataBase.class,DB_NAME).build();
            }
            return dataBase;
        }
    }
    public abstract AppDao dao();
}
