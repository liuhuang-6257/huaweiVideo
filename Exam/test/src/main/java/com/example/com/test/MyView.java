package com.example.com.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by q3226257 on 2016/5/13.
 */
public class MyView extends View{
    Paint mPaint ;
    Context context;
    TypedArray ty;
    public MyView(Context context) {
        super(context);
        this.context = context;
        mPaint = new Paint();
//        mPaint.setAntiAlias(true); //消除锯齿
//        mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
         ty = context.obtainStyledAttributes(attrs,R.styleable.MyView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int center = getWidth()/2;
        int innerCircle = dip2px(context, 83); //设置内圆半径
        int ringWidth = dip2px(context, 5); //设置圆环宽度
//        //绘制内圆
//        mPaint.setARGB(155, 167, 190, 206);
//        mPaint.setStrokeWidth(2);
//        canvas.drawCircle(center, center, innerCircle, mPaint);
//
//        //绘制圆环
//        mPaint.setARGB(255, 212, 225, 233);
//        mPaint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(center, center, innerCircle + 1 + ringWidth / 2, mPaint);

        //绘制外圆
        mPaint.setARGB(155, 167, 190, 206);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        float h = getPaddingLeft() + getPaddingRight();
        float v=getPaddingTop()+getPaddingBottom();
        float aa = Math.min((getWidth() / 2) - h, (getHeight() / 2) - v);
//        canvas.drawCircle(center, getHeight() / 2, aa, mPaint);

        float jiaodu = ty.getFloat(R.styleable.MyView_jiaodu, 200);
        System.out.println(jiaodu + "jiaodu+++++++++++++");
        RectF a = new RectF(0+getPaddingLeft(),0+getPaddingTop(),getWidth()-getPaddingRight()
                ,getHeight()-getPaddingBottom());
        canvas.drawArc(a,20,jiaodu,false,mPaint);
//        canvas.drawOval(a,mPaint);

        super.onDraw(canvas);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
