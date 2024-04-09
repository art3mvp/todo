package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface NotesDao {

    //data access object, interact with database.

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Insert
    Completable add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);
}
