package com.niujunjie.www.chart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.Map;

/**
 * @author: niujunjie
 * @date: 2018/3/28
 * @email: niujunjie@miao.cn
 * @function: 基础图表，绘制背景，x轴，y轴
 */
public class BaseChart extends View {
    public BaseChart(Context context) {
        super(context);
        init();
    }


    public BaseChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setData() {
        init();
        // invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    /**
     * 有效区域
     */
    protected RectF mValidArea;

    /**
     * 标题区域
     */
    protected RectF mTitleArea;

    /**
     * 标题的高度
     */
    protected int mTitleHeight = 40;

    /**
     * y区域宽度
     */
    protected int mYWidth = 40;

    /**
     * x区域高度
     */
    protected int mXHeight = 40;
    /**
     * x轴区域
     */
    protected RectF mXArea;

    /**
     * y轴区域
     */
    protected RectF mYArea;

    /**
     * 图表绘制区域
     */
    protected RectF mChartArea;


    protected YAxis mYAxis;
    protected XAxis mXAxis;


    Params params;

    public void init() {

        if (params == null) {
            params = new Params(getContext());
        }

        mXAxis = params.getxAxis();
        mYAxis = params.getyAxis();
        /**
         * 分配图表的区域，成标题区，x轴区域，y轴区域，图表区域
         */
        calcChartArea();
        // mXAxis.calcValues(mChartArea, 0, mXAxis.getOnePageCount()+params.getLeftCache());
        mYAxis.createValues(mYArea);
    }

    private void calcChartArea() {
        if (mYAxis.getMaxValue() != 0) {
            mYWidth = (int) ChartUtils.getValueWidth(mYAxis.getPaint(), mYAxis.getMaxValue() + "") + mYAxis.getPadding() * 2;
        } else {
            mYWidth = ChartUtils.dip2px(getContext(), 40);
        }
        /**
         * 有效区域
         */
        float left = getPaddingLeft();
        float top = getPaddingTop();
        float right = getMeasuredWidth() - getPaddingRight();
        float bottom = getHeight() - getPaddingBottom();
        mValidArea = new RectF(left, top, right, bottom);

        /**
         * 标题区，高度可设置
         */
        float titleLeft = mValidArea.left;
        float titleTop = mValidArea.top;
        float titleRight = mValidArea.right;
        float titleBottom = mValidArea.top + ChartUtils.dip2px(getContext(), mTitleHeight);
        mTitleArea = new RectF(titleLeft, titleTop, titleRight, titleBottom);

        /**
         * x轴区域
         */
        float xLeft = mValidArea.left;
        float xBottom = mValidArea.bottom;
        float xTop = xBottom - ChartUtils.dip2px(getContext(), mXHeight);
        float xRight = mValidArea.right;
        mXArea = new RectF(xLeft, xTop, xRight, xBottom);

        /**
         * y轴区域
         */
        float yLeft = mValidArea.left;
        float yTop = mTitleArea.bottom;
        float yRight = mValidArea.left + mYWidth;
        float yBottom = mXArea.top;
        mYArea = new RectF(yLeft, yTop, yRight, yBottom);

        /**
         * chart区域
         */
        float cLeft = mYArea.right;
        float cTop = mTitleArea.bottom;
        float cRight = mValidArea.right;
        float cBottom = mXArea.top;
        mChartArea = new RectF(cLeft, cTop, cRight, cBottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (ChartUtils.isDebug) {
            test(canvas);
        }
        //canvas.clipRect(mValidArea);
        drawLeftValue(canvas);
        drawRightValue(canvas);
        //drawXValue(canvas);
        drawBgLine(canvas);
        //drawYValue(canvas);
    }

    private void drawRightValue(Canvas canvas) {

        String[] value = new String[]{"血糖", "血压", "ssws"};
        String[] color = new String[]{"#987de6", "#535353", "#535353"};

        Paint[] paints = new Paint[value.length];
        if (value != null && value.length > 0) {
            TextPaint textPaint = (TextPaint) ChartUtils.initPaint(new TextPaint());
            textPaint.setTextSize(ChartUtils.dip2px(getContext(), 13));
            textPaint.setTextAlign(Paint.Align.RIGHT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                textPaint.setShadowLayer(5, 3, 3, 0x33535353);
            }
            float offet = 0;
            for (int i = 0; i < value.length; i++) {
                paints[i] = ChartUtils.initPaint(new Paint());

                paints[i].setColor(Color.parseColor(color[i]));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    paints[i].setShadowLayer(5, 3, 3, Color.parseColor(color[i]));
                }

                float textX = mTitleArea.right -
                        ChartUtils.dip2px(getContext(), 20) - offet;

                float textY = mTitleArea.top + mTitleArea.height() / 2
                        + ChartUtils.getTextHeight(textPaint) / 2;

                float reactWidth = ChartUtils.dip2px(getContext(), 15);
                float reactHeight = ChartUtils.getTextHeight(textPaint);

                float reactX = textX - ChartUtils.getValueWidth(textPaint, value[i])
                        - reactWidth / 2 - ChartUtils.getValueWidth(textPaint, "  ");

                float reactY = textY - ChartUtils.getTextHeight(textPaint) / 2;
                canvas.drawText(value[i], textX, textY, textPaint);

                canvas.drawRect(reactX + reactWidth / 2,
                        reactY + reactHeight / 2,
                        reactX - reactWidth / 2,
                        reactY - reactHeight / 2, paints[i]);

                offet = offet + reactWidth / 2 + ChartUtils.dip2px(getContext(), 20)
                        + ChartUtils.getValueWidth(textPaint, "  ")
                        + ChartUtils.getValueWidth(textPaint, value[i]);

            }


        }
    }

    private void drawLeftValue(Canvas canvas) {
        TextPaint paint = (TextPaint) ChartUtils.initPaint(new TextPaint());
        paint.setTextSize(ChartUtils.dip2px(getContext(), 13));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            paint.setShadowLayer(5, 3, 3, 0x33535353);
        }
        float x = mYArea.left + mYArea.width() / 2;
        float y = mTitleArea.top + mTitleArea.height() / 2 + ChartUtils.getTextHeight(paint) / 2;
        canvas.drawText("图表", x, y, paint);
    }


    private Paint linPaint;

    protected void drawXValue(Canvas canvas, Map selectData) {
        List<String> lables = mXAxis.getLabels();
        float y = mXArea.top + mXArea.height() / 2 + ChartUtils.getTextHeight(mXAxis.getPaint()) / 2;
        for (int i = mXAxis.getStart(); i < mXAxis.getEnd(); i++) {

            String lable;

            if (lables.size() > 0) {
                lable = lables.get(i);
            } else {
                lable = i + "";
            }

            TextPaint textPaint = mXAxis.getPaint();
            if (selectData.size() > 0) {
                LineData data = (LineData) selectData.get(0);
                if (data.getPosition() == i) {
                    textPaint.setColor(Color.parseColor("#535353"));
                } else {
                    textPaint.setColor(Color.parseColor("#9e9e9e"));
                }
            }
            canvas.drawText(lable, mXAxis.getValues().get(i - mXAxis.getStart()) - mXAxis.getOffset(), y, textPaint);
        }
    }

    private void drawBgLine(Canvas canvas) {
        linPaint = ChartUtils.initPaint(new Paint());
        linPaint.setColor(Color.parseColor("#e8e8e8"));
        linPaint.setStrokeWidth(ChartUtils.dip2px(getContext(), 2));
        for (int i = mXAxis.getStart(); i < mXAxis.getEnd(); i++) {
            /**
             * 使用lineValue的原因和局部刷新有关
             * 保证背景线不随线段移动
             */
            if (i < mXAxis.getLineValues().size()) {
                canvas.drawLine(mXAxis.getLineValues().get(i), mChartArea.bottom,
                        mXAxis.getLineValues().get(i), mChartArea.top, linPaint);
            }
        }
    }

    protected void drawYValue(Canvas canvas) {
        List<Float> values = mYAxis.getValues();
        List<String> lables = mYAxis.getLabels();
        float x = mYArea.left + mYAxis.getPadding();
        for (int i = 0; i < values.size(); i++) {
            String lable;
            if (lables.size() > 0) {
                lable = lables.get(i);
            } else {
                lable = i + "";
            }
            canvas.drawText(lable, x,
                    values.get(i), mYAxis.getPaint());
        }
    }

    private void test(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setAlpha(100);
        canvas.drawRect(mValidArea, p);
        p.setColor(Color.GREEN);
        p.setAlpha(100);
        canvas.drawRect(mTitleArea, p);
        p.setColor(Color.RED);
        p.setAlpha(100);
        canvas.drawRect(mYArea, p);
        p.setColor(Color.GRAY);
        p.setAlpha(100);
        canvas.drawRect(mXArea, p);
        p.setColor(Color.YELLOW);
        p.setAlpha(100);
        canvas.drawRect(mChartArea, p);
    }


    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
