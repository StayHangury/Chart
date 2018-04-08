package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: niujunjie
 * @date: 2018/3/28
 * @email: niujunjie@miao.cn
 * @function: x轴相关
 */
public class XAxis {

    private final float oneDayMsec = 24 * 60 * 60 * 1000;

    /**
     * x坐标值数据
     */
    private List<Float> mValues;


    /**
     * 背景线坐标
     */
    private List<Float> mLineValues;

    private List<String> mDetailData;


    /**
     * x轴标签(x轴显示的数据)
     */
    private List<String> mLabels;


    /**
     * x轴标签的个数
     */
    private int xLabelsNum = 7;


    /**
     * 图表一屏可见个数
     */
    private int onePageCount = 7;

    /**
     * x轴的画笔
     */
    private TextPaint mPaint;


    /**
     * x坐标开始计算的位置
     */
    private float margenRight;


    private float offset;

    private float space;


    /**
     * x轴数据显示的开始位置
     */
    private int start;

    /**
     * x轴数据显示的结束位置
     */
    private int end;

    public XAxis(Context context) {
        mValues = new ArrayList<>();
        mLineValues = new ArrayList<>();
        mLabels = new ArrayList<>();
        mPaint = (TextPaint) ChartUtils.initPaint(new TextPaint());
        mPaint.setTextSize(ChartUtils.dip2px(context, 10));
        mPaint.setColor(Color.parseColor("#9e9e9e"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mPaint.setShadowLayer(5, 3, 3, 0x33535353);
        }
        margenRight = ChartUtils.getValueWidth(mPaint, "MM--dd");
    }

    public void calcValues(RectF xArea, int start, int end) {
        this.start = start;
        this.end = end;

        /**
         * 按照毫秒值划分x坐标数据
         */
        if (mDetailData != null) {
            xLabelsNum = xLabelsNum + 1;
        } else {

        }


        float width = Math.abs(xArea.width()) - margenRight * 2;
        space = width * 1f / (onePageCount - 1);

        if (mLabels != null) {
            end = mLabels.size() > end ? end : mLabels.size();
        } else {
            end = xLabelsNum;
        }

        if (start != end) {
            mValues.clear();
        }
        for (int i = start; i < end; i++) {
            float x = xArea.right - margenRight - space * i;
            mValues.add(x);
            if (start == 0) {
                mLineValues.add(x);
            }
        }

    }


    public List<Float> getValues() {
        return mValues;
    }

    public void setValues(List<Float> mValues) {
        this.mValues = mValues;
    }

    public TextPaint getPaint() {
        return mPaint;
    }

    public void setPaint(TextPaint mPaint) {
        this.mPaint = mPaint;
    }


    public List<String> getLabels() {
        return mLabels;
    }

    public void setLabels(List<String> mLabels) {
        this.mLabels.clear();
        this.mLabels.addAll(mLabels);
        xLabelsNum = mLabels.size();
    }

    public int getxLabelsNum() {
        return xLabelsNum;
    }

    public void setxLabelsNum(int xLabelsNum) {
        this.xLabelsNum = xLabelsNum;
    }

    public int getOnePageCount() {
        return onePageCount;
    }

    public float getMargenRight() {
        return margenRight;
    }


    public void setOnePageCount(int onePageCount) {
        this.onePageCount = onePageCount;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getOffset() {
        return offset;
    }


    public float getSpace() {
        return space;
    }

    public List<Float> getLineValues() {
        return mLineValues;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
