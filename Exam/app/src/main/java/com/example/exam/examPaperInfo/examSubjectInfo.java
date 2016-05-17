package com.example.exam.examPaperInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q3226257 on 2016/4/21.
 * 用来保存考试试卷信息的类
 */
public class examSubjectInfo {
    boolean isSelected = false;
    String questionDescribe;
    int subjectCode;//题号

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    List<String> answerDescribbe = new ArrayList<>();

    public List<String> getAnswerDescribbe() {
        return answerDescribbe;
    }

    public void setAnswerDescribbe(List<String> answerDescribbe) {
        this.answerDescribbe = answerDescribbe;
    }

    public String getQuestionDescribe() {
        return questionDescribe;
    }

    public void setQuestionDescribe(String questionDescribe) {
        this.questionDescribe = questionDescribe;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
