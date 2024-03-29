package com.example.angelosgeorgiou.timetrack;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table where date = :requestedDate ORDER BY time")
    List<Note> getDateNotes(int requestedDate);

    @Query("SELECT * FROM note_table ORDER BY time")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT DISTINCT title FROM note_table")
    LiveData<List<String>> getTitles();
}
