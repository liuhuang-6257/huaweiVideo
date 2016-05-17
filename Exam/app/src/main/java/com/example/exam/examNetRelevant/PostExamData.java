package com.example.exam.examNetRelevant;

import com.example.exam.Interface.IPostExamDatCompelet;
import com.example.exam.examPaperInfo.ExamSubjectDataRoot;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import io.vov.vitamio.utils.Log;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by q3226257 on 2016/4/29.
 */
public class PostExamData {

    public void postExamSubjectData(final IPostExamDatCompelet postExamDatCompelet) {
        OkHttpUtils.get()
                .url("http://223.202.123.180/api/Exam/GetOnlineExamTestPaperData")
                .addHeader("Status-Token", "KwCg59MTgki3NpZQYl5clg9nrX6by+0K")
                .addParams("examInfoId", "2")
                .addParams("userId", "00000000-0000-0000-0000-000000000000")
                .addParams("activityId", "20160309-1714-3661-F465-001D286E4D32")
                .build()
                .execute(new callBackExamSubjectData() {
                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.println("++++++");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(ExamSubjectDataRoot examSubjectDataRoot) {
                        System.out.println("++++++" + examSubjectDataRoot.toString());
                        if (examSubjectDataRoot!=null) {
                                postExamDatCompelet.postExamDatCompelet(examSubjectDataRoot.getResultData(),examSubjectDataRoot.getMsgCode());
                        }
                    }
                });
    }

    public abstract class callBackExamSubjectData extends Callback<ExamSubjectDataRoot> {
        @Override
        public ExamSubjectDataRoot parseNetworkResponse(Response response) throws Exception {
            String rsp = response.body().string();
            ExamSubjectDataRoot eSDR = new Gson().fromJson(rsp, ExamSubjectDataRoot.class);
            Log.i("数据==",rsp.toString());
//            System.out.println("数据=="+rsp.toString());
            return eSDR;
        }
    }
}
