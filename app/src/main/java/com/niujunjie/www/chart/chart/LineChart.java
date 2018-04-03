package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: niujunjie
 * @date: 2018/4/2
 * @email: niujunjie@miao.cn
 * @function:
 */
public class LineChart extends BaseChart {


    /**
     * 滚动当前偏移量
     */
    private float offset;

    /**
     * 滚动偏移量的边界
     */
    private float maxOffset;

    /**
     * 判断左/右方向，当在边缘就不触发fling，以优化性能
     */
    float orientationX;

    private VelocityTracker velocityTracker;
    /**
     * 手指/fling的上次位置
     */
    private float lastX;

    private boolean isStopScroll = false;
    /**
     * fling最大速度
     */
    private int maxVelocity;

    private Scroller scroller;


    private int start = 0;
    private int end = 0;
    protected Map<Integer, List<LineData>> mChartDatas = new HashMap<>();
    private int mWidth;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void init() {
        super.init();
        scroller = new Scroller(getContext());
        maxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
    }

    @Override
    public void setData() {
        super.setData();
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        end = params.getCanVisiableNum() + params.getLeftCache();
        cacLineData(start, end);
    }

    public void cacLineData(int start, int end) {
        mXAxis.calcValues(mChartArea, start, end);

        /**
         * 可滑动的最大距离
         */
        if (mXAxis.getLabels().size() <= 7) {
            maxOffset = scroller.getFinalX();
        } else {
            //TODO 屏幕内可见数据个数目前8
            maxOffset = -(mXAxis.getLabels().size() - params.getCanVisiableNum()) * Math.abs(mXAxis.getSpace());
        }


        Map<Integer, List<Float>> data = params.getData();
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            List<Float> tempData = data.get(i);
            List<LineData> lineDatas = new ArrayList<>();
            end = tempData.size() > end ? end : tempData.size();

            for (int j = start; j < end; j++) {
                LineData lineData = new LineData();
                float currnetValue = tempData.get(j);
                if (currnetValue < params.getYMinValue()) {
                    currnetValue = params.getYMinValue();
                }

                if (currnetValue > params.getYMaxValue()) {
                    currnetValue = params.getYMaxValue();
                }

                float x = mXAxis.getValues().get(j - start);
                float y = 0;

                if (mYAxis.isNormal()) {
                    float rate = currnetValue / params.getYMaxValue();
                    y = mYAxis.getStartY() - mYAxis.getHeight() * rate;
                } else {
                    //计算不规则y值
                    List<String> lables = mYAxis.getLabels();
                    for (int var = 0; var < lables.size() - 1; var++) {
                        float temp1 = Float.parseFloat(lables.get(var));
                        float temp2 = Float.parseFloat(lables.get(var + 1));
                        if (temp1 <= currnetValue && currnetValue <= temp2) {
                            float rate = (currnetValue - temp1) / (temp2 - temp1);
                            y = mYAxis.getStartY() - (mYAxis.getSpace() * var + rate * mYAxis.getSpace());
                            break;
                        }
                    }
                }

                lineData.setXY(x, y);
                lineDatas.add(lineData);
            }
            if (start != end) {
                mChartDatas.put(i, lineDatas);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mChartDatas.size(); i++) {
            drawSmoothLine(canvas, mChartDatas.get(i),i);
            drawDot(canvas, mChartDatas.get(i), i);
        }
    }

    private void drawDot(Canvas canvas, List<LineData> lineData, int lineNum) {
        for (LineData data : lineData) {
            float x = data.getX();
            float y = data.getY();
            canvas.drawCircle(x - offset, y, ChartUtils.dip2px(getContext(), 5), params.getBigCirPaint());
            canvas.drawCircle(x - offset, y, ChartUtils.dip2px(getContext(), 3), params.getSmallCirPaint(lineNum));
        }
    }

    private void drawSmoothLine(Canvas canvas, List<LineData> lineData, int lineNum) {
        LineData startp;
        LineData endp;
        int tempSize = lineData.size();
        Path p = new Path();
        for (int i = 0; i < tempSize - 1; i++) {
            p.reset();
            startp = lineData.get(i);
            endp = lineData.get(i + 1);
            float wt = (startp.getX() - offset + endp.getX() - offset) / 2;
            p.moveTo(startp.getX() - offset, startp.getY());
            p.cubicTo(wt, startp.getY(), wt, endp.getY(), endp.getX() - offset, endp.getY());
            canvas.drawPath(p, params.getLinePaint(lineNum));
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isStopScroll = false;
                lastX = event.getX();
                initOrResetVelocityTracker();
                scroller.abortAnimation();
                velocityTracker.addMovement(event);
                super.onTouchEvent(event);
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                lastX = event.getX(0);
                break;
            case MotionEvent.ACTION_MOVE:
                orientationX = lastX - event.getX();
                if (Math.abs(orientationX) < 10f) {
                    break;
                }

                onScroll(orientationX);
                lastX = event.getX();
                velocityTracker.addMovement(event);

                break;
            case MotionEvent.ACTION_POINTER_UP:
                int minID = event.getPointerId(0);
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if (event.getPointerId(i) <= minID) {
                        minID = event.getPointerId(i);
                    }
                }
                if (event.getPointerId(event.getActionIndex()) == minID) {
                    minID = event.getPointerId(event.getActionIndex() + 1);
                }
                lastX = event.getX(event.findPointerIndex(minID));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1500, maxVelocity);
                int initialVelocity = (int) velocityTracker.getXVelocity();
                velocityTracker.clear();
                if (initialVelocity == 0) {
                    //选中处理
                }
                if (!isArriveAtLeftEdge() && !isArriveAtRightEdge()) {
                    scroller.fling((int) event.getX(), (int) event.getY(), -initialVelocity / 2,
                            0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                    invalidate();
                }
                lastX = event.getX();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否滑动到了左边缘，注意，并非指可视区域的边缘，下同
     *
     * @return
     */
    private boolean isArriveAtLeftEdge() {
        return Math.abs(offset) == Math.abs(maxOffset) && orientationX < 0;
    }

    /**
     * 是否滑动到了右边缘
     *
     * @return
     */
    private boolean isArriveAtRightEdge() {
        return offset == 0 && orientationX > 0;
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            onScroll(scroller.getCurrX() - lastX);
            lastX = scroller.getCurrX();
            postInvalidate();
        }
    }

    /**
     * 滑动方法，同时检测边缘条件
     *
     * @param deltaX
     */
    int changeValue = 0;
    int lastChaveValue = -1;

    private boolean isMoveToLeft = false;

    private void onScroll(float deltaX) {
        if (deltaX > 0) {
            //图表向左划
            isMoveToLeft = true;
        } else {
            //图表向右划
            isMoveToLeft = false;
        }
        offset += deltaX;
        offset = offset > 0 ? 0 : (Math.abs(offset) > Math.abs(maxOffset)) ? maxOffset : offset;

        changeValue = (int) (Math.abs(offset / mXAxis.getSpace()));

        int st = start + changeValue - 1;
        st = st < 0 ? 0 : st;
        cacLineData(st, end + changeValue);

        //图表停止滑动
        if (Math.abs(deltaX) == 1 || deltaX == 0) {
            isStopScroll = true;
        }
        mXAxis.setOffset(offset);
        invalidate();
    }

    private void initOrResetVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }
}
