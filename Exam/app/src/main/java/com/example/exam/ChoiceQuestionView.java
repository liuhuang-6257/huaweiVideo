package com.example.exam;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exam.examPaperInfo.TestPaperQuestion;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;

/**
 * Created by q3226257 on 2016/4/18.
 */
public class ChoiceQuestionView extends Fragment {


    Context context;
    ListView answerList;
    //选择题模式（单选  多选 判断）
    public static final int SINGLECHOICE = ListView.CHOICE_MODE_SINGLE;
    public static final int MULTIPLECHOICE = ListView.CHOICE_MODE_MULTIPLE;
    public static final int COMPLETIONQUESTION = 5270;
    private static final String TWENTYSIXLETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int choiceMode = SINGLECHOICE;//默认是单选选择题

    TestPaperQuestion testPaperQuestion;//试题信息

    Map<Integer, String> mapMutilAnswersResult;//用来储存多选的map
    String mutilAnswer = MainActivity.NOANSWER;//多选的答案

    Map<Integer, String> mapCplAnswersResult;//用来储存填空map


    StringBuilder stringBuilder;
    int clickPosition = 0;//记录点击的是那个item的position

    int currentQuestion;//当前题号

    Handler handler;

    BaseAdapter adapter = new BaseAdapter() {
        MyViewHold vH;

        @Override
        public int getCount() {
            return testPaperQuestion.getOptionCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            if (choiceMode == COMPLETIONQUESTION) {
                String a = MainActivity.listTestPaperQuestion.get(position).getQuestionStem();


                vH = new MyViewHold();
                convertView = LayoutInflater.from(context).inflate(R.layout.exam_completion_item, null);
                vH.et = (EditText) convertView.findViewById(R.id.editText_exam_completion);
                convertView.setTag(vH);

                vH.et.setHint("请将第" + position + "个答案填在这里");

                if(MainActivity.cplAnswer.get(currentQuestion)!=null){
                    mapCplAnswersResult = MainActivity.cplAnswer.get(currentQuestion);
                    if(MainActivity.cplAnswer.get(currentQuestion).get(position)!=null){
                        vH.et.setText(MainActivity.cplAnswer.get(currentQuestion).get(position));
                    }
                }
                vH.et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mapCplAnswersResult.put(position,s.toString());
                        MainActivity.cplAnswer.put(currentQuestion, mapCplAnswersResult);
                    }
                });

                vH.et.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            clickPosition = position;
                        }
                        return false;
                    }
                });
                vH.et.clearFocus();
                if (clickPosition == position) {
                    vH.et.requestFocus();
                    vH.et.setSelection(vH.et.getText().length());
                }
                return convertView;
            }
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //+++++++++++++++++++++++下面是选择的适配+++++++++++++++++++++++++++++++++++++++
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            if (convertView == null) {
                vH = new MyViewHold();
                convertView = LayoutInflater.from(context).inflate(R.layout.answer_item, null);
                vH.iv = (ImageView) convertView.findViewById(R.id.imageView_AnswerSelect);
                vH.tv = (CheckedTextView) convertView.findViewById(R.id.textview_DescribleAnswer);
                convertView.setTag(vH);
            } else {
                vH = (MyViewHold) convertView.getTag();
            }
            if (choiceMode == SINGLECHOICE) {
                if (answerList.isItemChecked(position)) {
                    vH.iv.setImageResource(R.drawable.singleselected);
                    vH.tv.setChecked(true);
                    System.out.println(vH.tv.isChecked());

                    System.out.print("设置第" + currentQuestion + "题的答案成功");
                    System.out.println(MainActivity.listTestPaperQuestion.get(currentQuestion).getResultAnswer() + "===>");
                    MainActivity.listTestPaperQuestion.get(currentQuestion).setResultAnswer(
                            testPaperQuestion.getQuestionCandidate()
                                    .get(position).getCandidateValue() + ""
                    );
                    System.out.println(MainActivity.listTestPaperQuestion.get(currentQuestion).getResultAnswer());
                } else {

                    vH.tv.setChecked(false);
                    System.out.println(vH.tv.isChecked());
                    vH.iv.setImageResource(R.drawable.singlenoselected);
                }
            } else {
                if(choiceMode == MULTIPLECHOICE){
                    if (answerList.isItemChecked(position)) {
                        vH.tv.setChecked(true);
                        vH.iv.setImageResource(R.drawable.mutilselected);
                        mapMutilAnswersResult.put(position, testPaperQuestion.getQuestionCandidate()
                                .get(position).getCandidateValue() + "");
                    } else {
                        vH.tv.setChecked(false);
                        mapMutilAnswersResult.remove(position);
                        vH.iv.setImageResource(R.drawable.mutilenoselected);
                    }


                    if (mapMutilAnswersResult.get(position) != null) {
                        if (stringBuilder.toString().equals(MainActivity.NOANSWER)) {
                            stringBuilder.delete(0, stringBuilder.length());
                            stringBuilder.append(mapMutilAnswersResult.get(position));
                        } else {
                            stringBuilder.append("," + mapMutilAnswersResult.get(position));
                        }
                    }
                    if((position+1) == testPaperQuestion.getOptionCount()){
                        System.out.print("设置第" + currentQuestion + "题的答案成功");
                        System.out.println(MainActivity.listTestPaperQuestion.get(currentQuestion).getResultAnswer() + "===>");
                        MainActivity.listTestPaperQuestion.get(currentQuestion).setResultAnswer(
                                stringBuilder.toString());
                        System.out.println(MainActivity.listTestPaperQuestion.get(currentQuestion).getResultAnswer());
                    }
                }
            }
            vH.tv.setText(TWENTYSIXLETTERS.charAt(position) + ".\t" + testPaperQuestion.getQuestionCandidate().get(position).getCandidateContent());
            return convertView;
        }
    };

    class MyViewHold {
        ImageView iv;
        CheckedTextView tv;
        EditText et;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy--------->");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView--------->");
    }

    public ChoiceQuestionView(Context context, TestPaperQuestion testPaperQuestion, Handler handler, int currentQuestion) {
        this.context = context;
        this.handler = handler;
        this.testPaperQuestion = testPaperQuestion;
        this.currentQuestion = currentQuestion;
    }

    //暴露一个方法设置选择题模式
    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.answer_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapMutilAnswersResult = new HashMap<>();
        stringBuilder = new StringBuilder(mutilAnswer);
        mapCplAnswersResult  = new HashMap<>();
        answerList = (ListView) view.findViewById(R.id.Answer_listView);
        answerList.setAdapter(adapter);

        answerList.setChoiceMode(choiceMode);
        answerList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println("onScrollStateChanged");
//                InputMethodManager imm = ( InputMethodManager )context.getSystemService( Context.INPUT_METHOD_SERVICE );
//                //切换界面的时候关闭输入法
//                if ( imm.isActive( ) ) {
//                    imm.hideSoftInputFromWindow( answerList.getApplicationWindowToken( ) , 0 );
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                System.out.println("onScroll");
                handler.sendEmptyMessage(1);
            }
        });
        answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainActivity.isOver) return;
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(MainActivity.NOANSWER);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
