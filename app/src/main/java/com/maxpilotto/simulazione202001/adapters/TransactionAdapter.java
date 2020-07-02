package com.maxpilotto.simulazione202001.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.persistance.tables.TransactionTable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionAdapter extends CursorAdapter {
    public TransactionAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_transaction,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int type = cursor.getInt(cursor.getColumnIndex(TransactionTable.COLUMN_TYPE));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(TransactionTable.COLUMN_DATE)));

        ((TextView)view.findViewById(R.id.type)).setText(type == 0 ? "Noleggio" : "Restituzione");
        ((TextView)view.findViewById(R.id.date)).setText(new SimpleDateFormat("hh:mm dd/MM/yyyy").format(date));
    }
}
