package com.ldm.cinequiz.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "questions")
public class QuestionEntity {
    @PrimaryKey
    private final int id;

    private final String title;
    private final ArrayList<String> answers;

    public QuestionEntity(int id, String title, ArrayList<String> answers) {
        this.id = id;
        this.title = title;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}

class AnswersTypeConverter {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
