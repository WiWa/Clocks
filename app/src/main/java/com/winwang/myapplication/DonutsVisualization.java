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

import com.google.common.base.Function;

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

        addArc(new DonutsArc(this, 10, 90, Color.RED, Color.BLUE));
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
    /*
        So it turns out this is pretty difficult,
        at least compared to how difficult I thought it should be.
        Since the clock has a set radius, I want to make each arc thinner or wider
        depending on how many arcs it overlaps with, like how Google Calendar does it.

        After a bit of thinking, I came up with this plan:
        For any given event, what is the largest number of overlaps it must handle simultaneously?
        To solve that problem, I realized that if I created a graph of all overlapping events,
        then the answer to that problem for any given event is the answer to:
        What is the size of largest fully-connected sub-graph that includes that event?
        // I use "fully-connected" to mean all points connect to all other points.
        So, make the overlap graph, which I will represent as a matrix because lazy,
        check if an events connected nodes are connected to each other,
        take the maximum of something and use it to calculate the width of the node's arc.
        Edit: Wow, how do you find a node's largest fully-connected subgraph efficiently?!
        Edit2: Okay turns out fully-coneccted has a name: complete. And turns out a complete
        subgraph's set of vertices is called a clique. And this is the clique problem? Okay then.
        Edit3: Got it. Possibly. Take an event's row in the overlap matrix. Only consider the
        columns where the row has a 1 (this is the list of the event's overlaps). The relevant
        sub-matrix we're interested in is where the top row is the event's overlap row (should be
         all 1's). The maximum clique the event is in, and thus the number of simultaneous
         overlaps the event has, is the number of rows filled with 1's (no 0's).

        Then comes the problem of different-sized arcs overlapping.
        I'll fix it like this: If an arc's list of other arcs that overlap it includes
        an arc whose number of maximum overlaps (I call this an arc's division) surpasses
        the length of the list of overlaps, then ignore (remove) that arc.
        This should result in the other arcs in that list having a small division,
        thus growing larger, and producing the visual effect of being laid under the smaller arc.

        Then comes the problem of "what if there is a hole in between arcs where another arc can
        fit?
        My answer is to check an arc's list of overlaps, and find a missing ranking.
        For example, if there were no holes, the innermost arc would be ranked 1, the second
        ranked 2, etc. If there was a gap then the rankings wouldn't be consecutive,
        so we just need to fill it in.

        Sometimes the hole is larger than just one rank, but that can be taken care of by making
        rankings a range. Let's worry about that later. Whoo, what a turn of events haha.
     */
    private void calculateDivisionRankings(){
        /*
        ArrayList<ArrayList<DonutsArc>> matrix_of_arcs = new ArrayList<ArrayList<DonutsArc>>();
        for(DonutsArc arc1 : mDonuts){
            ArrayList<DonutsArc> column_of_arcs = new ArrayList<DonutsArc>();
            for(DonutsArc arc2 : mDonuts){
                column_of_arcs.add(arc2);
            }
            matrix_of_arcs.add(column_of_arcs);
        }*/
        int[][] overlap_matrix = new int[mDonuts.size() + 1][mDonuts.size() + 1];
        // initialize edges of matrix to be indices of arcs
        // c for column, r for row
        for(int c = 0; c < mDonuts.size() + 1; c++){
            // Note that overlap_matrix[0][0] is null
            if(c == 0){
                for(int r = 1; r < mDonuts.size() + 1; r++){
                    overlap_matrix[0][r] = r;
                }
            }
            else {
                overlap_matrix[c][0] = c;
            }
        }
        // Populate matrix with 1 = overlap, 0 = no overlap
        for(int c = 1; c < mDonuts.size() + 1; c++){
            // Just need one half of the matrix because symmetry
            DonutsArc a = mDonuts.get(overlap_matrix[c][0]);
            for(int r = 1; r < c + 1; r++){
                DonutsArc b = mDonuts.get(overlap_matrix[0][r]);
                if(arcsOverlap(a, b)){
                    overlap_matrix[c][r] = 1;
                }
                else{
                    overlap_matrix[c][r] = 0;
                }
            }
        }
        // for each arc, find that arc's largest "fully connected graph"
        // Oh my god how do you do it.
        for(int r = 1; r < mDonuts.size() + 1; r++){
            DonutsArc a = mDonuts.get(overlap_matrix[0][r]);
            int maximum_overlaps = 0;
            for(int c = r; c < mDonuts.size() + 1; c++){
                boolean overlap = overlap_matrix[c][r] > 0;
                if(overlap){

                }
            }

        }
    }

    public boolean arcsOverlap(DonutsArc a, DonutsArc b){
        return b.getStartDegree() < a.getEndDegree() && b.getEndDegree() > a.getStartDegree();
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
