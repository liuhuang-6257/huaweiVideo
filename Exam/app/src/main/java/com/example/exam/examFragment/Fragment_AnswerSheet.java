package com.example.exam.examFragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.Interface.RecycleViewItemClickListener;
import com.example.exam.Main2Activity;
import com.example.exam.MainActivity;
import com.example.exam.R;
import com.example.exam.examNetRelevant.PostExamResult;
import com.example.exam.examPaperInfo.TestPaperQuestion;
import com.example.exam.examPaperInfo.examSubjectInfo;
import com.example.exam.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.example.exam.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.example.exam.recyclerviewflexibledivider.VerticalDividerItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q3226257 on 2016/4/21.
 */
public class Fragment_AnswerSheet extends Fragment implements View.OnClickListener {
    Context context;
    List<String> listQuestionCode;
    RecyclerView recycleAnswerSheet;
    RelativeLayout sheet_tab_showView;
    TextView textView_examResult;
    TextView textview_exam_result_count;
    TextView textView_commitButton;
    HomeAdapter adapter;
    RecycleViewItemClickListener itemClickListener;


    public Fragment_AnswerSheet(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer_sheet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        setListener();

    }


    private void setListener() {
        textView_commitButton.setOnClickListener(this);
    }

    /**
     * recycleView没有提供item点击的事件，这里采用的是回调方法来给其设置监听
     */
    public void setRecycleViewItemListener(RecycleViewItemClickListener itemListener) {
        this.itemClickListener = itemListener;
    }

    private void initData() {
        listQuestionCode = new ArrayList<>();
        for (int i = 0; i < MainActivity.listTestPaperQuestion.size(); i++) {
            listQuestionCode.add("" + (i + 1));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        cplAnswer();
        adapter.notifyDataSetChanged();

        int selectedNum = 0;
        for (TestPaperQuestion info : MainActivity.listTestPaperQuestion) {
            if (!(info.getResultAnswer().equals("")) &&
                    !info.getResultAnswer().contains(MainActivity.NOANSWER)) {
                selectedNum++;
            }
        }
        textView_examResult.setText(setTextColor(MainActivity.listTestPaperQuestion.size(), selectedNum));
        System.out.println("onHiddenChanged 方法执行了");
    }
//5.17 =======================================================
    //改变字体颜色
    private SpannableStringBuilder setTextColor(int AllNumber, int currentNumber) {
        String test = String.format(getResources().getString(R.string.result), AllNumber, currentNumber);
        SpannableStringBuilder builder = new SpannableStringBuilder(test);
        String a = AllNumber + "";
        String b = currentNumber + "";
        System.out.println("a的位置 = ="+test.indexOf(a));
        System.out.println(a.length() + "===" + b.length());
        ForegroundColorSpan shihuanghuang = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        ForegroundColorSpan shihuanghuang1 = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        builder.setSpan(shihuanghuang, test.indexOf(a), a.length() + test.indexOf(a), Spannable.SPAN_COMPOSING);
        builder.setSpan(shihuanghuang1, test.indexOf(b), test.indexOf(b)+ b.length(), Spannable.SPAN_MARK_MARK);
        return builder;
    }

    private SpannableStringBuilder setTextColor2(int AllNumber, int rightNumber, int erroNumber) {
        String test = String.format(getResources().getString(R.string.examResult), AllNumber, rightNumber, erroNumber);
        SpannableStringBuilder builder = new SpannableStringBuilder(test);
        String a = String.valueOf(AllNumber);
        String b = String.valueOf(rightNumber);
        String c = String.valueOf(erroNumber);

        System.out.println(a.length() + "===" + b.length());
        ForegroundColorSpan shihuanghuang = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        ForegroundColorSpan shihuanghuang1 = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));
        ForegroundColorSpan shihuanghuang2 = new ForegroundColorSpan(getResources().getColor(R.color.numberShiHuang));

        builder.setSpan(shihuanghuang, test.indexOf(a), a.length() + test.indexOf(a), Spannable.SPAN_COMPOSING);
        builder.setSpan(shihuanghuang1,test.indexOf(b), test.indexOf(b)+ b.length(), Spannable.SPAN_COMPOSING);
        builder.setSpan(shihuanghuang2, test.indexOf(c), test.indexOf(c) + c.length(), Spannable.SPAN_COMPOSING);
        return builder;
    }
//5.17 =======================================================

    private void initView(View v) {


        recycleAnswerSheet = (RecyclerView) v.findViewById(R.id.recycle_Answer_Sheet);
        sheet_tab_showView = (RelativeLayout) v.findViewById(R.id.sheet_tab_showView);
        textView_examResult = (TextView) v.findViewById(R.id.textview_exam_result);
        textView_commitButton = (TextView) v.findViewById(R.id.commitButton);
        textview_exam_result_count = (TextView) v.findViewById(R.id.textview_exam_result_count);
        textView_commitButton.setText("提交试卷");
        int selectedNum = 0;
        for (TestPaperQuestion info : MainActivity.listTestPaperQuestion) {
            if (!(info.getResultAnswer().equals("")) &&
                    !info.getResultAnswer().contains(MainActivity.NOANSWER)) {
                selectedNum++;
            }
        }
        textView_examResult.setText(setTextColor(MainActivity.listTestPaperQuestion.size(), selectedNum));
        recycleAnswerSheet.setLayoutManager(new GridLayoutManager(context, 6));
//        recycleAnswerSheet.addItemDecoration(
//                new HorizontalDividerItemDecoration
//                .Builder(context)
//                .size(1)
//                .showLastDivider()
//                .build());
//        recycleAnswerSheet.addItemDecoration(new VerticalDividerItemDecoration
//                .Builder(context)
//                .size(1)
//                .showLastDivider()
//                .build());
        adapter = new HomeAdapter();
        recycleAnswerSheet.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == textView_commitButton) {
            if (MainActivity.isOver) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                textView_commitButton.setText(R.string.reExam);
                sheet_tab_showView.setVisibility(View.GONE);
                textview_exam_result_count.setVisibility(View.VISIBLE);
                textview_exam_result_count.setText(setTextColor2(100, 5, 35));
                MainActivity.isOver = true;
                Toast.makeText(context, "提交试卷了", Toast.LENGTH_SHORT).show();
                Main2Activity.cache.clear();
                cplAnswer();
                String json = new Gson().toJson(Main2Activity.resultData);

                System.out.println("考试结果json=" + Main2Activity.resultData);
                new PostExamResult().examResult("00000000-0000-0000-0000-000000000000", json);

            }

        }
    }

    //加载填空题的答案并设置答案
    public void cplAnswer() {
        for (int i = 0; i < MainActivity.listTestPaperQuestion.size(); i++) {
            StringBuilder answer = new StringBuilder(MainActivity.NOANSWER);
            if (MainActivity.listTestPaperQuestion.get(i).getQuestionModel() == 3) {
                if (MainActivity.cplAnswer.get(i) != null) {
                    for (int j = 0; j < MainActivity.listTestPaperQuestion.get(i).getOptionCount(); j++) {
                        if (answer.toString().equals(MainActivity.NOANSWER) && j == 0) {
                            if (MainActivity.cplAnswer.get(i).get(j) != null) {
                                answer.delete(0, answer.length());
                                answer.append(MainActivity.cplAnswer.get(i).get(j));
                            } else {
                                answer.append(MainActivity.NOANSWER);
                            }
                        } else {
                            if (MainActivity.cplAnswer.get(i).get(j) != null) {
                                answer.append("," + MainActivity.cplAnswer.get(i).get(j));
                            } else {
                                answer.append(MainActivity.NOANSWER);
                            }
                        }

                    }
                }

                MainActivity.listTestPaperQuestion.get(i).setResultAnswer(answer.toString());
            }
        }
        Main2Activity.resultData.setTestPaperQuestion(MainActivity.listTestPaperQuestion);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHold> {

        @Override
        public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHold holder = new MyViewHold(LayoutInflater.from(getActivity())
                    .inflate(R.layout.answer_sheet_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHold holder, final int position) {
            holder.textview_Answersheetitem.setText(listQuestionCode.get(position));

            if (!(MainActivity.listTestPaperQuestion.get(position).getResultAnswer().equals("")) &&
                    !MainActivity.listTestPaperQuestion.get(position).getResultAnswer().contains(MainActivity.NOANSWER)
                    ) {
                holder.textview_Answersheetitem.setBackgroundResource(R.drawable.corner_selected_answersheet);
            } else {
                holder.textview_Answersheetitem.setBackgroundResource(R.drawable.corner_answersheet);
            }
            holder.textview_Answersheetitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClickListener(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return MainActivity.listTestPaperQuestion.size();
        }

        class MyViewHold extends RecyclerView.ViewHolder {
            TextView textview_Answersheetitem;

            public MyViewHold(View itemView) {
                super(itemView);
                textview_Answersheetitem = (TextView) itemView.findViewById(R.id.textview_Answer_sheet_item);

            }
        }
    }

}
