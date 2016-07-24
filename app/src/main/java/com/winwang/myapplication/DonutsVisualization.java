package com.winwang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by wiwa on 8/17/15.
 */
public class DonutsVisualization extends View {

    static final String TAG = "Donuts Visualization";
    private float innerRadius = 60;
    private float outerRadius = 100;

    private int width;
    private int height;
    private int centerx;
    private int centery;
    private int radius;

    ArrayList<DonutsArc> mDonuts;


    public DonutsVisualization(Context context) {
        super(context);
        init();
    }

    public DonutsVisualization(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DonutsVisualization(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        innerRadius = pxToDp(innerRadius);
        outerRadius = pxToDp(outerRadius);
        mDonuts = new ArrayList<>();
    }

    public float pxToDp(float pix) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pix, getResources()
                .getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        centerx = width / 2;
        centery = height / 2;
        radius = Math.min(width, height);

        for (DonutsArc arc : mDonuts) {
            arc.updatePosition(centerx, centery);
            arc.draw(canvas);
        }
    }

    public void addArc(@Nullable DonutsArc arc) {
        if (arc == null) {
            return;
        }
        mDonuts.add(arc);
    }

    public void clear() {
        mDonuts.clear();
        invalidate();
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
