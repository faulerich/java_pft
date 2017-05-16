package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {

    //проверка работоспособности функции вычисления расстояния distance
    Point p1 = new Point(2, 4);
    Point p2 = new Point(6, 7);

    System.out.println("Расстояние между точками с координатами (" + p1.x + " ; " + p1.y + ") и (" + p2.x + " ; " + p2.y + ") равно " + distance(p1, p2));

    //проверка работоспособности метода, описанного в классе Point
    System.out.println("Расстояние между точками с координатами (" + p1.x + " ; " + p1.y + ") и (" + p2.x + " ; " + p2.y + ") равно " + p1.distance(p2));
  }

  public static double distance(Point p1, Point p2) {

    return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
  }

}