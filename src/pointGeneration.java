import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class pointGeneration {
    public static ArrayList<Point> randomPointsXSqrd(int n) {
        ArrayList<Point> points = new ArrayList<Point>();
        Point p0 = new Point(0, 0);
        points.add(p0);
        Random r = new Random(0);
        for (int i = 1; i < n; i++) {
            float oldX = points.get(i - 1).x;
            float newX = oldX + r.nextFloat();
            Point p = new Point(newX, newX * newX);
            points.add(p);
        }
        Collections.sort(points);
        return points;
    }

    public static List<Point> randomPointsSquare(int n) {
        ArrayList<Point> points = new ArrayList<Point>();
        Random r = new Random(0);

        for (int i = 0; i < n; i++) {
            Point p = new Point(100 * r.nextFloat(), 100 * r.nextFloat());
            points.add(p);
        }
        Collections.sort(points);
        return points;
    }//implementation idea taken from stackoverflow comment: https://stackoverflow.com/questions/5837572/generate-a-random-point-within-a-circle-uniformly

    public static List<Point> randomPointsCircle(int n) {
        ArrayList<Point> points = new ArrayList<Point>();
        Random r = new Random(0);

        for (int i = 0; i < n; i++) {
            float radius = 2 * (float) Math.PI * r.nextFloat();
            float u = r.nextFloat() + r.nextFloat();
            float num;
            if (u > 1) {
                num = 2 - u;
            } else {
                num = u;
            }
            Point p = new Point(num * (float) Math.cos(radius), (num * (float) Math.sin(radius)));
            points.add(p);
        }
        Collections.sort(points);
        return points;
    }
}