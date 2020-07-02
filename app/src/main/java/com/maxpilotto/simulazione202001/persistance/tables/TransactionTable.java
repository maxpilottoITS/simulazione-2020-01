package com.maxpilotto.simulazione202001.persistance.tables;

import android.provider.BaseColumns;

public class TransactionTable implements BaseColumns {
    public static final String COLUMN_TYPE = "type";    // 0 Noleggio, 1 Restituzione
    public static final String COLUMN_BOOK = "book";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NOTE = "note";

    public static final String NAME = "transactions";
    public static final String CREATE = "CREATE TABLE " + NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_BOOK + " INTEGER," +
            COLUMN_TYPE + " INTEGER," +
            COLUMN_DATE + " INTEGER," +
            COLUMN_NOTE + " TEXT" +
            ");";
}
