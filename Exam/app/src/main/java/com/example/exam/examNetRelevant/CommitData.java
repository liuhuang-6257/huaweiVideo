package com.example.exam.examNetRelevant;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by q3226257 on 2016/4/20.
 */
public class CommitData {
    public void commitExamData() {
        OkHttpUtils.post()
                .url("")
                .addHeader("", "")
                .addParams("", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) {

                    }
                });
    }
}
