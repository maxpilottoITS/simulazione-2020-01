package com.maxpilotto.simulazione202001.activities;

import android.content.Intent;
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
import com.maxpilotto.simulazione202001.adapters.BookAdapter;
import com.maxpilotto.simulazione202001.dialogs.DeleteDialog;
import com.maxpilotto.simulazione202001.persistance.BookProvider;
import com.maxpilotto.simulazione202001.persistance.tables.BookTable;

public class List01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 135151;

    private ListView listView;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);

        adapter = new BookAdapter(this, null);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, Mod01.class);
            i.putExtra(Mod01.ID_EXTRA, id);

            startActivity(i);
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new DeleteDialog()
                    .setCallback(() -> getContentResolver().delete(BookProvider.URI_BOOKS, BookTable._ID + "=" + id, null))
                    .show(getSupportFragmentManager(), null);

            return true;
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, BookProvider.URI_BOOKS, null, null, null, null);
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
