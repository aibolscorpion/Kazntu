package com.example.kazntu.data.entity.grade.transcript;

import androidx.room.TypeConverter;

import com.example.kazntu.data.entity.academic.DatesItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterTranscript implements Serializable {
    Gson gson = new Gson();

    @TypeConverter
    public String fromCourseItemList(List<CoursesItem> coursesItems) {
        if (coursesItems == null) {
            return (null);
        }
        Type type = new TypeToken<List<CoursesItem>>() {}.getType();
        String json = gson.toJson(coursesItems, type);
        return json;
    }

    @TypeConverter
    public List<CoursesItem> toCourseItemList(String coursesItemsString) {
        if (coursesItemsString == null) {
            return (null);
        }
        Type type = new TypeToken<List<CoursesItem>>() {}.getType();
        List<CoursesItem> coursesItems = gson.fromJson(coursesItemsString, type);
        return coursesItems;
    }
}

