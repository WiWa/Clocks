package com.winwang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wiwa on 8/17/15.
 */
public class DonutsVisualization extends View{

    final String TAG = "Donuts Visualization";

    int width;
    int height;
    int centerx;
    int centery;
    int radius;

    Paint paintText;
    Paint paintArcBorder;
    Paint paintArcFill;


    public DonutsVisualization(Context context){
        super(context);
        init();
    }

    public DonutsVisualization(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public DonutsVisualization(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        paintText = new Paint();
        paintText.setTextSize(15);
        paintText.setColor(Color.WHITE);

        // used for arc filling
        paintArcFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArcFill.setStyle(Paint.Style.FILL);

        // used for arc border
        paintArcBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArcBorder.setStyle(Paint.Style.STROKE);
        paintArcBorder.setStrokeWidth(10);
        paintArcBorder.setColor(Color.WHITE);
        Log.i(TAG, "Donuts init()");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

/*
        if (width == 0) {
            width = getWidth();
            height = getHeight();
        } else {
            // do not get size again
            setMeasuredDimension(getMeasuredWidth(), height);
            return;
        }
*/
        width = getMeasuredWidth();
        height = getMeasuredHeight();


        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        centerx = width / 2;
        centery = height / 2;

        radius = Math.min(width, height);

        canvas.drawCircle(centerx, centery, 100, paintArcBorder); // testing
    }
}
