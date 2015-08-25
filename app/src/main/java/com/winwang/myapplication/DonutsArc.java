package com.winwang.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Toast;

/**
 * Created by wiwa on 8/18/15.
 */
public class DonutsArc {

    public static final int PAD_DEGREES = 0;
    public static final int PAD_RADIUS = 1;
    public static final int PAD_DEGREES_AND_RADIUS = 2;

    private double centerx;
    private double centery;
    private double innerRadius;
    private double outerRadius;
    private double startDegree;
    private double endDegree;
    private int fillColor;
    private int arcColor;

    private double paddingDegrees = 1;
    private double paddingRadiusPercent = 0.02;

    private RectF innerRect;
    private RectF outerRect;

    private Paint paintArc;

    private int division;
    private int ranking;

    public DonutsArc(){

    }

    public DonutsArc(DonutsVisualization donutsVisualization, double
            startDegree, double endDegree,
                     int fillColor, int arcColor){

        this(donutsVisualization.getCenterX(), donutsVisualization.getCenterY(),
                donutsVisualization.getInnerRadius(), donutsVisualization.getOuterRadius(),
                startDegree, endDegree, fillColor,arcColor);

    }

    public DonutsArc(double centerx, double centery, double innerRadius, double outerRadius, double
            startDegree, double endDegree,
                     int fillColor, int arcColor){

        this.centerx = centerx;
        this.centery = centery;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startDegree = startDegree - 90; // Start from top.
        this.endDegree = Math.max(this.endDegree, 360); // Maximum of 360
        this.endDegree = endDegree - 90;
        this.fillColor = fillColor;
        this.arcColor = arcColor;

        this.paintArc = new Paint();
        paintArc.setStyle(Paint.Style.FILL);
        paintArc.setStrokeWidth(10);
        paintArc.setColor(arcColor);

        applyPadding(PAD_DEGREES_AND_RADIUS);

        this.innerRect = calculateRect(new RectF(), innerRadius);
        this.outerRect = calculateRect(new RectF(), outerRadius);

    }
    
    public void draw(Canvas canvas){
/*
        canvas.drawArc(getInnerRect(), getStartAngle(), getSweepAngle(), false,
                paintArc);

        canvas.drawArc(getOuterRect(), getStartAngle(), geapplyPadtSweepAngle(), false,
                paintArc);
        canvas.drawLine(getStartX(innerRadius), getStartY(innerRadius),
                getStartX(outerRadius), getStartY(outerRadius), paintArc);
        canvas.drawLine(getEndX(innerRadius), getEndY(innerRadius),
                getEndX(outerRadius), getEndY(outerRadius), paintArc);
  */
        Path path = new Path();
        path.moveTo(getStartX(innerRadius), getStartY(innerRadius));
        path.lineTo(getStartX(outerRadius), getStartY(outerRadius));
        path.arcTo(getOuterRect(), getStartAngle(), getSweepAngle());
        path.lineTo(getEndX(innerRadius), getEndY(innerRadius));
        path.arcTo(getInnerRect(), getEndAngle(), -getSweepAngle());
        ///// Are you serious do I really have to tell you everything??
        path.lineTo(getStartX(outerRadius), getStartY(outerRadius));
        //Fill
        canvas.drawPath(path, paintArc);
        //Border
        int darkerColor = darken(arcColor);
        paintArc.setColor(darkerColor);
        paintArc.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paintArc);
        //Cleanup
        paintArc.setColor(arcColor);
        paintArc.setStyle(Paint.Style.STROKE);
    }

    public int darken(int color){
        // hsv means hue saturation value, aka hue saturation brightness
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component (brightness)
        return Color.HSVToColor(hsv);
    }

    public RectF calculateRect(RectF rect, double radius){
        double left = centerx - radius;
        double right = centerx + radius;
        double top = centery - radius;
        double bottom = centery + radius;

        rect.set((float) left, (float) top, (float) right, (float) bottom);

        return rect;
    }

    public float getStartAngle(){
        return (float) this.startDegree;
    }
    public float getEndAngle(){
        return (float) this.endDegree;
    }

    public float getSweepAngle(){
        return (float) (this.endDegree - this.startDegree);
    }

    public float getStartX(double radius){
        return (float) (this.centerx + radius * Math.cos(Math.toRadians(startDegree)));
    }
    public float getStartY(double radius){
        return (float) (this.centery + radius * Math.sin(Math.toRadians(startDegree)) );
    }

    public float getEndX(double radius){
        return (float) (this.centerx + radius * Math.cos(Math.toRadians(endDegree)) );
    }
    public float getEndY(double radius){
        return (float) (this.centery + radius * Math.sin(Math.toRadians(endDegree)) );
    }


    public void setCenterX(double centerx) {
        this.centerx = centerx;
    }

    public void setCenterY(double centery) {
        this.centery = centery;
    }

    public void updatePosition(double centerx, double centery){
        if(this.centerx != centerx || this.centery != centery){
            setCenterX(centerx);
            setCenterY(centery);
            this.innerRect = calculateRect(this.innerRect, innerRadius);
            this.outerRect = calculateRect(this.outerRect, outerRadius);
        }
    }
    public void updateRadii(double innerRadius, double outerRadius) throws Exception{
        if(outerRadius > innerRadius){
            throw new Exception("Outer Radius can't be smaller than Inner Radius. Duh.");
        }
        if(this.innerRadius != innerRadius){
            setInnerRadius(innerRadius);
            this.innerRect = calculateRect(this.innerRect, innerRadius);
        }
        if(this.outerRadius != outerRadius){
            setOuterRadius(outerRadius);
            this.outerRect = calculateRect(this.outerRect, outerRadius);
        }
        applyPadding(PAD_RADIUS);
    }

    private void applyPadding(int mode){
        if(mode == PAD_DEGREES){
            this.startDegree += this.paddingDegrees;
            this.endDegree -= this.paddingDegrees;
        }
        else if (mode == PAD_RADIUS){
            this.innerRadius += this.innerRadius * paddingRadiusPercent;
            this.outerRadius -= this.outerRadius * paddingRadiusPercent;
        }
        else if (mode == PAD_DEGREES_AND_RADIUS){
            this.startDegree += this.paddingDegrees;
            this.endDegree -= this.paddingDegrees;
            this.innerRadius += this.innerRadius * paddingRadiusPercent;
            this.outerRadius -= this.outerRadius * paddingRadiusPercent;
        }
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    public double getStartDegree() {
        return startDegree;
    }

    public void setStartDegree(double startDegree) {
        this.startDegree = startDegree;
    }

    public double getEndDegree() {
        return endDegree;
    }

    public void setEndDegree(double endDegree) {
        this.endDegree = endDegree;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getArcColor() {
        return arcColor;
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
    }

    public RectF getInnerRect() {
        return innerRect;
    }

    public RectF getOuterRect() {
        return outerRect;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
