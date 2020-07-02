package com.maxpilotto.simulazione202001.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.maxpilotto.simulazione202001.R;
import com.maxpilotto.simulazione202001.adapters.TransactionAdapter;
import com.maxpilotto.simulazione202001.persistance.BookProvider;
import com.maxpilotto.simulazione202001.persistance.tables.TransactionTable;

public class List02 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 621351;

    public static final String ID_EXTRA = "id.extra";

    private ListView listView;
    private CursorAdapter adapter;
    private long bookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list02);

        adapter = new TransactionAdapter(this,null);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        bookId = getIntent().getLongExtra(ID_EXTRA,-1);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, BookProvider.URI_TRANSACTIONS,null, TransactionTable.COLUMN_BOOK + "=" + bookId,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }
}
