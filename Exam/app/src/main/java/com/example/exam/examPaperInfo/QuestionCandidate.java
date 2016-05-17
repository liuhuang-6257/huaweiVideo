package com.example.exam.examPaperInfo;

import java.io.Serializable;

public class QuestionCandidate implements Serializable{
    private long CandidateId;

    private long QuestionID;

    private String CandidateContent;

    private String CandidateValue;

        private int CandidateOrder;

    public long getCandidateId() {
        return CandidateId;
    }

    public void setCandidateId(long candidateId) {
        CandidateId = candidateId;
    }

    public long getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(long questionID) {
        QuestionID = questionID;
    }

    public String getCandidateContent() {
        return CandidateContent;
    }

    public void setCandidateContent(String candidateContent) {
        CandidateContent = candidateContent;
    }

    public String getCandidateValue() {
        return CandidateValue;
    }

    public void setCandidateValue(String candidateValue) {
        CandidateValue = candidateValue;
    }

    public int getCandidateOrder() {
        return CandidateOrder;
    }

    public void setCandidateOrder(int candidateOrder) {
        CandidateOrder = candidateOrder;
    }

    @Override
    public String toString() {
        return "QuestionCandidate{" +
                "CandidateId=" + CandidateId +
                ", QuestionID=" + QuestionID +
                ", CandidateContent='" + CandidateContent + '\'' +
                ", CandidateValue='" + CandidateValue + '\'' +
                ", CandidateOrder=" + CandidateOrder +
                '}';
    }
}