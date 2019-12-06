package com.hjy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hjy.customview.R;

import java.security.Key;

public class CustomTextView extends View {

    private static final String TAG = "CustomTextView";

    private static final String INSTANCE = "instance";
    private static final String KEY_TEXT = "key_text";

    private boolean booleanText = false;
    private String stringText = "default";
    private int integerText = -1;
    private float dimensionText = 0;
    private int intText = -1;
    private Paint mPaint;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);

//        booleanText = ta.getBoolean(R.styleable.CustomTextView_textView_boolean, false);
//        stringText = ta.getString(R.styleable.CustomTextView_textView_string);
//        integerText = ta.getInteger(R.styleable.CustomTextView_textView_integer, -1);
//        dimensionText = ta.getDimension(R.styleable.CustomTextView_textView_dimension, 0);
//        intText = ta.getInt(R.styleable.CustomTextView_textView_enum, -1);

        for (int i = 0; i < ta.getIndexCount(); i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.CustomTextView_textView_boolean:
                    booleanText = ta.getBoolean(R.styleable.CustomTextView_textView_boolean, false);
                    break;
                case R.styleable.CustomTextView_textView_string:
                    stringText = ta.getString(R.styleable.CustomTextView_textView_string);
                    break;
                case R.styleable.CustomTextView_textView_integer:
                    integerText = ta.getInteger(R.styleable.CustomTextView_textView_integer, -1);
                    break;
                case R.styleable.CustomTextView_textView_dimension:
                    dimensionText = ta.getDimension(R.styleable.CustomTextView_textView_dimension, 0);
                    break;
                case R.styleable.CustomTextView_textView_enum:
                    intText = ta.getInt(R.styleable.CustomTextView_textView_enum, -1);
                    break;
            }
        }

        Log.d(TAG, "booleanText: " + booleanText + ",stringText:" + stringText + ",integerText:" + integerText + ",dimensionText:" + dimensionText + ",intText:" + intText);

        ta.recycle();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {//EXACTLY是指父级已确定确切的大小
            width = widthSize;
        } else {
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();

            if (widthMode == MeasureSpec.AT_MOST) {//AT_MOST是指子级可以根据需要大到指定的大小,即子级你至多显示多少
                width = Math.min(needWidth, widthSize);
            } else {                             //UNSPECIFIED该模式是指父级未对子级施加任何约束。它可以是它想要的任何尺寸。
                width = needWidth;
            }
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int needHeight = measureHeight() + getPaddingTop() + getPaddingBottom();

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(needHeight, heightSize);
            } else {
                height = needHeight;
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mPaint.getStrokeWidth() / 2, mPaint);
//        //画线
//        mPaint.setStrokeWidth(1);
//
////        canvas.drawLine(0, getHeight() / 2, getWidth(), 0, mPaint);
////        canvas.drawLine(getWidth() , 0, getWidth() / 2, getHeight(), mPaint);
//
//        canvas.drawLines(new float[]{
//                0, getHeight() / 2, getWidth(), getHeight() / 2,
//                getWidth(), getHeight() / 2, 0, getHeight(),
//                0, getHeight(), getWidth() / 2, 0,
//                getWidth() / 2, 0, getWidth(), getHeight(),
//                getWidth(), getHeight(), 0, getHeight() / 2}, mPaint);
        //画文本
        mPaint.setTextSize(60);
        mPaint.setStrokeWidth(0);
        canvas.drawText(stringText, 0, stringText.length(), 0, getHeight(), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        stringText = "888888";
        invalidate();//重绘
        return true;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT,stringText);
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        return bundle;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            stringText = bundle.getString(KEY_TEXT);
            return;
        }

        super.onRestoreInstanceState(state);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);//STROKE空心  FILL实心
        mPaint.setStrokeWidth(6);//画笔的宽度
        mPaint.setColor(Color.RED);//画笔的颜色
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
    }

    private int measureWidth() {
        return 0;
    }

    private int measureHeight() {
        return 0;
    }

}
