package com.winwang.clocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import static com.winwang.clocks.DonutsArc.Ring.INNER;

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

    public enum Ring {
        INNER,
        OUTER
    }

    public static DonutsArc create(DonutsVisualization donutsVisualization, double
            startDegree, double endDegree,
                                   int fillColor, int arcColor, Ring ring) {
        double innerRadius = donutsVisualization.getInnerRadius();
        double outerRadius = donutsVisualization.getOuterRadius();
        double halfWidth = (outerRadius - innerRadius) / 2;
        if (ring == INNER) {
            outerRadius -= halfWidth * 1.15;
        } else {
            innerRadius += halfWidth * 1.15;
        }
        return new DonutsArc(donutsVisualization.getCenterX(), donutsVisualization.getCenterY(),
                innerRadius, outerRadius,
                startDegree, endDegree, fillColor, arcColor);
    }

    public DonutsArc(double centerx, double centery, double innerRadius, double outerRadius, double
            startDegree, double endDegree,
                     int fillColor, int arcColor) {

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

    public void draw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getStartX(innerRadius), getStartY(innerRadius));
        path.lineTo(getStartX(outerRadius), getStartY(outerRadius));
        path.arcTo(getOuterRect(), getStartAngle(), getSweepAngle());
        path.lineTo(getEndX(innerRadius), getEndY(innerRadius));
        path.arcTo(getInnerRect(), getEndAngle(), -getSweepAngle());
        ///// Are you serious do I really have to tell you everything??
        path.lineTo(getStartX(outerRadius), getStartY(outerRadius));
        //Fill
        paintArc.setAlpha(100);
        paintArc.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paintArc);
        //Border
        int darkerColor = darken(arcColor);
        paintArc.setColor(darkerColor);
        paintArc.setAlpha(255);
        paintArc.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paintArc);
        //Cleanup
        paintArc.setColor(arcColor);
    }

    public int darken(int color) {
        // hsv means hue saturation value, aka hue saturation brightness
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component (brightness)
        return Color.HSVToColor(hsv);
    }

    public RectF calculateRect(RectF rect, double radius) {
        double left = centerx - radius;
        double right = centerx + radius;
        double top = centery - radius;
        double bottom = centery + radius;

        rect.set((float) left, (float) top, (float) right, (float) bottom);
        return rect;
    }

    public float getStartAngle() {
        return (float) this.startDegree;
    }

    public float getEndAngle() {
        return (float) this.endDegree;
    }

    public float getSweepAngle() {
        return (float) (this.endDegree - this.startDegree);
    }

    public float getStartX(double radius) {
        return (float) (this.centerx + radius * Math.cos(Math.toRadians(startDegree)));
    }

    public float getStartY(double radius) {
        return (float) (this.centery + radius * Math.sin(Math.toRadians(startDegree)));
    }

    public float getEndX(double radius) {
        return (float) (this.centerx + radius * Math.cos(Math.toRadians(endDegree)));
    }

    public float getEndY(double radius) {
        return (float) (this.centery + radius * Math.sin(Math.toRadians(endDegree)));
    }


    public void setCenterX(double centerx) {
        this.centerx = centerx;
    }

    public void setCenterY(double centery) {
        this.centery = centery;
    }

    public void updatePosition(double centerx, double centery) {
        if (this.centerx != centerx || this.centery != centery) {
            setCenterX(centerx);
            setCenterY(centery);
            this.innerRect = calculateRect(this.innerRect, innerRadius);
            this.outerRect = calculateRect(this.outerRect, outerRadius);
        }
    }

    private void applyPadding(int mode) {
        if (mode == PAD_DEGREES) {
            this.startDegree += this.paddingDegrees;
            this.endDegree -= this.paddingDegrees;
        } else if (mode == PAD_RADIUS) {
            this.innerRadius += this.innerRadius * paddingRadiusPercent;
            this.outerRadius -= this.outerRadius * paddingRadiusPercent;
        } else if (mode == PAD_DEGREES_AND_RADIUS) {
            this.startDegree += this.paddingDegrees;
            this.endDegree -= this.paddingDegrees;
            this.innerRadius += this.innerRadius * paddingRadiusPercent;
            this.outerRadius -= this.outerRadius * paddingRadiusPercent;
        }
    }

    public RectF getInnerRect() {
        return innerRect;
    }

    public RectF getOuterRect() {
        return outerRect;
    }
}
