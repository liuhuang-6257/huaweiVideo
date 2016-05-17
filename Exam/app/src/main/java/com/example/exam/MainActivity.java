package com.example.exam;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.Interface.RecycleViewItemClickListener;
import com.example.exam.examFragment.Fragment_AnswerSheet;
import com.example.exam.examFragment.Fragment_AnswerView;
import com.example.exam.examPaperInfo.TestPaperQuestion;
import com.example.exam.examPaperInfo.examSubjectInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewItemClickListener {
    /**
     * 用来保存考试信息的全局变量
     */

//    public static List<examSubjectInfo> listExamSubjectInfo;
    public static List<TestPaperQuestion> listTestPaperQuestion;
    public static boolean isOver = false;//考试是否结束的boolean

    public static String NOANSWER = "NOANSWER";
    public static Map<Integer,Map<Integer,String>> cplAnswer;

    int examTime = 0;//考试时间
    Timer timer;//考试时间定时器
    TextView textView_examTime;
    ImageView imageView_goToAnswerSheet;
    ImageView imageView_examBack;
    ImageView imageView_examTimeIcon;

    Fragment_AnswerSheet fragmentAnswerSheet;
    Fragment_AnswerView fragmentAnswerView;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    textView_examTime.setText(getTimeFromLong(examTime));
                    break;
                case 2:
                    isOver = true;
                    Toast.makeText(getBaseContext(), "考试时间结束", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.alpha, 0);
        initData();
        initview();
        setChangeListner();

    }

    private void initData() {
        cplAnswer = new HashMap<>();
        Intent intent = getIntent();
        listTestPaperQuestion = (List<TestPaperQuestion>)intent.getSerializableExtra("TestPaperQuestion");
        examTime = intent.getIntExtra("examTime",0);
        System.out.println("listTestPaperQuestion =="+listTestPaperQuestion.toString());

        Collections.shuffle(listTestPaperQuestion);

    }

    private void initview() {
        textView_examTime = (TextView) findViewById(R.id.textView_examTime);
        imageView_examBack = (ImageView) findViewById(R.id.imageView_examBack);
        imageView_examTimeIcon = (ImageView) findViewById(R.id.imageView_examTimeIcon);
        imageView_goToAnswerSheet = (ImageView) findViewById(R.id.imageView_goToAnswerSheet);
        fragmentAnswerSheet = new Fragment_AnswerSheet(getBaseContext());
        fragmentAnswerView = new Fragment_AnswerView(getBaseContext());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_answer_relativeLayout, fragmentAnswerView);
        ft.commit();
        showExamTime();

    }

    public void showExamTime() {
        examTime = examTime *60;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
                examTime--;
                if (examTime == 0) {
                    timer.cancel();
                    mHandler.sendEmptyMessage(2);
                }
            }
        }, 1000, 1000);
    }

    //获取系统当前时间转化为string
    public String formatTime(long examTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(examTime);//考试的时间 （毫秒）
        String str = formatter.format(curDate);
        return str;
    }

    private void setChangeListner() {
        fragmentAnswerSheet.setRecycleViewItemListener(this);
        imageView_goToAnswerSheet.setOnClickListener(this);
        imageView_examBack.setOnClickListener(this);
    }

    public static String getTimeFromLong(long time) {
        long seconds = time % 60;
        long minutes = (time / 60) % 60;
        long hours = time / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    @Override
    public void onClick(View v) {
        if (v == imageView_examBack) {
            isOver = false;
            finish();
        }
        if (v == imageView_goToAnswerSheet) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (fragmentAnswerSheet.isAdded()) {
                if (fragmentAnswerSheet.isVisible()) {
                    ft.hide(fragmentAnswerSheet).show(fragmentAnswerView);
                }
                if (fragmentAnswerSheet.isHidden()) {
                    ft.hide(fragmentAnswerView).show(fragmentAnswerSheet);
                }
            } else {
                ft.hide(fragmentAnswerView).add(R.id.main_answer_relativeLayout, fragmentAnswerSheet);
            }
            ft.commit();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOver = false;
        timer.cancel();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isOver) {
            if (event.KEYCODE_BACK == keyCode) {
                Toast.makeText(getBaseContext(), "考试时间不准退出！", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void itemClickListener(View view, int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentAnswerView.isAdded()) {
            ft.hide(fragmentAnswerSheet).show(fragmentAnswerView);
            fragmentAnswerView.jumpto(position);
        } else {
            ft.hide(fragmentAnswerSheet).add(R.id.main_answer_relativeLayout, fragmentAnswerView);
            fragmentAnswerView.jumpto(position);
        }
        ft.commit();
    }
}
