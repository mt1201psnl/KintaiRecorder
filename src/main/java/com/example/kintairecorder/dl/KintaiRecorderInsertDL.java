package com.example.kintairecorder.dl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kintairecorder.dl.db.Mgr.TblKintaiRecordMgr;
import com.example.kintairecorder.dl.db.entity.TblKintaiRecord;

/**
 * KintaiRecorderRegistのDomainLogic。
 */
public class KintaiRecorderInsertDL {

    // Context
    private Context context;
    // TBL_RECORDのEntity
    private TblKintaiRecord tbl_record_entity;

    /**
     * 引数にContextを受け取るコンストラクタ
     * @param context
     */
    public KintaiRecorderInsertDL(Context context) {
        this.tbl_record_entity = new TblKintaiRecord(context);
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
        TblKintaiRecordMgr tbl_record_accsessor = new TblKintaiRecordMgr(db, dateTmp, timeTmp);

        // 処理結果返却
        return tbl_record_accsessor.insertTime();
    }
}
