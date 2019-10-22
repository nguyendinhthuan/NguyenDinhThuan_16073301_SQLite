package com.example.student.dialog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "book_list", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table Book(id integer primary key, name text, address text, email text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists Book";
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", book.getId());
        contentValues.put("name", book.getName());
        contentValues.put("address", book.getAddress());
        contentValues.put("email", book.getEmail());

        db.insert("Book", null, contentValues);
        return true;
        //int result = (int) db.insert("Book", null, contentValues);
       //return result;
    }

    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from Book where id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        Book book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return book;
    }

    public ArrayList<Book> getAllBook() {
        ArrayList<Book> list = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from Book";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            list.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "delete from Book where id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null) {
            return false;
        } else {
            cursor.moveToFirst();
            cursor.close();
        }
        return true;
    }

    public boolean updateBook(int id, String address, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("email", email);
        db.update("Book", contentValues, "id=" + id, null);
        return true;
    }
}
