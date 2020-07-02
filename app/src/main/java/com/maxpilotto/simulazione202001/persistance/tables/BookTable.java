package com.maxpilotto.simulazione202001.persistance.tables;

import android.provider.BaseColumns;

public class BookTable implements BaseColumns {
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_RENTED = "rented";    // 0 Libero, 1 Noleggiato

    public static final String NAME = "books";
    public static final String CREATE = "CREATE TABLE " + NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TITLE + " TEXT," +
            COLUMN_AUTHOR + " TEXT," +
            COLUMN_RENTED + " INTEGER" +
            ");";
}
