package com.ldm.cinequiz.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {QuestionEntity.class}, version = 1)
@TypeConverters({AnswersTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
}
