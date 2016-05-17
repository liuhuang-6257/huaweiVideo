package com.example.exam.examFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.exam.ChoiceQuestionView;
import com.example.exam.MFragmentAdapter;
import com.example.exam.MainActivity;
import com.example.exam.MyRelativeLayout;
import com.example.exam.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vov.vitamio.widget.CenterLayout;

/**
 * Created by q3226257 on 2016/4/21.
 */
public class Fragment_AnswerView extends Fragment implements ViewPager.OnPageChangeListener
        , View.OnClickListener {

    ViewPager exam_ViewPager;
    List<Integer> listViews;

    TextView textView_current_subjectCode;//当前题号
    TextView textView_current_subjectTyple;//当前题目类型
    TextView textView_current_subjectAllNum;//当前试卷总题数

    TextView textView_Previous_Subject;//上一题
    TextView textView_Next_Subject;//下一题
    Context context;
    RelativeLayout Rlexam_next_previous;
    MyRelativeLayout Rl_Exam_AnswerView;
    RelativeLayout Rl3_Exam_Question_View;
    TextView textView_QuestionDescrible;//考试问题描述

    View view_exam_fill;//填充的view

    Pattern p = Pattern.compile("(\\{[^\\}]+\\})");//填空的正则

    int currentPage = 0;//记录当前是第几题的int
    int cplMark;
    String questionDescrible;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //输入法管理器
            InputMethodManager imm;
            //输入法管理器初始化
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //切换界面的时候关闭输入法
            if (isShowingInput()) {
                Rlexam_next_previous.setVisibility(View.GONE);
            } else {
                Rlexam_next_previous.setVisibility(View.VISIBLE);
            }

        }
    };

    public boolean isShowingInput() {
        int screenHeight = Rl_Exam_AnswerView.getRootView().getHeight();
        int myHeight = Rl_Exam_AnswerView.getHeight();
        int heightDiff = screenHeight - myHeight;
        System.out.println("onGlobalLayout  screenHeight=" + screenHeight);
        System.out.println("onGlobalLayout myHeight=" + myHeight);
        System.out.println("onGlobalLayout heightDiff=" + heightDiff);
        if (heightDiff > 400) {
            System.out.println("onGlobalLayout Soft keyboard showing");
            return true;
        } else {
            System.out.println("onGlobalLayout Soft keyboard hidden");
            return false;
        }
    }

    private void initview(List<Integer> listViews, View view) {
        Rl_Exam_AnswerView = (MyRelativeLayout) view.findViewById(R.id.Rl_Exam_AnswerView);
        Rl3_Exam_Question_View = (RelativeLayout) view.findViewById(R.id.Rl3_Exam_Question_View);

        Rlexam_next_previous = (RelativeLayout) view.findViewById(R.id.include1).findViewById(R.id.Rlexam_next_previous);
        view_exam_fill = view.findViewById(R.id.view_exam_fill);
        exam_ViewPager = (ViewPager) view.findViewById(R.id.exam_viewpager);
        textView_current_subjectCode = (TextView) view.findViewById(R.id.textView_current_subjectCode);
        textView_current_subjectTyple = (TextView) view.findViewById(R.id.textView_current_subjectTyple);
        textView_current_subjectAllNum = (TextView) view.findViewById(R.id.textView_current_subjectAllNum);
        textView_QuestionDescrible = (TextView) view.findViewById(R.id.textView_DescribleQuestion);
        textView_Previous_Subject = (TextView) view.findViewById(R.id.textView_Previous_Subject);
        textView_Next_Subject = (TextView) view.findViewById(R.id.textView_Next_Subject);
        /**
         * 界面第一次加载
         */
        questionDescrible = MainActivity.listTestPaperQuestion.get(0).getQuestionStem();
        while (questionDescrible.contains("{")) {
            Matcher m = p.matcher(questionDescrible);
            questionDescrible = m.replaceFirst("（" + (cplMark++) + "）");
            System.out.println(questionDescrible);
        }
        cplMark = 0;
        textView_QuestionDescrible.setText(questionDescrible);

        MFragmentAdapter adapter = new MFragmentAdapter(getFragmentManager(), context, listViews, handler);
        exam_ViewPager.setAdapter(adapter);

        textView_current_subjectCode.setText(String.format(getResources()
                .getString(R.string.currentSubjectCode), 1));

        textView_current_subjectAllNum.setText(String.format(getResources()
                .getString(R.string.allSubjectNum), MainActivity.listTestPaperQuestion.size()));

    }

    public Fragment_AnswerView(Context context) {
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViews = new ArrayList<>();
        System.out.println("onViewCreated");
        cplMark = 0;
        Collections.shuffle(MainActivity.listTestPaperQuestion);
        for (int i = 0; i < MainActivity.listTestPaperQuestion.size(); i++) {

            switch (MainActivity.listTestPaperQuestion.get(i).getQuestionModel()) {
                case 1:
                    listViews.add(ChoiceQuestionView.SINGLECHOICE);
                    break;
                case 2:
                    listViews.add(ChoiceQuestionView.MULTIPLECHOICE);
                    break;
                case 3:
                    listViews.add(ChoiceQuestionView.COMPLETIONQUESTION);
                    break;
                case 4:
                    listViews.add(ChoiceQuestionView.SINGLECHOICE);
                    break;
                case 5:
                    //z主观题 还么有做
                    break;
            }

        }
        initview(listViews, view);
        setChangeListner();
    }

    @Override
    public void onResume() {
        super.onResume();
        view_exam_fill.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.BELOW, R.id.Rl3_Exam_Question_View);
                params.addRule(RelativeLayout.ABOVE, R.id.include1);
                params.leftMargin = 20;
                params.rightMargin = 20;
                view_exam_fill.setBackgroundResource(R.color.whiteColor);
                view_exam_fill.setLayoutParams(params);
            }
        });
        Rl3_Exam_Question_View.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, Rl3_Exam_Question_View.getHeight());
//                params.addRule(RelativeLayout.ABOVE,R.id.include1);

                Rl3_Exam_Question_View.setPadding(20, 20, 20, 0);
                params.leftMargin = 20;
                params.rightMargin = 20;
                params.topMargin = 30;
                Rl3_Exam_Question_View.setBackgroundResource(R.drawable.corner_examanswerview);
                Rl3_Exam_Question_View.setLayoutParams(params);
            }
        });
    }

    private void setChangeListner() {
        textView_Previous_Subject.setOnClickListener(this);
        textView_Next_Subject.setOnClickListener(this);
        exam_ViewPager.addOnPageChangeListener(this);

    }

    public void jumpto(int Page) {
        exam_ViewPager.setCurrentItem(Page);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.fragment_answer_view, container, false);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //输入法管理器
        InputMethodManager imm;
        //输入法管理器初始化
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //切换界面的时候关闭输入法
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(exam_ViewPager.getApplicationWindowToken(), 0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (MainActivity.listTestPaperQuestion.get(position).getQuestionModel()) {
            case 1:
                textView_current_subjectTyple.setText(R.string.singleChoice);
                break;
            case 2:
                textView_current_subjectTyple.setText(R.string.mutilChoice);
                break;
            case 3:
                textView_current_subjectTyple.setText(R.string.completionSubject);
                break;
            case 4:
                textView_current_subjectTyple.setText(R.string.judgeChoice);
                break;
            case 5:
                textView_current_subjectTyple.setText(R.string.Subjective);
                break;
        }
        currentPage = position;
        textView_current_subjectCode.setText(String.format(getResources()
                .getString(R.string.currentSubjectCode), position + 1));


        questionDescrible = MainActivity.listTestPaperQuestion.get(position).getQuestionStem();
        while (questionDescrible.contains("{")) {
            Matcher m = p.matcher(questionDescrible);
            questionDescrible = m.replaceFirst("（" + (cplMark++) + "）");
            System.out.println(questionDescrible);
        }
        cplMark = 0;
        textView_QuestionDescrible.setText(questionDescrible);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v == textView_Next_Subject) {
            exam_ViewPager.setCurrentItem(currentPage + 1);
        }
        if (v == textView_Previous_Subject) {
            exam_ViewPager.setCurrentItem(currentPage - 1);
        }
    }


}
