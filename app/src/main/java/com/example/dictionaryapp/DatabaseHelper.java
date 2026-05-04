package com.example.dictionaryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "dictionary.db";
    private static final int    DB_VERSION = 2;

    public static final String TABLE_NAME = "words";
    public static final String COL_ID     = "id";
    public static final String COL_WORD   = "word";
    public static final String COL_DEF    = "definition";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_WORD + " TEXT NOT NULL, "
                + COL_DEF  + " TEXT NOT NULL"
                + ")");

        // Seed dữ liệu mẫu
        String[] words = {
                "apple",       "A round fruit with red or green skin",
                "application", "A program designed to perform a specific task",
                "android",     "A mobile operating system developed by Google",
                "database",    "An organized collection of structured data",
                "data",        "Facts and statistics collected for reference",
                "developer",   "A person who writes and maintains software",
                "debug",       "To identify and remove errors from code",
                "deploy",      "To release software for use in production",
                "boolean",     "A data type with only true or false values",
                "byte",        "A unit of digital information equal to 8 bits",
                "output",        "Data produced by a computer or program",
                "input",         "Data entered into a computer or program",
                "network",       "A system of connected computers",
                "server",        "A computer that provides services to other computers",
                "client",        "A device or program that accesses a server",
                "memory",        "Storage space in a computer for data",
                "function",      "A block of code that performs a specific task",
                "variable",      "A named storage location in a program",
                "class",         "A blueprint for creating objects in programming",
                "object",        "An instance of a class in programming",
                "interface",     "A point of interaction between components",
                "algorithm",     "A step-by-step procedure to solve a problem",
                "array",         "A collection of elements stored at contiguous memory",
                "string",        "A sequence of characters in programming",
                "integer",       "A whole number without decimal points",
                "loop",          "A sequence of instructions repeated multiple times",
                "method",        "A function defined inside a class",
                "compiler",      "A program that translates code into machine language",
                "syntax",        "The set of rules defining a programming language",
                "error",         "A mistake or fault in a program",
                "exception",     "An event that disrupts the normal flow of a program",
                "library",       "A collection of pre-written code for reuse",
                "framework",     "A platform for developing software applications",
                "cache",         "Temporary storage for frequently accessed data",
                "query",         "A request for data from a database",
                "socket",        "An endpoint for network communication",
                "thread",        "A unit of execution within a process",
                "process",       "An instance of a running program",
                "cursor",        "A pointer used to traverse database records",
                "protocol",      "A set of rules for data communication"
        };

        for (int i = 0; i < words.length; i += 2) {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (word, definition) VALUES (?, ?)",
                    new String[]{ words[i], words[i + 1] });
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}