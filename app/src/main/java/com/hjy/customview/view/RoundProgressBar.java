package com.hjy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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

    private int mRadius;
    private int mColor;
    private int mLineWidth;
    private int mTextSize;
    private int mProgress;

    private Paint mPaint;
    private RectF rectF;
    private Rect bounds;

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    /**
     * 获取进度
     *
     * @return
     */
    public int getProgress() {
        return mProgress;
    }

    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.RoundProgressBar_radius:
                    mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_radius, DpPxUtils.dp2Px(context, 30));
                    break;
                case R.styleable.RoundProgressBar_android_textSize:
                    mTextSize = (int) ta.getDimension(R.styleable.RoundProgressBar_android_textSize, DpPxUtils.dp2Px(context, 16));
                    break;
                case R.styleable.RoundProgressBar_color:
                    mColor = ta.getColor(R.styleable.RoundProgressBar_color, Color.BLACK);
                    break;
                case R.styleable.RoundProgressBar_android_progress:
                    mProgress = ta.getInt(R.styleable.RoundProgressBar_android_progress, 0);
                    break;
                case R.styleable.RoundProgressBar_lineWidth:
                    mLineWidth = (int) ta.getDimension(R.styleable.RoundProgressBar_lineWidth, DpPxUtils.dp2Px(context, 1));
                    break;
            }
        }
        ta.recycle();

        initPaint();
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置矩形的范围,这里left和top写0 是因为canvas.translate(getPaddingLeft(), getPaddingTop());已经将原点移动到的该位置
        rectF = new RectF(0, 0, w - getPaddingLeft() * 2, h - getPaddingLeft() * 2);
        bounds = new Rect();
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
        //如果用户穿了一个100，，200  那就不是圆，  这样防止宽高一致。
        width = Math.min(width, height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //todo 本案例中没有讲解到，可以自己学习
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        //绘制初始的细空心圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth * 1.0f / 4);
        canvas.drawCircle(width / 2, height / 2, width / 2 - getPaddingLeft() - mPaint.getStrokeWidth() / 2, mPaint);

        //绘制圆弧
        mPaint.setStrokeWidth(mLineWidth);

        canvas.save();
        //将原点移动到(getPaddingLeft(), getPaddingTop())位置
        canvas.translate(getPaddingLeft(), getPaddingTop());
        //角度
        float angle = mProgress * 1.0f / 100 * 360;
        canvas.drawArc(rectF, 0, angle, false, mPaint);
        canvas.restore();

        //绘制进度文字
        mPaint.setStrokeWidth(0);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);
        String text = mProgress + "%";
        //y文字的基线
        int y = height / 2;
        //获取文字的高度
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        int textHeight = bounds.height();
        canvas.drawText(text, 0, text.length(), width / 2, y + textHeight / 2, mPaint);
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

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    private int measureWidth() {
        return mRadius * 2;
    }

    private int measureHeight() {
        return mRadius * 2;
    }
}
