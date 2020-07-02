package com.maxpilotto.simulazione202001.persistance;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxpilotto.simulazione202001.persistance.tables.BookTable;
import com.maxpilotto.simulazione202001.persistance.tables.TransactionTable;

public class BookProvider extends ContentProvider {
    public static final String AUTHORITY = "com.maxpilotto.simulazione202001.database.ContentProvider";

    public static final String BASE_PATH_BOOKS = "books";
    public static final String BASE_PATH_TRANSACTIONS = "transactions";

    public static final int ALL_BOOKS = 1000;
    public static final int SINGLE_BOOK = 1020;
    public static final int ALL_TRANSACTIONS = 2000;
    public static final int SINGLE_TRANSACTION = 2020;

    public static final String MIME_TYPE_ALL_BOOKS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_books";
    public static final String MIME_TYPE_BOOK = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_book";
    public static final String MIME_TYPE_ALL_TRANSACTIONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_transactions";
    public static final String MIME_TYPE_TRANSACTION = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_transaction";

    public static final Uri URI_BOOKS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_BOOKS);
    public static final Uri URI_TRANSACTIONS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_TRANSACTIONS);

    private BookDatabase database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH_BOOKS, ALL_BOOKS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_BOOKS + "/#", SINGLE_BOOK);

        uriMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTIONS, ALL_TRANSACTIONS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_TRANSACTIONS + "/#", SINGLE_TRANSACTION);
    }

    @Override
    public boolean onCreate() {
        database = new BookDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case SINGLE_BOOK:
                builder.setTables(BookTable.NAME);
                builder.appendWhere(BookTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_BOOKS:
                builder.setTables(BookTable.NAME);
                break;

            case SINGLE_TRANSACTION:
                builder.setTables(TransactionTable.NAME);
                builder.appendWhere(TransactionTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_TRANSACTIONS:
                builder.setTables(TransactionTable.NAME);
                break;
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_BOOK:
                return MIME_TYPE_BOOK;

            case ALL_BOOKS:
                return MIME_TYPE_ALL_BOOKS;

            case SINGLE_TRANSACTION:
                return MIME_TYPE_TRANSACTION;

            case ALL_TRANSACTIONS:
                return MIME_TYPE_ALL_TRANSACTIONS;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_BOOKS) {
            long result = database.getWritableDatabase().insert(BookTable.NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_BOOKS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        } else if (uriMatcher.match(uri) == ALL_TRANSACTIONS) {
            long result = database.getWritableDatabase().insert(TransactionTable.NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_TRANSACTIONS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_BOOK:
                table = BookTable.NAME;
                query = BookTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_BOOKS:
                table = BookTable.NAME;
                query = selection;
                break;

            case SINGLE_TRANSACTION:
                table = TransactionTable.NAME;
                query = TransactionTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_TRANSACTIONS:
                table = TransactionTable.NAME;
                query = selection;
                break;
        }

        int deletedRows = db.delete(table, query, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_BOOK:
                table = BookTable.NAME;
                query = BookTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_BOOKS:
                table = BookTable.NAME;
                query = selection;
                break;

            case SINGLE_TRANSACTION:
                table = TransactionTable.NAME;
                query = TransactionTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_TRANSACTIONS:
                table = TransactionTable.NAME;
                query = selection;
                break;
        }

        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }
}
