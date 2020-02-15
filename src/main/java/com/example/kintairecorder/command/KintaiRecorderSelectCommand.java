package com.example.kintairecorder.command;

import android.content.Context;

import com.example.kintairecorder.KintaiRecorderConst;
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

    // 取得内容
    private final int order;
    // Context
    private Context context;
    // 返却用文字列
    private String returnStr = "";

    /**
     * 引数にContextと取得内容(int)を受け取るコンストラクタ。
     * @param context
     * @param order
     */
    public KintaiRecorderSelectCommand(Context context, int order) {
        this.context = context;
        this.order = order;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

        // 当月と前月をフォーマット
        String thisMonthStr = sdf.format(thisMonthDate);
        String lastMonthStr = sdf.format(lastMonthDate);

        // DL実行
        KintaiRecorderSelectDL dl = new KintaiRecorderSelectDL(context);
        HashMap<String, ArrayList<KintaiRecordVo>> resultMap = dl.selectTblKintaiRecord(thisMonthStr, lastMonthStr);

        // 分離処理
        ArrayList<KintaiRecordVo> thisMonthRecordList = resultMap.get(thisMonthStr);
        ArrayList<KintaiRecordVo> lastMonthRecordList = resultMap.get(lastMonthStr);

        this.returnStr = createReturnStr(thisMonthRecordList, lastMonthRecordList, this.order);

    }

    /**
     * 画面表示用文字列を生成する。
     * @param thisList
     * @param lastList
     * @return sb.toString()
     */
    private String createReturnStr(ArrayList<KintaiRecordVo> thisList, ArrayList<KintaiRecordVo> lastList, int order) {

        // TODO orderを使って表示順を指定できるようにする(現状は先月→今月で固定)

        // 組み立て用sb
        StringBuilder sb = new StringBuilder();

        // 先月分のレコードを連結して文字列にする
        sb.append(KintaiRecorderConst.NEWLINE);
        for (KintaiRecordVo lastVo : lastList) {
            sb.append(lastVo.getDate());
            sb.append(KintaiRecorderConst.HALF_SPACE_THREE);
            sb.append(KintaiRecorderConst.SHIGYOU);
            sb.append(lastVo.getTime());
            sb.append(KintaiRecorderConst.NEWLINE);
        }

        // 今月分のレコードを連結して文字列にする
        sb.append(KintaiRecorderConst.NEWLINE);
        for (KintaiRecordVo thisVo : thisList) {
            sb.append(thisVo.getDate());
            sb.append(KintaiRecorderConst.HALF_SPACE_THREE);
            sb.append(KintaiRecorderConst.SHIGYOU);
            sb.append(thisVo.getTime());
            sb.append(KintaiRecorderConst.NEWLINE);
        }

        return sb.toString();

    }

    /**
     * DB検索結果を取得する。
     * @return returnStr
     */
    public String getReturnStr() {
        return returnStr;
    }
}
