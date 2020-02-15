package com.example.kintairecorder.dl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kintairecorder.dl.db.Mgr.TblKintaiRecordMgr;
import com.example.kintairecorder.dl.db.entity.TblKintaiRecord;
import com.example.kintairecorder.vo.KintaiRecordVo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * KintaiRecorderSelectのDomainLogic。
 */
public class KintaiRecorderSelectDL {

    // TBL_RECORDのEntity
    private TblKintaiRecord tbl_record_entity;

    /**
     * 引数にContextを受け取るコンストラクタ
     * @param context
     */
    public KintaiRecorderSelectDL(Context context) {
        this.tbl_record_entity = new TblKintaiRecord(context);
    }

    /**
     * テーブル検索処理。
     * @param thisMonthStr
     * @param lastMonthStr
     * @return recordMap
     */
    public HashMap<String, ArrayList<KintaiRecordVo>> selectTblKintaiRecord(String thisMonthStr, String lastMonthStr) {

        // db接続
        SQLiteDatabase db = tbl_record_entity.getReadableDatabase();
        // Mgr作成
        TblKintaiRecordMgr tblKintaiRecordMgr = new TblKintaiRecordMgr(db, thisMonthStr, lastMonthStr);

        // selectを実行して取得結果Mapを返却
        return tblKintaiRecordMgr.selectRecord();
    }

}
