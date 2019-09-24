package com.example.kintairecorder.dl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kintairecorder.dl.db.accessor.TblRecordAccessor;
import com.example.kintairecorder.dl.db.entity.TblRecord;

/**
 * KintaiRecorderRegistのDomainLogic。
 */
public class KintaiRecorderRegistDL {

    // Context
    private Context context;
    // TBL_RECORDのEntity
    TblRecord tbl_record_entity;

    /**
     * 引数にContextを受け取るコンストラクタ
     * @param context
     */
    public KintaiRecorderRegistDL(Context context) {
        this.tbl_record_entity = new TblRecord(context);
    }

    /**
     * レコード挿入処理。
     * @param dateTmp
     * @param timeTmp
     * @return
     */
    public int insertTblRecord(int dateTmp, String timeTmp) {

        // db接続
        SQLiteDatabase db = tbl_record_entity.getWritableDatabase();
        // Accessor作成
        TblRecordAccessor tbl_record_accsessor = new TblRecordAccessor(db, dateTmp, timeTmp);
        // 挿入
        int result = tbl_record_accsessor.insertTime();
        // 処理結果返却
        return result;
    }
}
