package com.github.nickardson.gui.util;

public class Point {
    public static Point ZERO = new Point(0, 0);
    public static Point POSX = new Point(1, 0);
    public static Point POSY = new Point(0, 1);
    public static Point NEGX = new Point(-1, 0);
    public static Point NEGY = new Point(0, -1);
    public static Point UNIT = new Point(1, 1);

    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point p2) {
        return new Point(this.x + p2.x, this.y + p2.y);
    }

    public Point subtract(Point p2) {
        return new Point(this.x - p2.x, this.y - p2.y);
    }

    public Point multiply(Point p2) {
        return new Point(this.x * p2.x, this.y * p2.y);
    }

    public Point multiply(int d) {
        return new Point(this.x * d, this.y * d);
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }

    public double distance2(Point p2) {
        return (x - p2.x) * (x - p2.x) + (y - p2.y) * (y - p2.y);
    }

    public double distance(Point p2) {
        return this.subtract(p2).magnitude();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
