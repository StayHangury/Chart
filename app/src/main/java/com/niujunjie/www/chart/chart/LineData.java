package com.niujunjie.www.chart.chart;

import android.graphics.PointF;

/**
 * @author: niujunjie
 * @date: 2018/4/2
 * @email: niujunjie@miao.cn
 * @function:
 */
public class LineData {

    private PointF point = new PointF();

    private String popValue;


    private float offset;

    public LineData() {

    }

    public LineData(float x, float y) {
        point.set(x, y);
    }


    public void setXY(float x, float y) {
        point.set(x, y);
    }


    public String getPopValue() {
        return popValue;
    }

    public void setPopValue(String popValue) {
        this.popValue = popValue;
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getX(){
        return point.x - offset;
    }

    public float getY() {
        return point.y;
    }

}
