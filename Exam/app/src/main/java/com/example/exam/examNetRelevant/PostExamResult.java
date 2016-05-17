package com.example.exam.examNetRelevant;

import com.example.exam.examPaperInfo.QuestionCandidate;
import com.example.exam.examPaperInfo.TestPaperQuestion;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.sql.SQLOutput;

import okhttp3.Call;

/**
 * Created by q3226257 on 2016/5/4.
 */
public class PostExamResult {

    public void examResult(String guid,String result){

        System.out.println("========"+result);
        OkHttpUtils.post()
                .url("http://223.202.123.180/api/Exam/SubmitOnlineTestPaperData")
                .addHeader("Status-Token", "KwCg59MTgkhqHbWH0XlAwlBG2GTqbFtn")
//                .addParams("userId",guid)
                .addHeader("contentType", "application/json")
                .addParams("examResultInfo", result)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String s) {
                        System.out.println("考试结果" + s);
                    }
                });
    }
}
