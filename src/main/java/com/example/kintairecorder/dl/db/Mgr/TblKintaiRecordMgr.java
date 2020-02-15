package com.example.kintairecorder.dl.db.Mgr;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.kintairecorder.KintaiRecorderConst;
import com.example.kintairecorder.dl.db.Mgr.Absract.AbstractMgr;
import com.example.kintairecorder.vo.KintaiRecordVo;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * TBL_RECORDのAccessorクラス。
 * CRUD操作を提供する。
 */
public class TblKintaiRecordMgr extends AbstractMgr {

    // db
    private SQLiteDatabase db;
    // 日付
    private int date;
    // 時分
    private String time;
    //　理由コード
    private int reason_code;

    // 今月
    private String thisMonthStr;
    // 先月
    private String lastMonthStr;

    /**
     * insert用コンストラクタ
     * @param db
     * @param date
     * @param time
     */
    public TblKintaiRecordMgr(SQLiteDatabase db, int date, String time) {
        this.db = db;
        this.date = date;
        this.time = time;
    }

    /**
     * select用コンストラクタ
     * @param db
     * @param thisMonthStr
     * @param lastMonthStr
     */
    public TblKintaiRecordMgr(SQLiteDatabase db, String thisMonthStr, String lastMonthStr) {
        this.db = db;
        this.thisMonthStr = thisMonthStr;
        this.lastMonthStr = lastMonthStr;
    }

    /**
     * insert用メソッド
     * @return 結果コード
     */
    public int insertTime() {

        //values作成
        ContentValues values = new ContentValues();
        values.put(KintaiRecorderConst.DATE, this.thisMonthStr);
        values.put(KintaiRecorderConst.TIME, this.lastMonthStr);

        try {
            // 実行時に例外を受け取る
            db.insertOrThrow(KintaiRecorderConst.TABLE_NAME, null, values);
        } catch (SQLiteConstraintException conex) {
            // 一意制約違反
            return 2;
        } catch (SQLiteException ex) {
            // その他のSQLiteException
            Log.e(TAG, ex.getMessage(), ex);
            return 3;
        }
        return 1;
    }

    /**
     * insert用メソッド
     * @return 2ヶ月分のレコードVoのリストを保持したMap
     */
    public HashMap<String, ArrayList<KintaiRecordVo>> selectRecord() {

        /** 今月分**/
        ArrayList<KintaiRecordVo> thisMonthRecordList = new ArrayList<>();

        // クエリ文
        String sqlStrThisM = "select *" + " from " + KintaiRecorderConst.TABLE_NAME + " where DATE like '" + this.thisMonthStr + "%'" + "order by DATE asc";

        // SQL実行
        Cursor cursorThisM = db.rawQuery(sqlStrThisM, null);

        // cursolからListに詰め替える
        if (cursorThisM.moveToFirst()) {
            do {
                String dateStr = cursorThisM.getString(0);
                String timeStr = cursorThisM.getString(1);
                KintaiRecordVo vo = new KintaiRecordVo();
                vo.setDate(dateStr);
                vo.setTime(timeStr);
                thisMonthRecordList.add(vo);
            } while (cursorThisM.moveToNext());
        }
        cursorThisM.close();

        /** 先月分**/
        ArrayList<KintaiRecordVo> lastMonthRecordList = new ArrayList<>();

        // クエリ文
        String sqlStrLastM = "select DATE,TIME" + " from " + KintaiRecorderConst.TABLE_NAME + " where DATE like '" + this.lastMonthStr + "%'" + "order by DATE asc";

        // SQL実行
        Cursor cursorLastM = db.rawQuery(sqlStrLastM, null);

        // cursolからListに詰め替える
        if (cursorLastM.moveToFirst()) {
            do {
                String dateStr = cursorLastM.getString(0);
                String timeStr = cursorLastM.getString(1);
                KintaiRecordVo vo = new KintaiRecordVo();
                vo.setDate(dateStr);
                vo.setTime(timeStr);
                lastMonthRecordList.add(vo);
            } while (cursorLastM.moveToNext());
        }
        cursorLastM.close();

        // MapにそれぞれのListを格納
        HashMap<String, ArrayList<KintaiRecordVo>> map = new HashMap<>();
        map.put(thisMonthStr, thisMonthRecordList);
        map.put(lastMonthStr, lastMonthRecordList);

        return map;
    }

}
