package com.example.kintairecorder.vo;

/**
 * KintaiRecorderのVo。
 * TBL_KINTAI_RECORDのレコードを格納する。
 */
public class KintaiRecordVo {

    private String date;
    private String time;
    private String reason;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
