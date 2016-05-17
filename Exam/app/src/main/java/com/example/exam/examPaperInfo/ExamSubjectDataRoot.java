package com.example.exam.examPaperInfo;

import java.io.Serializable;

public class ExamSubjectDataRoot implements Serializable{
    private String Message;

    private String MsgCode;

    private ResultData ResultData;

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMsgCode(String MsgCode) {
        this.MsgCode = MsgCode;
    }

    public String getMsgCode() {
        return this.MsgCode;
    }

    public void setResultData(ResultData ResultData) {
        this.ResultData = ResultData;
    }

    public ResultData getResultData() {
        return this.ResultData;
    }

    @Override
    public String toString() {
        return "ExamSubjectDataRoot{" +
                "Message='" + Message + '\'' +
                ", MsgCode='" + MsgCode + '\'' +
                ", ResultData=" + ResultData +
                '}';
    }
}