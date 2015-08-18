package com.winwang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by wiwa on 8/17/15.
 */
public class DonutsVisualization extends View{

    static final String TAG = "Donuts Visualization";
    private float innerRadius = 60;
    private float outerRadius = 100;

    private int width;
    private int height;
    private int centerx;
    private int centery;
    private int radius;

    Paint paintText;
    Paint paintArcBorder;
    Paint paintArcFill;

    ArrayList<DonutsArc> mDonuts;


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


    public float pxToDp(float pix){
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pix, getResources()
                .getDisplayMetrics());
    }

    private void init(){

        innerRadius = pxToDp(innerRadius);
        outerRadius = pxToDp(outerRadius);

        paintText = new Paint();
        paintText.setTextSize(15);
        paintText.setColor(Color.WHITE);

        // used for arc filling
        paintArcFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArcFill.setStyle(Paint.Style.FILL);
        paintArcFill.setColor(Color.YELLOW);

        // used for arc border
        paintArcBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArcBorder.setStyle(Paint.Style.STROKE);
        paintArcBorder.setStrokeWidth(10);
        paintArcBorder.setColor(Color.GREEN);
        Log.i(TAG, "Donuts init()");

        mDonuts = new ArrayList<DonutsArc>();
//        addArc(new DonutsArc(centerx, centery, innerRadius, outerRadius, 10, 90, Color.RED,Color.BLUE));

        addArc(new DonutsArc(this, 10, 90, Color.RED,Color.BLUE));
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

        //testing
        //canvas.drawCircle(centerx, centery, 100, paintArcBorder);
        for(DonutsArc arc : mDonuts){
            arc.updatePosition(centerx, centery);
            arc.draw(canvas);
        }
        /*
        RectF myRect = new RectF(150, 150, 500, 500);
        canvas.drawArc(100, 100, 400, 400, 30, 140, true,
                paintArcBorder);
        canvas.drawArc(myRect, 0, 90, false,
                paintArcBorder);
        */
        //canvas.drawArc();
    }

    public void addArc(DonutsArc arc){
        mDonuts.add(arc);
    }

    public int getCenterX() {
        return centerx;
    }

    public int getCenterY() {
        return centery;
    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public float getOuterRadius() {
        return outerRadius;
    }
}
