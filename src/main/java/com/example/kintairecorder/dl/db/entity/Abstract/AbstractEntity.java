package com.example.kintairecorder.dl.db.entity.Abstract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractEntity extends SQLiteOpenHelper {

    private String SQL_CREATE_TABLE;

    public AbstractEntity(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String SQL_CREATE_TABLE) {
        super(context, name, factory, version);
        this.SQL_CREATE_TABLE = SQL_CREATE_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
