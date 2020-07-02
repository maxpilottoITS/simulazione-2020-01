package com.maxpilotto.simulazione202001.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.maxpilotto.simulazione202001.persistance.tables.BookTable;
import com.maxpilotto.simulazione202001.persistance.tables.TransactionTable;

public class BookDatabase extends SQLiteOpenHelper {
    public BookDatabase(@Nullable Context context) {
        super(context, "BookDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BookTable.CREATE);
        db.execSQL(TransactionTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
