package com.maxpilotto.simulazione202001.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.persistance.BookProvider;
import com.maxpilotto.simulazione202001.persistance.tables.BookTable;

public class New01 extends AppCompatActivity {
    private TextView author;
    private TextView title;
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new01);

        author = findViewById(R.id.author);
        title = findViewById(R.id.title);
        save = findViewById(R.id.save);

        save.setOnClickListener(v -> {
            String sTitle = title.getText().toString();
            String sAuthor = author.getText().toString();

            if (sTitle.isEmpty() || sAuthor.isEmpty()) {
                Toast.makeText(this,getString(R.string.emptyField),Toast.LENGTH_LONG).show();

                return;
            }

            ContentValues book = new ContentValues();
            book.put(BookTable.COLUMN_AUTHOR,sAuthor);
            book.put(BookTable.COLUMN_TITLE,sTitle);
            book.put(BookTable.COLUMN_RENTED,0);

            getContentResolver().insert(BookProvider.URI_BOOKS,book);
            finish();
        });
    }
}
