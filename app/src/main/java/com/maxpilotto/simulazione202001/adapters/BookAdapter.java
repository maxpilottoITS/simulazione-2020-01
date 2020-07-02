package com.maxpilotto.simulazione202001.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.persistance.tables.BookTable;

public class BookAdapter extends CursorAdapter {
    public BookAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_book,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor.getInt(cursor.getColumnIndex(BookTable.COLUMN_RENTED)) > 0) {
            view.setBackgroundColor(Color.RED);
        } else{
            view.setBackgroundColor(Color.WHITE);
        }

        ((TextView)view.findViewById(R.id.title)).setText(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_TITLE)));
        ((TextView)view.findViewById(R.id.author)).setText(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_AUTHOR)));
    }
}
