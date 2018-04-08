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

    /**
     * 点击是气泡要显示的值
     */
    private float popValue;


    /**
     * 所属的线段
     */
    private int LineNum;


    /**
     * 数据所在的位置
     */
    private int position;

    private float offset;

    /**
     * 坐标点是否被选中
     */
    private boolean isSelect = false;

    public LineData() {

    }

    public LineData(float x, float y) {
        point.set(x, y);
    }


    public void setXY(float x, float y) {
        point.set(x, y);
    }


    public float getPopValue() {
        return popValue;
    }

    public void setPopValue(float popValue) {
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

    public float getX() {
        return point.x - offset;
    }

    public float getY() {
        return point.y;
    }

    public int getLineNum(){
        return LineNum;
    }

    public void setLineNum(int lineNum) {
        LineNum = lineNum;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
