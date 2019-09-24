package com.example.kintairecorder.dl.db.accessor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.kintairecorder.KintaiRecorderConst;
import com.example.kintairecorder.dl.db.accessor.Absract.AbstractAccessor;

import static android.content.ContentValues.TAG;

/**
 * TBL_RECORDのAccessorクラス。
 * CRUD操作を提供する。
 */
public class TblRecordAccessor extends AbstractAccessor {

    // db
    SQLiteDatabase db;
    // 日付
    private int date;
    // 時分
    private String time;
    //　理由コード
    private int reason_code;

    /**
     * insert用コンストラクタ。
     * @param db
     * @param date
     * @param time
     */
    public TblRecordAccessor(SQLiteDatabase db, int date, String time) {
        this.db = db;
        this.date = date;
        this.time = time;
    }

    /**
     * 時分データを登録する。
     * @return result
     */
    public int insertTime() {

        //values作成
        ContentValues values = new ContentValues();
        values.put(KintaiRecorderConst.DATE, this.date);
        values.put(KintaiRecorderConst.TIME, this.time);

        try {
            // 実行時に例外を受け取る
            db.insertOrThrow(KintaiRecorderConst.TABLE_NAME, null, values);
        } catch (SQLiteConstraintException conex) {
            // 一意制約違反
            return 1;
        } catch (SQLiteException ex) {
            // その他のSQLiteException
            Log.e(TAG, ex.getMessage(), ex);
            return 2;
        }
        return 0;
    }
}
