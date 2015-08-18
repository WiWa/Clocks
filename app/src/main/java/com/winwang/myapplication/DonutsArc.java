package com.winwang.myapplication;

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

    public DonutsArc(){

    }

    public DonutsArc(double centerx, double centery, double innerRadius, double outerRadius, double
            startDegree, double endDegree,
                     int fillColor, int borderColor){

        this.centerx = centerx;
        this.centery = centery;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.startDegree = startDegree;
        this.endDegree = endDegree;
        this.fillColor = fillColor;
        this.borderColor = borderColor;

        this.innerRect = calculateRect(innerRadius);
        this.outerRect = calculateRect(outerRadius);

    }

    public RectF calculateRect(double radius){
        RectF rect = new RectF();

        double left = centerx - radius;
        double right = centerx + radius;
        double top = centery - radius;
        double bottom = centery + radius;

        rect.set((float) left,(float) top,(float) right,(float) bottom);

        return rect;
    }

    public float getStartAngle(){
        return (float) this.startDegree;
    }

    public float getSweepAngle(){
        return (float) (this.endDegree - this.startDegree);
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
