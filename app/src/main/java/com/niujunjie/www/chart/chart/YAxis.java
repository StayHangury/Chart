package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: niujunjie
 * @date: 2018/3/28
 * @email: niujunjie@miao.cn
 * @function: y轴相关操作
 */
public class YAxis {


    /**
     * y轴的画笔
     */
    private TextPaint mPaint;


    /**
     * 最小值
     */
    private int mMinValue = 0;


    /**
     * 最大值
     */
    private int mMaxValue = 10;

    /**
     * y轴标签的个数
     */
    private int yLabelsNum = 7;

    /**
     * 气泡的高度
     */
    private int mPopHeight;


    /**
     * y坐标值数据
     */
    private List<Float> mValues;


    /**
     * y轴标签(y轴显示的数据)
     */
    private List<String> mLabels;


    /**
     * y轴开始和结束距离yArea的top和bottom的距离
     */
    private float yTopBottomPadding;


    /**
     * 相等间隔的y值
     */
    private boolean isNormal = true;


    /**
     * y轴开始绘制的位置
     */
    private float startY;

    public void setLabels(List<String> data) {
        mLabels.clear();
        mLabels.addAll(data);
        yLabelsNum = data.size();
    }

    public YAxis(Context context) {
        mValues = new ArrayList<>();
        mLabels = new ArrayList<>();
        mPopHeight = ChartUtils.dip2px(context, 10);
        mPaint = (TextPaint) ChartUtils.initPaint(new TextPaint());
        mPaint.setTextSize(ChartUtils.dip2px(context, 10));
        mPaint.setColor(Color.parseColor("#535353"));
        mPaint.setTextAlign(Paint.Align.LEFT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mPaint.setShadowLayer(5, 3, 3, 0x33535353);
        }
        yTopBottomPadding = ChartUtils.getTextHeight(mPaint);
    }


    float height = 0;

    float space = 0;

    public void createValues(RectF yArea) {
        mValues.clear();
        height = Math.abs(yArea.height());
        height = height - 2 * yTopBottomPadding - mPopHeight;
        space = 1f / (yLabelsNum - 1) * height;

        for (int i = 0; i < yLabelsNum; i++) {
            float y = yArea.bottom - yTopBottomPadding / 2 - i * space;
            if (i == 0) {
                startY = y;
            }
            mValues.add(y);
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


    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int mMaxValue) {
        this.mMaxValue = mMaxValue;
    }

    public List<String> getLabels() {
        return mLabels;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMinValue(int mMinValue) {
        this.mMinValue = mMinValue;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    /**
     * 两个y轴数据相隔的距离
     *
     * @return
     */
    public float getSpace() {
        return space;
    }

    public void setSpace(float space) {
        this.space = space;
    }

}
