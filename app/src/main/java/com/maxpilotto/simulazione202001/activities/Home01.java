package com.maxpilotto.simulazione202001.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.persistance.BookProvider;
import com.maxpilotto.simulazione202001.persistance.tables.BookTable;

public class Home01 extends AppCompatActivity {
    private TextView booksInfo;

    @Override
    protected void onResume() {
        super.onResume();

        int total = getContentResolver().query(BookProvider.URI_BOOKS,null,null,null,null).getCount();
        int rented = getContentResolver().query(BookProvider.URI_BOOKS,null, BookTable.COLUMN_RENTED + "=1",null,null).getCount();

        booksInfo.setText(getString(R.string.booksInfo,total,rented));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);

        booksInfo = findViewById(R.id.booksInfo);

        findViewById(R.id.showList).setOnClickListener(v -> {
            startActivity(new Intent(this,List01.class));
        });

        findViewById(R.id.addBook).setOnClickListener(v -> {
            startActivity(new Intent(this,New01.class));
        });
    }
}
