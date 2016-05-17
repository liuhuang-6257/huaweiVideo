package com.example.exam.examPaperInfo;

import java.io.Serializable;
import java.util.List;

public class ResultData implements Serializable{
    private String UserId;

    private String UserName;

    private int ExamInfoId;

    private int TestPaperId;

    private String ActivityId;

    private String ExamInfoName;

    private String ExamInfoNo;

    private int PaperDisplayWay;

    private int PaperDifficulty;

    private int MinSubmitTime;

    private double ExamTotalScore;

    private int QuestionTotalCount;

    private int ExamTime;

    private boolean IsExamAfterPass;

    private boolean IsQuestionRandom;

    private boolean IsChoiceRandom;

    private boolean IsCutScreen;

    private boolean IsShowScore;

    private boolean IsShowAnswer;

    private String StartExamTime;

    private List<TestPaperQuestion> TestPaperQuestion;

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserId() {
        return this.UserId;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setExamInfoId(int ExamInfoId) {
        this.ExamInfoId = ExamInfoId;
    }

    public int getExamInfoId() {
        return this.ExamInfoId;
    }

    public void setTestPaperId(int TestPaperId) {
        this.TestPaperId = TestPaperId;
    }

    public int getTestPaperId() {
        return this.TestPaperId;
    }

    public void setActivityId(String ActivityId) {
        this.ActivityId = ActivityId;
    }

    public String getActivityId() {
        return this.ActivityId;
    }

    public void setExamInfoName(String ExamInfoName) {
        this.ExamInfoName = ExamInfoName;
    }

    public String getExamInfoName() {
        return this.ExamInfoName;
    }

    public void setExamInfoNo(String ExamInfoNo) {
        this.ExamInfoNo = ExamInfoNo;
    }

    public String getExamInfoNo() {
        return this.ExamInfoNo;
    }

    public void setPaperDisplayWay(int PaperDisplayWay) {
        this.PaperDisplayWay = PaperDisplayWay;
    }

    public int getPaperDisplayWay() {
        return this.PaperDisplayWay;
    }

    public void setPaperDifficulty(int PaperDifficulty) {
        this.PaperDifficulty = PaperDifficulty;
    }

    public int getPaperDifficulty() {
        return this.PaperDifficulty;
    }

    public void setMinSubmitTime(int MinSubmitTime) {
        this.MinSubmitTime = MinSubmitTime;
    }

    public int getMinSubmitTime() {
        return this.MinSubmitTime;
    }

    public void setExamTotalScore(double ExamTotalScore) {
        this.ExamTotalScore = ExamTotalScore;
    }

    public double getExamTotalScore() {
        return this.ExamTotalScore;
    }

    public void setQuestionTotalCount(int QuestionTotalCount) {
        this.QuestionTotalCount = QuestionTotalCount;
    }

    public int getQuestionTotalCount() {
        return this.QuestionTotalCount;
    }

    public void setExamTime(int ExamTime) {
        this.ExamTime = ExamTime;
    }

    public int getExamTime() {
        return this.ExamTime;
    }

    public void setIsExamAfterPass(boolean IsExamAfterPass) {
        this.IsExamAfterPass = IsExamAfterPass;
    }

    public boolean getIsExamAfterPass() {
        return this.IsExamAfterPass;
    }

    public void setIsQuestionRandom(boolean IsQuestionRandom) {
        this.IsQuestionRandom = IsQuestionRandom;
    }

    public boolean getIsQuestionRandom() {
        return this.IsQuestionRandom;
    }

    public void setIsChoiceRandom(boolean IsChoiceRandom) {
        this.IsChoiceRandom = IsChoiceRandom;
    }

    public boolean getIsChoiceRandom() {
        return this.IsChoiceRandom;
    }

    public void setIsCutScreen(boolean IsCutScreen) {
        this.IsCutScreen = IsCutScreen;
    }

    public boolean getIsCutScreen() {
        return this.IsCutScreen;
    }

    public void setIsShowScore(boolean IsShowScore) {
        this.IsShowScore = IsShowScore;
    }

    public boolean getIsShowScore() {
        return this.IsShowScore;
    }

    public void setIsShowAnswer(boolean IsShowAnswer) {
        this.IsShowAnswer = IsShowAnswer;
    }

    public boolean getIsShowAnswer() {
        return this.IsShowAnswer;
    }

    public void setStartExamTime(String StartExamTime) {
        this.StartExamTime = StartExamTime;
    }

    public String getStartExamTime() {
        return this.StartExamTime;
    }

    public void setTestPaperQuestion(List<TestPaperQuestion> TestPaperQuestion) {
        this.TestPaperQuestion = TestPaperQuestion;
    }

    public List<TestPaperQuestion> getTestPaperQuestion() {
        return this.TestPaperQuestion;
    }


    @Override
    public String toString() {
        return "ResultData{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ExamInfoId=" + ExamInfoId +
                ", TestPaperId=" + TestPaperId +
                ", ActivityId='" + ActivityId + '\'' +
                ", ExamInfoName='" + ExamInfoName + '\'' +
                ", ExamInfoNo='" + ExamInfoNo + '\'' +
                ", PaperDisplayWay=" + PaperDisplayWay +
                ", PaperDifficulty=" + PaperDifficulty +
                ", MinSubmitTime=" + MinSubmitTime +
                ", ExamTotalScore=" + ExamTotalScore +
                ", QuestionTotalCount=" + QuestionTotalCount +
                ", ExamTime=" + ExamTime +
                ", IsExamAfterPass=" + IsExamAfterPass +
                ", IsQuestionRandom=" + IsQuestionRandom +
                ", IsChoiceRandom=" + IsChoiceRandom +
                ", IsCutScreen=" + IsCutScreen +
                ", IsShowScore=" + IsShowScore +
                ", IsShowAnswer=" + IsShowAnswer +
                ", StartExamTime='" + StartExamTime + '\'' +
                ", TestPaperQuestion=" + TestPaperQuestion +
                '}';
    }
}