package com.example.kintairecorder.command;

import android.content.Context;

import com.example.kintairecorder.dl.KintaiRecorderInsertDL;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * KintaiRecorderRegistのCommand。
 */
public class KintaiRecorderInsertCommand {

    // Context
    private Context context;

    // 処理結果コード
    private int result;

    /**
     * 引数にContextを受け取るコンストラクタ。
     * @param context
     */
    public KintaiRecorderInsertCommand(Context context) {
        this.context = context;
    }

    /**
     * executeメソッド。
     */
    public void execute() {

        // 現在日時の取得
        // 日付(主キー)と時分を取得する
        Date date = new Date();
        SimpleDateFormat sdf_key = new SimpleDateFormat("yyyy/MM/dd(E)");
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");

        // フォーマット
        String dateTmp = sdf_key.format(date);
        String timeTmp = sdf_time.format(date);

        // DL実行
        KintaiRecorderInsertDL dl = new KintaiRecorderInsertDL(context);
        result = dl.insertTblRecord(dateTmp, timeTmp);

    }

    /**
     * 処理結果コードを取得する。
     * @return result
     */
    public int getResult() {
        return result;
    }
}
