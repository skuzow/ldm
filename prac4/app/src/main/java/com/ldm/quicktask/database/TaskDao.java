package com.ldm.quicktask.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity task);

    @Query("SELECT * FROM tasks")  // Query to get all tasks
    LiveData<List<TaskEntity>> getAllTasks();

    @Update
    void update(TaskEntity taskEntity);

    @Delete
    void delete(TaskEntity taskEntity);
}
