package com.winwang.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by wiwa on 8/18/15.
 */
public class DonutsArc {

    private double centerx;
    private double centery;
    private double innerRadius;
    private double outerRadius;
    private double startDegree;
    private double endDegree;
    private int fillColor;
    private int borderColor;

    private RectF innerRect;
    private RectF outerRect;

    private Paint paintArcBorder;

    public DonutsArc(){

    }

    public DonutsArc(DonutsVisualization donutsVisualization, double
            startDegree, double endDegree,
                     int fillColor, int borderColor){

        this(donutsVisualization.getCenterX(), donutsVisualization.getCenterY(),
                donutsVisualization.getInnerRadius(), donutsVisualization.getOuterRadius(),
                startDegree, endDegree, fillColor,borderColor);

    }

    public DonutsArc(double centerx, double centery, double innerRadius, double outerRadius, double
            startDegree, double endDegree,
                     int fillColor, int borderColor){

        this.centerx = centerx;
        this.centery = centery;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startDegree = startDegree - 90; // Start from top.
        this.endDegree = endDegree - 90;
        this.fillColor = fillColor;
        this.borderColor = borderColor;

        this.paintArcBorder = new Paint();
        paintArcBorder.setStyle(Paint.Style.STROKE);
        paintArcBorder.setStrokeWidth(10);
        paintArcBorder.setColor(borderColor);

        this.innerRect = calculateRect(new RectF(), innerRadius);
        this.outerRect = calculateRect(new RectF(), outerRadius);

    }
    
    public void draw(Canvas canvas){

        canvas.drawArc(getInnerRect(), getStartAngle(), getSweepAngle(), false,
                paintArcBorder);
        canvas.drawArc(getOuterRect(), getStartAngle(), getSweepAngle(), false,
                paintArcBorder);
        canvas.drawLine(getStartX(innerRadius), getStartY(innerRadius),
                getStartX(outerRadius), getStartY(outerRadius), paintArcBorder);
        canvas.drawLine(getEndX(innerRadius), getEndY(innerRadius),
                getEndX(outerRadius), getEndY(outerRadius), paintArcBorder);
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

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public RectF getInnerRect() {
        return innerRect;
    }

    public RectF getOuterRect() {
        return outerRect;
    }

}
