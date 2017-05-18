package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Bond on 16.05.2017.
 */
public class DistanceTests {

  @Test
  public void testDistance() {
    Point p1 = new Point(2, 4);
    Point p2 = new Point(6, 7);
    Assert.assertEquals(p1.distance(p2), 5.0, "Ошибка!");

    Point p3 = new Point(0, 0);
    Point p4 = new Point(0, -1);
    Assert.assertEquals(p3.distance(p4), 1.0, "Ошибка!!!!");

    Point p5 = new Point(1, 1);
    Point p6 = new Point(-1, -1);
    Assert.assertEquals(p5.distance(p6), Math.sqrt(8), "Ошибка!!!!");

    Point p7 = new Point(0, 0);
    Point p8 = new Point(0, 0);
    Assert.assertEquals(p7.distance(p8), 0.0, "Ошибка!!!!");
  }
}
