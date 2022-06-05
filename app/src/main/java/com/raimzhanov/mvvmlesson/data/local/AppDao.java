package com.raimzhanov.mvvmlesson.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raimzhanov.mvvmlesson.pojo.Person;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM person_list")
    LiveData<List<Person>> getAllPersons();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersons(List<Person> people);

    @Query("DELETE FROM person_list")
    void clearDb();
}
