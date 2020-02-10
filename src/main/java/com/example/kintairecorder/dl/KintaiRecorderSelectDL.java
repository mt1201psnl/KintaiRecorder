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

    // Context
    private Context context;
    // TBL_RECORDのEntity
    private TblKintaiRecord tbl_record_entity;

    public KintaiRecorderSelectDL(Context context) {
        this.tbl_record_entity = new TblKintaiRecord(context);
    }

    public HashMap<String, ArrayList<KintaiRecordVo>> selectTblRecord(String thisMonthStr, String lastMonthStr) {

        // db接続
        SQLiteDatabase db = tbl_record_entity.getReadableDatabase();
        // Accessor作成
        TblKintaiRecordMgr tblKintaiRecordMgr = new TblKintaiRecordMgr(db, thisMonthStr, lastMonthStr);
        // select
        HashMap<String, ArrayList<KintaiRecordVo>> recordMap = tblKintaiRecordMgr.selectRecord();

        // 分離処理(没)
        ArrayList<KintaiRecordVo> thisMonthRecordList = recordMap.get(thisMonthStr);
        ArrayList<KintaiRecordVo> lastMonthRecordList = recordMap.get(lastMonthStr);

        // 処理結果返却
        return recordMap;
    }

}
