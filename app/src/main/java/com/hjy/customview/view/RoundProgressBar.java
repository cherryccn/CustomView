package com.hjy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hjy.customview.R;
import com.hjy.customview.utils.DpPxUtils;

public class RoundProgressBar extends View {

    public static final String INSTANCE = "INSTANCE";
    public static final String KEY_PROGRESS = "key_progress";
    private int mRadius = 30;
    private int mColor = Color.BLACK;
    private int mLineHeight = 1;
    private int mTextSize = 16;
    private int mProgress = 0;

    private Paint mPaint;

    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.RoundProgressBar_radius:
                    mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_radius, DpPxUtils.dp2Px(context, 30));
                    break;
                case R.styleable.RoundProgressBar_android_textSize:
                    mTextSize = (int) ta.getDimension(R.styleable.RoundProgressBar_android_textSize, 16);
                    break;
                case R.styleable.RoundProgressBar_color:
                    mColor = ta.getColor(R.styleable.RoundProgressBar_color, Color.BLACK);
                    break;
                case R.styleable.RoundProgressBar_android_progress:
                    mProgress = (int) ta.getDimension(R.styleable.RoundProgressBar_android_progress, 0);
                    break;
                case R.styleable.RoundProgressBar_line_width:
                    mLineHeight = (int) ta.getDimension(R.styleable.RoundProgressBar_line_width, 1);
                    break;
            }
        }
        ta.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(mColor);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(needWidth, widthSize);
            } else {
                width = needWidth;
            }
        }

        int height = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
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

    private int measureHeight() {
        return 0;
    }

    private int measureWidth() {
        return 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PROGRESS, mProgress);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mProgress = bundle.getInt(KEY_PROGRESS);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
