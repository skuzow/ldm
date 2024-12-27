package com.ldm.prac4.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity task);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity findTaskById(int id);

    @Update
    void update(TaskEntity taskEntity);

    @Delete
    void delete(TaskEntity taskEntity);
}
