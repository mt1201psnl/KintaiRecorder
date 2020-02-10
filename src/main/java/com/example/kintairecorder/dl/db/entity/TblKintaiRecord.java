package com.example.kintairecorder.dl.db.entity;

import android.content.Context;

import com.example.kintairecorder.KintaiRecorderConst;
import com.example.kintairecorder.dl.db.entity.Abstract.AbstractEntity;

/**
 * TBL_RECORDのEntityクラス。
 */
public final class TblKintaiRecord extends AbstractEntity {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + KintaiRecorderConst.TABLE_NAME + "("
                    + "DATE INTEGER PRIMARY KEY,"
                    + "TIME TEXT NOT NULL,"
                    + "REASON_CODE INTEGER)";

    /**
     * 引数にContextを受け取るコンストラクタ
     * 親クラスでテーブルのチェックを行う。
     * @param context
     */
    public TblKintaiRecord(Context context) {
        super(context, KintaiRecorderConst.DATABASE_NAME, null, KintaiRecorderConst.DATABASE_VERSION,SQL_CREATE_TABLE);
    }
}
