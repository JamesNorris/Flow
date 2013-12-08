package com.github.jamesnorris.flow.selection;

public class Wave2D {
    private double xIntercept, yIntercept, length, height;
    private boolean /* xFlip, already handled if xIntercept < 0 */yFlip;

    public Wave2D(double originX, double originY, double length, double height, boolean flip) {
        // doesn't make sure args are not equal to Double.NaN
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than 0");
        }
        xIntercept = originX;
        yIntercept = originY;
        this.length = length;
        this.height = height;
        yFlip = flip;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public boolean isReflectedOverY() {
        return yFlip;
    }

    public double getYAt(double x) {
        if (xIntercept < 0 ? x > xIntercept || x < xIntercept - length : x < xIntercept || x > xIntercept + length) {
            return Double.NaN;
        }
        return (yFlip ? -1 : 1) * (height * (x - xIntercept) / x + (xIntercept < 0 ? -1 : 1) * yIntercept);
    }
}
