package com.maxpilotto.simulazione202001.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.persistance.BookProvider;
import com.maxpilotto.simulazione202001.persistance.tables.BookTable;
import com.maxpilotto.simulazione202001.persistance.tables.TransactionTable;

import java.util.Date;

public class Mod01 extends AppCompatActivity {
    public static final String ID_EXTRA = "id.extra";

    private EditText title;
    private EditText author;
    private Button editBtn;
    private Button rentBtn;
    private Button returnBtn;
    private Button historyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod01);

        long id = getIntent().getLongExtra(ID_EXTRA, -1);
        Cursor cursor = getContentResolver().query(BookProvider.URI_BOOKS, null, BookTable._ID + "=" + id, null, null);

        cursor.moveToNext();

        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        editBtn = findViewById(R.id.edit);
        rentBtn = findViewById(R.id.rent);
        returnBtn = findViewById(R.id.returnBook);
        historyBtn = findViewById(R.id.history);

        title.setText(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_TITLE)));
        author.setText(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_AUTHOR)));

        editBtn.setOnClickListener(v -> {
            String sTitle = title.getText().toString();
            String sAuthor = author.getText().toString();

            if (sTitle.isEmpty() || sAuthor.isEmpty()) {
                Toast.makeText(this, getString(R.string.emptyField), Toast.LENGTH_LONG).show();

                return;
            }

            ContentValues book = new ContentValues();
            book.put(BookTable.COLUMN_AUTHOR, sAuthor);
            book.put(BookTable.COLUMN_TITLE, sTitle);

            getContentResolver().update(BookProvider.URI_BOOKS, book, BookTable._ID + "=" + id, null);

            finish();
        });

        rentBtn.setOnClickListener(v -> {
            if (cursor.getInt(cursor.getColumnIndex(BookTable.COLUMN_RENTED)) > 0) {
                Toast.makeText(this, getString(R.string.errorRent), Toast.LENGTH_SHORT).show();

                return;
            }

            ContentValues transaction = new ContentValues();
            transaction.put(TransactionTable.COLUMN_BOOK,id);
            transaction.put(TransactionTable.COLUMN_TYPE,0);
            transaction.put(TransactionTable.COLUMN_DATE,System.currentTimeMillis());

            ContentValues book = new ContentValues();
            book.put(BookTable.COLUMN_RENTED, 1);

            getContentResolver().insert(BookProvider.URI_TRANSACTIONS,transaction);
            getContentResolver().update(BookProvider.URI_BOOKS, book, BookTable._ID + "=" + id, null);

            finish();
        });

        returnBtn.setOnClickListener(v -> {
            if (cursor.getInt(cursor.getColumnIndex(BookTable.COLUMN_RENTED)) == 0) {
                Toast.makeText(this, getString(R.string.errorReturn), Toast.LENGTH_SHORT).show();

                return;
            }

            ContentValues transaction = new ContentValues();
            transaction.put(TransactionTable.COLUMN_BOOK,id);
            transaction.put(TransactionTable.COLUMN_TYPE,1);
            transaction.put(TransactionTable.COLUMN_DATE,System.currentTimeMillis());

            ContentValues book = new ContentValues();
            book.put(BookTable.COLUMN_RENTED, 0);

            getContentResolver().insert(BookProvider.URI_TRANSACTIONS,transaction);
            getContentResolver().update(BookProvider.URI_BOOKS, book, BookTable._ID + "=" + id, null);

            finish();
        });

        historyBtn.setOnClickListener(v -> {
            Intent i = new Intent(this,List02.class);
            i.putExtra(List02.ID_EXTRA,id);

            startActivity(i);
        });
    }
}
