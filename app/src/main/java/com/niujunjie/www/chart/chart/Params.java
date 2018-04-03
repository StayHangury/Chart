package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: niujunjie
 * @date: 2018/3/30
 * @email: niujunjie@miao.cn
 * @function: 图表数据设置
 */
public class Params {

    /**
     * 画线的颜色
     */
    private String[] lineColor = {"#987de6", "#f9870e"};

    private XAxis xAxis;
    private YAxis yAxis;


    private List<String> xLables;
    private List<String> yLables;


    /**
     * 计算需要绘制的坐标点的个数
     * 默认可显示区域绘制7条数据，左右个多绘制3个
     */
    private int canVisiableNum = 8;

    private int leftCache = 3;

    private int rightCache = 3;


    /**
     * 图表数据
     */
    private Map<Integer, LineData> mChartData = new HashMap<>();


    private Map<Integer, List<Float>> mData = new HashMap<>();

    private Context mContext;

    public Params(Context context) {
        mContext = context;
        xLables = new ArrayList<>();
        yLables = new ArrayList<>();
        xAxis = new XAxis(context);
        yAxis = new YAxis(context);
    }


    public XAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(XAxis xAxis) {
        this.xAxis = xAxis;
    }

    public YAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(YAxis yAxis) {
        this.yAxis = yAxis;
    }

    public Params setYMaxValue(int value) {
        yAxis.setMaxValue(value);
        return this;
    }

    public Params setYMinValue(int value) {
        yAxis.setMinValue(value);
        return this;
    }

    public Params setyLables(List<String> yLables) {
        this.yLables = yLables;
        yAxis.setLabels(yLables);
        return this;
    }


    public Params setxLables(List<String> xLables) {
        this.xLables = xLables;
        xAxis.setLabels(xLables);
        return this;
    }

    public List<String> getxLables() {
        return xLables;
    }

    public List<String> getyLables() {
        return yLables;
    }

    public int getYMaxValue() {
        return yAxis.getMaxValue();
    }

    public int getYMinValue() {
        return yAxis.getMinValue();
    }

    /**
     * 获取图表要绘制的
     *
     * @return
     */
    public Map<Integer, LineData> getChartData() {
        return mChartData;
    }

    public void setChartData(Map<Integer, LineData> mChartData) {
        this.mChartData = mChartData;
    }

    public Paint getLinePaint(int i) {
        Paint paint = new Paint();
        paint = ChartUtils.initPaint(paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ChartUtils.dip2px(mContext, 3));
        if (i < lineColor.length) {
            paint.setColor(Color.parseColor(lineColor[i]));
        }
        return paint;
    }

    public int getCanVisiableNum() {
        return canVisiableNum;
    }

    public void setCanVisiableNum(int canVisiableNum) {
        this.canVisiableNum = canVisiableNum;
    }

    public int getLeftCache() {
        return leftCache;
    }

    public void setLeftCache(int leftCache) {
        this.leftCache = leftCache;
    }

    public int getRightCache() {
        return rightCache;
    }

    public void setRightCache(int rightCache) {
        this.rightCache = rightCache;
    }

    public Map<Integer, List<Float>> getData() {
        return mData;
    }

    public void setData(Map<Integer, List<Float>> mData) {
        this.mData = mData;
    }

    public Paint getBigCirPaint() {
        Paint paint = ChartUtils.initPaint(new Paint());
        paint.setColor(Color.WHITE);
        return paint;
    }

    public Paint getSmallCirPaint(int i) {
        Paint paint = ChartUtils.initPaint(new Paint());
        if (i < lineColor.length) {
            paint.setColor(Color.parseColor(lineColor[i]));
        }
        return paint;
    }
}
