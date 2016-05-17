package com.example.exam.examPaperInfo;

import java.io.Serializable;
import java.util.List;

public class TestPaperQuestion implements Serializable{
    private long PaperCatalogUpID;

    private long PaperCatalogID;

    private long PaperQuestionID;

    private double PaperQuestionScore;

    private int PaperQuestionOrder;

    private int QuestionModel;

    private int OptionCount;

    private String QuestionStem;

    private String CorrectAswer;

    private String ResultAnswer;

    private List<QuestionCandidate> QuestionCandidate;

    public long getPaperCatalogUpID() {
        return PaperCatalogUpID;
    }

    public void setPaperCatalogUpID(long paperCatalogUpID) {
        PaperCatalogUpID = paperCatalogUpID;
    }

    public long getPaperCatalogID() {
        return PaperCatalogID;
    }

    public void setPaperCatalogID(long paperCatalogID) {
        PaperCatalogID = paperCatalogID;
    }

    public long getPaperQuestionID() {
        return PaperQuestionID;
    }

    public void setPaperQuestionID(long paperQuestionID) {
        PaperQuestionID = paperQuestionID;
    }

    public double getPaperQuestionScore() {
        return PaperQuestionScore;
    }

    public void setPaperQuestionScore(double paperQuestionScore) {
        PaperQuestionScore = paperQuestionScore;
    }

    public int getPaperQuestionOrder() {
        return PaperQuestionOrder;
    }

    public void setPaperQuestionOrder(int paperQuestionOrder) {
        PaperQuestionOrder = paperQuestionOrder;
    }

    public int getOptionCount() {
        return OptionCount;
    }

    public void setOptionCount(int optionCount) {
        OptionCount = optionCount;
    }

    public int getQuestionModel() {
        return QuestionModel;
    }

    public void setQuestionModel(int questionModel) {
        QuestionModel = questionModel;
    }

    public String getQuestionStem() {
        return QuestionStem;
    }

    public void setQuestionStem(String questionStem) {
        QuestionStem = questionStem;
    }

    public String getCorrectAswer() {
        return CorrectAswer;
    }

    public void setCorrectAswer(String correctAswer) {
        CorrectAswer = correctAswer;
    }

    public String getResultAnswer() {
        return ResultAnswer;
    }

    public void setResultAnswer(String resultAnswer) {
        ResultAnswer = resultAnswer;
    }

    public List<com.example.exam.examPaperInfo.QuestionCandidate> getQuestionCandidate() {
        return QuestionCandidate;
    }

    public void setQuestionCandidate(List<com.example.exam.examPaperInfo.QuestionCandidate> questionCandidate) {
        QuestionCandidate = questionCandidate;
    }

    @Override
    public String toString() {
        return "TestPaperQuestion{" +
                "PaperCatalogUpID=" + PaperCatalogUpID +
                ", PaperCatalogID=" + PaperCatalogID +
                ", PaperQuestionID=" + PaperQuestionID +
                ", PaperQuestionScore=" + PaperQuestionScore +
                ", PaperQuestionOrder=" + PaperQuestionOrder +
                ", QuestionModel=" + QuestionModel +
                ", OptionCount=" + OptionCount +
                ", QuestionStem='" + QuestionStem + '\'' +
                ", CorrectAswer='" + CorrectAswer + '\'' +
                ", ResultAnswer='" + ResultAnswer + '\'' +
                ", QuestionCandidate=" + QuestionCandidate +
                '}';
    }
}