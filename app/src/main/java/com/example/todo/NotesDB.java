package com.example.todo;


import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDB extends RoomDatabase {
    //sigleton
    private static NotesDB instance = null;
    private static final String DB_NAME = "notes.db";

    public static NotesDB getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    NotesDB.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();
}
