package com.scriptedpapers.pickadate;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by mahes on 29/8/15.
 */
public class TopLineView  extends View {

    private Paint linePaint = new Paint();
    private Paint testPaint = new Paint();

    int DEFAULT_INDICATOR_WIDTH = 2;

    Context context;

    float leftEdgeLineStartX;
    float leftEdgeLineStartY;

    float leftEdgeLineEndX;
    float leftEdgeLineEndY;

    float rightEdgeLineStartX;
    float rightEdgeLineStartY;

    float rightEdgeLineEndX;
    float rightEdgeLineEndY;

    float middleHoriEdgeLineStartX;
    float middleHoriEdgeLineStartY;

    float middleHoriEdgeLineEndX;
    float middleHoriEdgeLineEndY;

    float middleVertEdgeLineStartX;
    float middleVertEdgeLineStartY;

    float middleVertEdgeLineEndX;
    float middleVertEdgeLineEndY;

    float topEdgeLineStartX;
    float topEdgeLineStartY;

    float topEdgeLineEndX;
    float topEdgeLineEndY;

    float bottomEdgeLineStartX;
    float bottomEdgeLineStartY;

    float bottomEdgeLineEndX;
    float bottomEdgeLineEndY;

    float leftEdgeLineStartXVar;
    float leftEdgeLineStartYVar;

    float leftEdgeLineEndXVar;
    float leftEdgeLineEndYVar;

    float rightEdgeLineStartXVar;
    float rightEdgeLineStartYVar;

    float rightEdgeLineEndXVar;
    float rightEdgeLineEndYVar;

    float middleHoriEdgeLineStartXVar;
    float middleHoriEdgeLineStartYVar;

    float middleHoriEdgeLineEndXVar;
    float middleHoriEdgeLineEndYVar;

    float middleVertEdgeLineStartXVar;
    float middleVertEdgeLineStartYVar;

    float middleVertEdgeLineEndXVar;
    float middleVertEdgeLineEndYVar;

    float topEdgeLineStartXVar;
    float topEdgeLineStartYVar;

    float topEdgeLineEndXVar;
    float topEdgeLineEndYVar;

    float bottomEdgeLineStartXVar;
    float bottomEdgeLineStartYVar;

    float bottomEdgeLineEndXVar;
    float bottomEdgeLineEndYVar;

    float dayBarWidth;
    float singleColumnWidth;

    float leftMostEdgeStartX;
    float leftMostEdgeStartY;

    float leftMostEdgeEndX;
    float leftMostEdgeEndY;

    boolean initStrechValue;

    float horizontalExpandStartX;
    float horizontalExpandStartY;

    float horizontalExpandEndX;
    float horizontalExpandEndY;

    ObjectAnimator verticalAnimator;
    ObjectAnimator verticalAnimator1;
    ObjectAnimator verticalAnimator2;
    ObjectAnimator verticalAnimator3;
    ObjectAnimator verticalAnimator4;
    ObjectAnimator verticalAnimator5;
    ObjectAnimator verticalAnimator6;
    ObjectAnimator verticalAnimator7;

    int xValue;
    int xValue1;
    int xValue2;
    int xValue3;
    int xValue4;
    int xValue5;
    int xValue6;
    int xValue7;

    ObjectAnimator horizontalAnimator;
    ObjectAnimator horizontalAnimator1;
    ObjectAnimator horizontalAnimator2;
    ObjectAnimator horizontalAnimator3;
    ObjectAnimator horizontalAnimator4;
    ObjectAnimator horizontalAnimator5;
    ObjectAnimator horizontalAnimator6;
    ObjectAnimator horizontalAnimator7;

    int yValue;
    int yValue1;
    int yValue2;
    int yValue3;
    int yValue4;
    int yValue5;
    int yValue6;
    int yValue7;

    public TopLineView(Context context)
    {
        super(context);
        init(context);
    }
    public TopLineView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    public TopLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    void init(Context context) {

        this.context = context;

        linePaint.setColor(Color.BLACK);

        linePaint.setAntiAlias(true);

        linePaint.setStyle(Paint.Style.STROKE);

        linePaint.setStrokeWidth(DEFAULT_INDICATOR_WIDTH);

        initStrechValue = false;

        testPaint.setColor(Color.RED);

        testPaint.setAntiAlias(true);

        testPaint.setStyle(Paint.Style.STROKE);

        testPaint.setStrokeWidth(DEFAULT_INDICATOR_WIDTH);


    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {

        super.onSizeChanged(width, height, oldWidth, oldHeight);

        float margin = context.getResources().getDimension(R.dimen.default_margin);
        float boxLength = context.getResources().getDimension(R.dimen.box_length);

        rightEdgeLineStartX = width - margin;
        rightEdgeLineStartY = margin;

        rightEdgeLineEndX = rightEdgeLineStartX;
        rightEdgeLineEndY = rightEdgeLineStartY + boxLength;

        rightEdgeLineStartXVar = rightEdgeLineStartX;
        rightEdgeLineStartYVar = rightEdgeLineStartY;

        rightEdgeLineEndXVar = rightEdgeLineEndX;
        rightEdgeLineEndYVar = rightEdgeLineEndY;

        leftEdgeLineStartX = rightEdgeLineStartX - boxLength;
        leftEdgeLineStartY = margin;

        leftEdgeLineEndX = leftEdgeLineStartX;
        leftEdgeLineEndY = leftEdgeLineStartY + boxLength;

        leftEdgeLineStartXVar = leftEdgeLineStartX;
        leftEdgeLineStartYVar = leftEdgeLineStartY;

        leftEdgeLineEndXVar = leftEdgeLineEndX;
        leftEdgeLineEndYVar = leftEdgeLineEndY;

        topEdgeLineStartX = rightEdgeLineStartX - boxLength;
        topEdgeLineStartY = margin;

        topEdgeLineEndX = width - margin;
        topEdgeLineEndY = margin;

        topEdgeLineStartXVar = topEdgeLineStartX;
        topEdgeLineStartYVar = topEdgeLineStartY;

        topEdgeLineEndXVar = topEdgeLineEndX;
        topEdgeLineEndYVar = topEdgeLineEndY;

        bottomEdgeLineStartX = leftEdgeLineStartX;
        bottomEdgeLineStartY = leftEdgeLineStartY + boxLength;

        bottomEdgeLineEndX = rightEdgeLineStartX;
        bottomEdgeLineEndY = rightEdgeLineStartY + boxLength;

        bottomEdgeLineStartXVar = bottomEdgeLineStartX;
        bottomEdgeLineStartYVar = bottomEdgeLineStartY;

        bottomEdgeLineEndXVar = bottomEdgeLineEndX;
        bottomEdgeLineEndYVar = bottomEdgeLineEndY;

        middleHoriEdgeLineStartX = topEdgeLineStartX;
        middleHoriEdgeLineStartY = topEdgeLineStartY + (boxLength/2);

        middleHoriEdgeLineEndX = topEdgeLineEndX;
        middleHoriEdgeLineEndY = topEdgeLineEndY + (boxLength/2);

        middleHoriEdgeLineStartXVar = middleHoriEdgeLineStartX;
        middleHoriEdgeLineStartYVar = middleHoriEdgeLineStartY;

        middleHoriEdgeLineEndXVar = middleHoriEdgeLineEndX;
        middleHoriEdgeLineEndYVar = middleHoriEdgeLineEndY;



        middleVertEdgeLineStartX = rightEdgeLineStartX - (boxLength/2);
        middleVertEdgeLineStartY = rightEdgeLineStartY;

        middleVertEdgeLineEndX = rightEdgeLineEndX - (boxLength/2);
        middleVertEdgeLineEndY = rightEdgeLineEndY ;

        middleVertEdgeLineStartXVar = middleVertEdgeLineStartX;
        middleVertEdgeLineStartYVar = middleVertEdgeLineStartY;

        middleVertEdgeLineEndXVar = middleVertEdgeLineEndX;
        middleVertEdgeLineEndYVar = middleVertEdgeLineEndY;



        xValue = 0;

        invalidate();
    }

    public void setVariableProperty(float dayBarWidth, GridView weekdayGridView) {

        if(!initStrechValue || dayBarWidth == 0) {

            float margin = context.getResources().getDimension(R.dimen.default_margin);
            float titleHeight = context.getResources().getDimension(R.dimen.calendar_title_height);

            this.dayBarWidth = dayBarWidth;
            singleColumnWidth = (dayBarWidth - (2F * margin)) / 7;

            singleColumnWidth = singleColumnWidth - (DEFAULT_INDICATOR_WIDTH/2);

            weekdayGridView.getLayoutParams().height = (int) singleColumnWidth;
            ((WeekdayArrayAdapter)weekdayGridView.getAdapter()).setHeight((int) singleColumnWidth);

            leftMostEdgeStartX = margin;
            leftMostEdgeStartY = titleHeight;

            leftMostEdgeEndX = margin;;
            leftMostEdgeEndY = leftMostEdgeStartY  + (7 * singleColumnWidth);


            horizontalExpandStartX = leftMostEdgeStartX;
            horizontalExpandStartY = leftMostEdgeStartY;

            horizontalExpandEndX = horizontalExpandStartX + (7 * singleColumnWidth);
            horizontalExpandEndY = horizontalExpandStartY;
            initStrechValue = true;
        }
    }

    public float getSingleColumnWidth() {
        return singleColumnWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Three Left Edges Vertical
        canvas.drawLine(leftEdgeLineStartX - ((leftEdgeLineStartX - leftMostEdgeStartX) * xValue / 100),
                leftEdgeLineStartY - ((leftEdgeLineStartY - leftMostEdgeStartY) * xValue / 100),
                leftEdgeLineEndX - ((leftEdgeLineEndX - leftMostEdgeEndX) * xValue / 100),
                leftEdgeLineEndY - ((leftEdgeLineEndY - leftMostEdgeEndY) * xValue / 100),
                linePaint);


        canvas.drawLine(leftEdgeLineStartX - ((leftEdgeLineStartX - leftMostEdgeStartX - (1 * singleColumnWidth)) * xValue1 / 100),
                leftEdgeLineStartY - ((leftEdgeLineStartY - leftMostEdgeStartY) * xValue1 / 100),
                leftEdgeLineEndX - ((leftEdgeLineEndX - leftMostEdgeEndX - (1 * singleColumnWidth)) * xValue1 / 100),
                leftEdgeLineEndY - ((leftEdgeLineEndY - leftMostEdgeEndY) * xValue1 / 100),
                linePaint);


        canvas.drawLine(leftEdgeLineStartX - ((leftEdgeLineStartX - leftMostEdgeStartX - (2 * singleColumnWidth)) * xValue2 / 100),
                leftEdgeLineStartY - ((leftEdgeLineStartY - leftMostEdgeStartY) * xValue2 / 100),
                leftEdgeLineEndX - ((leftEdgeLineEndX - leftMostEdgeEndX - (2 * singleColumnWidth)) * xValue2 / 100),
                leftEdgeLineEndY - ((leftEdgeLineEndY - leftMostEdgeEndY) * xValue2 / 100),
                linePaint);

        // Two Middle Edges Vertical
        canvas.drawLine(middleVertEdgeLineStartX - ((middleVertEdgeLineStartX - leftMostEdgeStartX - (3 * singleColumnWidth)) * xValue3 / 100),
                middleVertEdgeLineStartY - ((middleVertEdgeLineStartY - leftMostEdgeStartY) * xValue3 / 100),
                middleVertEdgeLineEndX - ((middleVertEdgeLineEndX - leftMostEdgeEndX - (3 * singleColumnWidth)) * xValue3 / 100),
                middleVertEdgeLineEndY - ((middleVertEdgeLineEndY - leftMostEdgeEndY) * xValue3 / 100),
                linePaint);

        canvas.drawLine(middleVertEdgeLineStartX - ((middleVertEdgeLineStartX - leftMostEdgeStartX - (4 * singleColumnWidth)) * xValue4 / 100),
                middleVertEdgeLineStartY - ((middleVertEdgeLineStartY - leftMostEdgeStartY) * xValue4 / 100),
                middleVertEdgeLineEndX - ((middleVertEdgeLineEndX - leftMostEdgeEndX - (4 * singleColumnWidth)) * xValue4 / 100),
                middleVertEdgeLineEndY - ((middleVertEdgeLineEndY - leftMostEdgeEndY) * xValue4 / 100),
                linePaint);

        // Three Right Edges Vertical
        canvas.drawLine(rightEdgeLineStartX - ((rightEdgeLineStartX - leftMostEdgeStartX - (5 * singleColumnWidth)) * xValue5 / 100),
                rightEdgeLineStartY - ((rightEdgeLineStartY - leftMostEdgeStartY) * xValue5 / 100),
                rightEdgeLineEndX - ((rightEdgeLineEndX - leftMostEdgeEndX - (5 * singleColumnWidth)) * xValue5 / 100),
                rightEdgeLineEndY - ((rightEdgeLineEndY - leftMostEdgeEndY) * xValue5 / 100),
                linePaint);

        canvas.drawLine(rightEdgeLineStartX - ((rightEdgeLineStartX - leftMostEdgeStartX - (6 * singleColumnWidth)) * xValue6 / 100),
                rightEdgeLineStartY - ((rightEdgeLineStartY - leftMostEdgeStartY) * xValue6 / 100),
                rightEdgeLineEndX - ((rightEdgeLineEndX - leftMostEdgeEndX - (6 * singleColumnWidth)) * xValue6 / 100),
                rightEdgeLineEndY - ((rightEdgeLineEndY - leftMostEdgeEndY) * xValue6 / 100),
                linePaint);

        canvas.drawLine(rightEdgeLineStartX - ((rightEdgeLineStartX - leftMostEdgeStartX - (7 * singleColumnWidth)) * xValue7 / 100),
                rightEdgeLineStartY - ((rightEdgeLineStartY - leftMostEdgeStartY) * xValue7 / 100),
                rightEdgeLineEndX - ((rightEdgeLineEndX - leftMostEdgeEndX - (7 * singleColumnWidth)) * xValue7 / 100),
                rightEdgeLineEndY - ((rightEdgeLineEndY - leftMostEdgeEndY) * xValue7 / 100),
                linePaint);

        // Top 3 Horizontal lines
        canvas.drawLine(topEdgeLineStartX - ((topEdgeLineStartX - horizontalExpandStartX) * xValue / 100),
                topEdgeLineStartY - ((topEdgeLineStartY - horizontalExpandStartY -  (0 * singleColumnWidth)) * xValue / 100),
                topEdgeLineEndX - ((topEdgeLineEndX - horizontalExpandEndX) * xValue / 100),
                topEdgeLineEndY - ((topEdgeLineEndY - horizontalExpandEndY -  (0 * singleColumnWidth)) * xValue / 100),
                linePaint);

        canvas.drawLine(topEdgeLineStartX - ((topEdgeLineStartX - horizontalExpandStartX) * xValue1 / 100),
                topEdgeLineStartY - ((topEdgeLineStartY - horizontalExpandStartY -  (1 * singleColumnWidth)) * xValue1 / 100),
                topEdgeLineEndX - ((topEdgeLineEndX - horizontalExpandEndX) * xValue1 / 100),
                topEdgeLineEndY - ((topEdgeLineEndY - horizontalExpandEndY -  (1 * singleColumnWidth)) * xValue1 / 100),
                linePaint);

        canvas.drawLine(topEdgeLineStartX - ((topEdgeLineStartX - horizontalExpandStartX) * xValue2 / 100),
                topEdgeLineStartY - ((topEdgeLineStartY - horizontalExpandStartY -  (2 * singleColumnWidth)) * xValue2 / 100),
                topEdgeLineEndX - ((topEdgeLineEndX - horizontalExpandEndX) * xValue2 / 100),
                topEdgeLineEndY - ((topEdgeLineEndY - horizontalExpandEndY -  (2 * singleColumnWidth)) * xValue2 / 100),
                linePaint);

        // Middle 2 Horizontal lines

        canvas.drawLine(middleHoriEdgeLineStartX - ((middleHoriEdgeLineStartX - horizontalExpandStartX) * xValue3 / 100),
                middleHoriEdgeLineStartY - ((middleHoriEdgeLineStartY - horizontalExpandStartY -  (3 * singleColumnWidth)) * xValue3 / 100),
                middleHoriEdgeLineEndX - ((middleHoriEdgeLineEndX - horizontalExpandEndX) * xValue3 / 100),
                middleHoriEdgeLineEndY - ((middleHoriEdgeLineEndY - horizontalExpandEndY -  (3 * singleColumnWidth)) * xValue3 / 100),
                linePaint);

        canvas.drawLine(middleHoriEdgeLineStartX - ((middleHoriEdgeLineStartX - horizontalExpandStartX) * xValue4 / 100),
                middleHoriEdgeLineStartY - ((middleHoriEdgeLineStartY - horizontalExpandStartY -  (4 * singleColumnWidth)) * xValue4 / 100),
                middleHoriEdgeLineEndX - ((middleHoriEdgeLineEndX - horizontalExpandEndX) * xValue4 / 100),
                middleHoriEdgeLineEndY - ((middleHoriEdgeLineEndY - horizontalExpandEndY -  (4 * singleColumnWidth)) * xValue4 / 100),
                linePaint);

        // Bottom 3 Horizontal Lines
        canvas.drawLine(bottomEdgeLineStartX - ((bottomEdgeLineStartX - horizontalExpandStartX) * xValue5 / 100),
                bottomEdgeLineStartY - ((bottomEdgeLineStartY - horizontalExpandStartY -  (5 * singleColumnWidth)) * xValue5 / 100),
                bottomEdgeLineEndX - ((bottomEdgeLineEndX - horizontalExpandEndX) * xValue5 / 100),
                bottomEdgeLineEndY - ((bottomEdgeLineEndY - horizontalExpandEndY -  (5 * singleColumnWidth)) * xValue5 / 100),
                linePaint);

        canvas.drawLine(bottomEdgeLineStartX - ((bottomEdgeLineStartX - horizontalExpandStartX) * xValue6 / 100),
                bottomEdgeLineStartY - ((bottomEdgeLineStartY - horizontalExpandStartY -  (6 * singleColumnWidth)) * xValue6 / 100),
                bottomEdgeLineEndX - ((bottomEdgeLineEndX - horizontalExpandEndX) * xValue6 / 100),
                bottomEdgeLineEndY - ((bottomEdgeLineEndY - horizontalExpandEndY -  (6 * singleColumnWidth)) * xValue6 / 100),
                linePaint);

        canvas.drawLine(bottomEdgeLineStartX - ((bottomEdgeLineStartX - horizontalExpandStartX) * xValue7 / 100),
                bottomEdgeLineStartY - ((bottomEdgeLineStartY - horizontalExpandStartY -  (7 * singleColumnWidth)) * xValue7 / 100),
                bottomEdgeLineEndX - ((bottomEdgeLineEndX - horizontalExpandEndX) * xValue7 / 100),
                bottomEdgeLineEndY - ((bottomEdgeLineEndY - horizontalExpandEndY -  (7 * singleColumnWidth)) * xValue7 / 100),
                linePaint);






    }

    public void moveToEdge() {

        verticalAnimator = ObjectAnimator.ofInt(this, "xValue", 0, 100);
        verticalAnimator.setStartDelay(50 + 500);
        verticalAnimator.setDuration(500);
        verticalAnimator.start();

        verticalAnimator1 = ObjectAnimator.ofInt(this, "xValue1", 0, 100);
        verticalAnimator1.setStartDelay(100 + 500);
        verticalAnimator1.setDuration(500);
        verticalAnimator1.start();

        verticalAnimator2 = ObjectAnimator.ofInt(this, "xValue2", 0, 100);
        verticalAnimator2.setStartDelay(150 + 500);
        verticalAnimator2.setDuration(500);
        verticalAnimator2.start();

        verticalAnimator3 = ObjectAnimator.ofInt(this, "xValue3", 0, 100);
        verticalAnimator3.setStartDelay(200 + 500);
        verticalAnimator3.setDuration(500);
        verticalAnimator3.start();

        verticalAnimator4 = ObjectAnimator.ofInt(this, "xValue4", 0, 100);
        verticalAnimator4.setStartDelay(250 + 500);
        verticalAnimator4.setDuration(500);
        verticalAnimator4.start();

        verticalAnimator5 = ObjectAnimator.ofInt(this, "xValue5", 0, 100);
        verticalAnimator5.setStartDelay(300 + 500);
        verticalAnimator5.setDuration(500);
        verticalAnimator5.start();

        verticalAnimator6 = ObjectAnimator.ofInt(this, "xValue6", 0, 100);
        verticalAnimator6.setStartDelay(350 + 500);
        verticalAnimator6.setDuration(500);
        verticalAnimator6.start();

        verticalAnimator7 = ObjectAnimator.ofInt(this, "xValue7", 0, 100);
        verticalAnimator7.setStartDelay(400 + 500);
        verticalAnimator7.setDuration(500);
        verticalAnimator7.start();

        horizontalAnimator = ObjectAnimator.ofInt(this, "yValue", 0, 100);
        horizontalAnimator.setStartDelay(50);
        horizontalAnimator.setDuration(500);
        horizontalAnimator.start();

        horizontalAnimator1 = ObjectAnimator.ofInt(this, "yValue1", 0, 100);
        horizontalAnimator1.setStartDelay(100);
        horizontalAnimator1.setDuration(500);
        horizontalAnimator1.start();

        horizontalAnimator2 = ObjectAnimator.ofInt(this, "yValue2", 0, 100);
        horizontalAnimator2.setStartDelay(150);
        horizontalAnimator2.setDuration(500);
        horizontalAnimator2.start();

        horizontalAnimator3 = ObjectAnimator.ofInt(this, "yValue3", 0, 100);
        horizontalAnimator3.setStartDelay(200);
        horizontalAnimator3.setDuration(500);
        horizontalAnimator3.start();

        horizontalAnimator4 = ObjectAnimator.ofInt(this, "yValue4", 0, 100);
        horizontalAnimator4.setStartDelay(250);
        horizontalAnimator4.setDuration(500);
        horizontalAnimator4.start();

        horizontalAnimator5 = ObjectAnimator.ofInt(this, "yValue5", 0, 100);
        horizontalAnimator5.setStartDelay(300);
        horizontalAnimator5.setDuration(500);
        horizontalAnimator5.start();

        horizontalAnimator6 = ObjectAnimator.ofInt(this, "yValue6", 0, 100);
        horizontalAnimator6.setStartDelay(350);
        horizontalAnimator6.setDuration(500);
        horizontalAnimator6.start();

        horizontalAnimator7 = ObjectAnimator.ofInt(this, "yValue7", 0, 100);
        horizontalAnimator7.setStartDelay(400);
        horizontalAnimator7.setDuration(500);
        horizontalAnimator7.start();
    }

    public void moveToBack() {

        verticalAnimator = ObjectAnimator.ofInt(this, "xValue", 100, 0);
        verticalAnimator.setStartDelay(400);
        verticalAnimator.setDuration(500);
        verticalAnimator.start();

        verticalAnimator1 = ObjectAnimator.ofInt(this, "xValue1", 100, 0);
        verticalAnimator1.setStartDelay(350);
        verticalAnimator1.setDuration(500);
        verticalAnimator1.start();

        verticalAnimator2 = ObjectAnimator.ofInt(this, "xValue2", 100, 0);
        verticalAnimator2.setStartDelay(300);
        verticalAnimator2.setDuration(500);
        verticalAnimator2.start();

        verticalAnimator3 = ObjectAnimator.ofInt(this, "xValue3", 100, 0);
        verticalAnimator3.setStartDelay(250);
        verticalAnimator3.setDuration(500);
        verticalAnimator3.start();

        verticalAnimator4 = ObjectAnimator.ofInt(this, "xValue4", 100, 0);
        verticalAnimator4.setStartDelay(200);
        verticalAnimator4.setDuration(500);
        verticalAnimator4.start();

        verticalAnimator5 = ObjectAnimator.ofInt(this, "xValue5", 100, 0);
        verticalAnimator5.setStartDelay(150);
        verticalAnimator5.setDuration(500);
        verticalAnimator5.start();

        verticalAnimator6 = ObjectAnimator.ofInt(this, "xValue6", 100, 0);
        verticalAnimator6.setStartDelay(100);
        verticalAnimator6.setDuration(500);
        verticalAnimator6.start();

        verticalAnimator7 = ObjectAnimator.ofInt(this, "xValue7", 100, 0);
        verticalAnimator7.setStartDelay(50);
        verticalAnimator7.setDuration(500);
        verticalAnimator7.start();

        horizontalAnimator = ObjectAnimator.ofInt(this, "yValue", 100, 0);
        horizontalAnimator.setStartDelay(400);
        horizontalAnimator.setDuration(500);
        horizontalAnimator.start();

        horizontalAnimator1 = ObjectAnimator.ofInt(this, "yValue1", 100, 0);
        horizontalAnimator1.setStartDelay(350);
        horizontalAnimator1.setDuration(500);
        horizontalAnimator1.start();

        horizontalAnimator2 = ObjectAnimator.ofInt(this, "yValue2", 100, 0);
        horizontalAnimator2.setStartDelay(300);
        horizontalAnimator2.setDuration(500);
        horizontalAnimator2.start();

        horizontalAnimator3 = ObjectAnimator.ofInt(this, "yValue3", 100, 0);
        horizontalAnimator3.setStartDelay(250);
        horizontalAnimator3.setDuration(500);
        horizontalAnimator3.start();

        horizontalAnimator4 = ObjectAnimator.ofInt(this, "yValue4", 100, 0);
        horizontalAnimator4.setStartDelay(200);
        horizontalAnimator4.setDuration(500);
        horizontalAnimator4.start();

        horizontalAnimator5 = ObjectAnimator.ofInt(this, "yValue5", 100, 0);
        horizontalAnimator5.setStartDelay(150);
        horizontalAnimator5.setDuration(500);
        horizontalAnimator5.start();

        horizontalAnimator6 = ObjectAnimator.ofInt(this, "yValue6", 100, 0);
        horizontalAnimator6.setStartDelay(100);
        horizontalAnimator6.setDuration(500);
        horizontalAnimator6.start();

        horizontalAnimator7 = ObjectAnimator.ofInt(this, "yValue7", 100, 0);
        horizontalAnimator7.setStartDelay(50);
        horizontalAnimator7.setDuration(500);
        horizontalAnimator7.start();

    }

    public void setXValue(int xValue) {
        this.xValue = xValue;
        invalidate();
    }

    public void setXValue1(int xValue1) {
        this.xValue1 = xValue1;
        invalidate();
    }

    public void setXValue2(int xValue2) {
        this.xValue2 = xValue2;
        invalidate();
    }

    public void setXValue3(int xValue3) {
        this.xValue3 = xValue3;
        invalidate();
    }

    public void setXValue4(int xValue4) {
        this.xValue4 = xValue4;
        invalidate();
    }

    public void setXValue5(int xValue5) {
        this.xValue5 = xValue5;
        invalidate();
    }

    public void setXValue6(int xValue6) {
        this.xValue6 = xValue6;
        invalidate();
    }

    public void setXValue7(int xValue7) {
        this.xValue7 = xValue7;
        invalidate();
    }

    public void setYValue(int yValue) {
        this.yValue = yValue;
        invalidate();
    }

    public void setYValue1(int yValue1) {
        this.yValue1 = yValue1;
        invalidate();
    }

    public void setYValue2(int yValue2) {
        this.yValue2 = yValue2;
        invalidate();
    }

    public void setYValue3(int yValue3) {
        this.yValue3 = yValue3;
        invalidate();
    }

    public void setYValue4(int yValue4) {
        this.yValue4 = yValue4;
        invalidate();
    }

    public void setYValue5(int yValue5) {
        this.yValue5 = yValue5;
        invalidate();
    }

    public void setYValue6(int yValue6) {
        this.yValue6 = yValue6;
        invalidate();
    }

    public void setYValue7(int yValue7) {
        this.yValue7 = yValue7;
        invalidate();
    }

}
