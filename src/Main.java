import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {


    private static boolean isStriclyOver(Point p1, Point p2, Point p3){
        return p1.x*(p2.y-p3.y) + p2.x*(p3.y-p1.y) + p3.x*(p1.y - p2.y) > 0;
    }

    private static boolean isStriclyOver1(Point p1, Point p2, Point p3){
        return p1.x*p2.y - p1.x * p3.y + p2.x*p3.y - p2.x*p1.y + p3.x*p1.y - p3.x*p2.y > 0;
    }
    
    private static boolean isStriclyOver2(Point p1, Point p2, Point p3){
        return p1.x * p2.y+p2.x*p3.y+ p3.x*p1.y - p1.x * p3.y - p2.x*p1.y - p3.x*p2.y > 0;
    }

    private static void testStrictlyOver() {
        int n = 500;
        ArrayList<Point> points = new ArrayList<>();
        Point p0 = new Point(0,0);
        points.add(p0);
        Random r = new Random(0);
        for (int i = 1; i < n; i++) {
            float oldX = points.get(i-1).x;
            float newX = oldX + r.nextFloat();
            Point p = new Point(newX, newX*newX);
            points.add(p);
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!isStriclyOver2(points.get(i-2), points.get(i-1), points.get(i))){
                System.out.println(i);
                count++;
            }
        }
        System.out.println("count: " + count);
    }


    public static void main(String[] args) {
        //testStrictlyOver();

        System.out.println(INC_CH(getRandomSortedArrForConvexHull(100)));

    }



    public static List<Point> getRandomSortedArrForConvexHull(int n){
        ArrayList<Point> points = new ArrayList<>();
        Random r = new Random(0);

        for (int i = 1; i < n; i++) {
            Point p = new Point(100 * r.nextFloat(), 100 * r.nextFloat());
            points.add(p);
        }

        Collections.sort(points);

        return points;
    }



    public static List<Point> INC_CH(List<Point> points){
        List<Point> UH = new ArrayList<>();
        int s = 1;
        UH.add(points.get(0));
        UH.add(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            while (s >= 1 && isStriclyOver(UH.get(s-1), UH.get(s), points.get(i))){
                UH.remove(s);
                s--;
            }
            UH.add(points.get(i));
            s++;
        }


        List<Point> BH = new ArrayList<>();
        s = 1;
        BH.add(points.get(0));
        BH.add(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            while (s >= 1 && !isStriclyOver(BH.get(s-1), BH.get(s), points.get(i))){
                BH.remove(s);
                s--;
            }
            BH.add(points.get(i));
            s++;
        }

        Collections.reverse(BH);
        BH.remove(0);
        BH.remove(BH.size()-1);

        UH.addAll(BH);
        return UH;
    }
}

