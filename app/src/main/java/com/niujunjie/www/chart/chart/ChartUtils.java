package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * @author: niujunjie
 * @date: 2018/3/29
 * @email: niujunjie@miao.cn
 * @function:
 */
public class ChartUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static Paint initPaint(Paint p) {
        if (p instanceof TextPaint) {
            p.setAntiAlias(true);
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStyle(Paint.Style.FILL);
            p.setTextAlign(Paint.Align.CENTER);
        } else {
            // 设置是否抗锯齿
            p.setAntiAlias(true);
            // 帮助消除锯齿
            p.setFlags(Paint.ANTI_ALIAS_FLAG);
            p.setStrokeCap(Paint.Cap.ROUND);
        }
        return p;
    }


    public static float getTextHeight(Paint textPaint) {
        return -textPaint.ascent() - textPaint.descent();
    }

    public static float getValueWidth(Paint paint, String value) {
        return paint.measureText(value);
    }
}
