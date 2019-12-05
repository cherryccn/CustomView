package com.hjy.customview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DpPxUtils {

    public static int dp2Px(Context context, int dpVal) {

//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//
//        float density = displayMetrics.density;
//
//        int px = (int) (dp * density + 0.5f);
//
//        return px;
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,context.getResources().getDisplayMetrics());
    }


    public static int px2Dp(Context context, int pxVal) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        float density = displayMetrics.density;

        int dp = (int) (pxVal / density + 0.5f);

        return dp;

    }

}
