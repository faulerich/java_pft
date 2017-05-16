package ru.stqa.pft.sandbox;

/**
 * Created by Bond on 15.05.2017.
 */
public class Point {
  public double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }


  public double distance(Point p2) {

    return Math.sqrt(Math.pow((this.x - p2.x), 2) + Math.pow((this.y - p2.y), 2));
  }

}
