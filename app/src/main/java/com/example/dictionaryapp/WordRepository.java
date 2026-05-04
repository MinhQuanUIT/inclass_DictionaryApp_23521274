package com.example.dictionaryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class WordRepository {

    private final DatabaseHelper dbHelper;

    public WordRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Tìm exact match, trả về definition hoặc null
    public String getExactDefinition(String word) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseHelper.COL_DEF
                        + " FROM " + DatabaseHelper.TABLE_NAME
                        + " WHERE LOWER(" + DatabaseHelper.COL_WORD + ") = LOWER(?)",
                new String[]{ word }
        );

        String definition = null;
        if (cursor.moveToFirst()) {
            definition = cursor.getString(0);
        }
        cursor.close();
        return definition;
    }

    // Tìm các từ có chứa substring, trả về list "word — definition"
    public List<String> searchBySubstring(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseHelper.COL_WORD + ", " + DatabaseHelper.COL_DEF
                        + " FROM " + DatabaseHelper.TABLE_NAME
                        + " WHERE LOWER(" + DatabaseHelper.COL_WORD + ") LIKE LOWER(?)",
                new String[]{ "%" + query + "%" }
        );

        List<String> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            String w = cursor.getString(0);
            String d = cursor.getString(1);
            results.add(w + "  —  " + d);
        }
        cursor.close();
        return results;
    }
}