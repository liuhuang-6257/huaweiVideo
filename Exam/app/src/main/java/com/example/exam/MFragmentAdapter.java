package com.example.exam;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by q3226257 on 2016/4/26.
 */
public class MFragmentAdapter extends FragmentStatePagerAdapter {
    Context context;
    List<Integer> listViews;
    Handler handler;
    public MFragmentAdapter(FragmentManager fm, Context context, List<Integer> listViews,Handler handler) {
        super(fm);
        this.context = context;
        this.handler = handler;
        this.listViews = listViews;
    }

    @Override
    public Fragment getItem(int position) {
        ChoiceQuestionView choiceQuestionView = new ChoiceQuestionView(context, MainActivity.listTestPaperQuestion.get(position),handler,position);
        choiceQuestionView.setChoiceMode(listViews.get(position));

        return choiceQuestionView;
    }

    @Override
    public int getCount() {
        return listViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
