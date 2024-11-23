package com.ldm.cinequiz.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QuestionEntity question);

    @Query("SELECT * FROM questions WHERE id = :id")
    QuestionEntity findQuestionById(int id);
}
