package com.example.kintairecorder.command;

import android.content.Context;

import com.example.kintairecorder.dl.KintaiRecorderSelectDL;
import com.example.kintairecorder.vo.KintaiRecordVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * KintaiRecorderSelectのCommand。
 */
public class KintaiRecorderSelectCommand {

    private Context context;

    // 返却用List
    private HashMap<String, String> result = new HashMap<>();

    /**
     * 引数にContextを受け取るコンストラクタ。
     * @param context
     */
    public KintaiRecorderSelectCommand(Context context) {
        this.context = context;
    }

    /**
     * executeメソッド。
     */
    public void execute() {

        // select用の現在日付を取得
        // 当月
        Date thisMonthDate = new Date();
        // 前月
        Date lastMonthDate;

        // 前月のCalendarインスタンスを取得
        Calendar lastMonthCal = Calendar.getInstance();
        lastMonthCal.setTime(thisMonthDate);
        lastMonthCal.add(Calendar.MONTH, -1);
        lastMonthDate = lastMonthCal.getTime();

        // フォーマッター
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");

        // 当月と前月をフォーマット
        String thisMonthStr = sdf.format(thisMonthDate);
        String lastMonthStr = sdf.format(lastMonthDate);

        // DL実行
        KintaiRecorderSelectDL dl = new KintaiRecorderSelectDL(context);
        HashMap<String, ArrayList<KintaiRecordVo>> resultMap = dl.selectTblRecord(thisMonthStr, lastMonthStr);

        // 以下未実装

    }

    /**
     * DB検索結果を取得する。
     * @return result
     */
    public HashMap<String, String> getResult() {
        return result;
    }
}
