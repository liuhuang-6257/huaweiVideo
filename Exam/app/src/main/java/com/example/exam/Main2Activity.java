package com.example.exam;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.Interface.IPostExamDatCompelet;
import com.example.exam.MainActivity;
import com.example.exam.R;
import com.example.exam.examNetRelevant.PostExamData;
import com.example.exam.examPaperInfo.ExamSubjectDataRoot;
import com.example.exam.examPaperInfo.ResultData;
import com.example.exam.simplecache.ACache;

import java.io.Serializable;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    TextView textView_commitButton;
    TextView textView_ExaminationCount;
    TextView textView_ExaminationCameTime;
    TextView textView_ExaminationTotalScore;
    TextView textView_ExaminationPlayTime;
    TextView textView_ExaminationTyple;
    TextView textView_ExaminationTitle;
    public static ACache cache;

    public static ResultData resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_paper_details);
        textView_commitButton = (TextView) findViewById(R.id.commitButton);
        textView_commitButton.setOnClickListener(this);
        initview();
        initData();

    }
//5.17================================================
    private void initUi() {
        String a[] = resultData.getStartExamTime().split("T");

        textView_ExaminationCount.setText(setTextColor(getResources().getString(R.string.exam_count)
                ,resultData.getQuestionTotalCount()+""));
        textView_ExaminationCameTime.setText(setTextColor(getResources().getString(R.string.exam_publishTime)
                ,a[0]));

        textView_ExaminationTotalScore.setText(setTextColor(getResources().getString(R.string.exam_totalScore)
                ,resultData.getExamTotalScore()+""));

        textView_ExaminationPlayTime.setText(setTextColor(getResources().getString(R.string.exam_time)
                , resultData.getExamTime() + ""));

        textView_ExaminationTyple.setText(setTextColor(getResources().getString(R.string.exam_type)
                , resultData.getExamInfoName()));

        textView_ExaminationTitle.setText(resultData.getExamInfoName() + "");
    }
    //改变字体颜色
    private SpannableStringBuilder setTextColor(String text, String... tag) {
        String test = String.format(text, tag[0]);
        SpannableStringBuilder builder = new SpannableStringBuilder(test);
        String a = tag[0] + "";

        ForegroundColorSpan shihuanghuang = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        ForegroundColorSpan shihuanghuang1 = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        builder.setSpan(shihuanghuang, test.indexOf(a), a.length() + test.indexOf(a), Spannable.SPAN_COMPOSING);
//        builder.setSpan(shihuanghuang1, test.indexOf(b), test.indexOf(b) + b.length(), Spannable.SPAN_MARK_MARK);
        return builder;
    }
    //5.17================================================

    private ResultData initData() {
        resultData = (ResultData) cache.getAsObject("examInfo");
        if (resultData == null) {
            new PostExamData().postExamSubjectData(new IPostExamDatCompelet() {
                @Override
                public void postExamDatCompelet(ResultData result,String msgCode) {
                    if(msgCode.equals("200")){
                        resultData = result;
                        System.out.println("获取结束=" + result.toString());
                        cache.put("examInfo", result,result.getExamTime()*60);
                        initUi();
                    }else {
//                        textView_commitButton.setClickable(false);
                    }
                }
            });
        } else {
            System.out.println("缓存还有数据=" + resultData.toString());
            initUi();
        }
        return resultData;

    }

    private void initview() {
        /**
         * 可根据需要来配置Acache
         */
        cache = ACache.get(getBaseContext());
        textView_ExaminationCount = (TextView) findViewById(R.id.textView_ExaminationCount);
        textView_ExaminationCameTime = (TextView) findViewById(R.id.textView_ExaminationCameTime);
        textView_ExaminationTotalScore = (TextView) findViewById(R.id.textView_ExaminationTotalScore);
        textView_ExaminationPlayTime = (TextView) findViewById(R.id.textView_ExaminationPlayTime);
        textView_ExaminationTyple = (TextView) findViewById(R.id.textView_ExaminationTyple);
        textView_ExaminationTitle = (TextView) findViewById(R.id.textView_ExaminationTitle);
    }

    @Override
    public void onClick(View v) {
        //5.17===========
        if (v == textView_commitButton) {
            if(resultData!=null){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("TestPaperQuestion", (Serializable)resultData.getTestPaperQuestion());
                intent.putExtra("examTime",resultData.getExamTime());
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "获取数据失败,无法考试", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
