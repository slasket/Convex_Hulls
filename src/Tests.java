import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void testStrictlyOver() {
        int n = 500;
        ArrayList<Point> points = pointGeneration.randomPointsXSqrd(n);

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!util.isStriclyOver2(points.get(i - 2), points.get(i - 1), points.get(i))){
                System.out.println(i);
                count++;
            }
        }
        System.out.println("count: " + count);
    }

    public static void testAngleCalculation() {
        Line2D Line1 = new Line2D.Float(1.0f,1.0f,1.0f,Float.POSITIVE_INFINITY);
        Line2D Line2 = new Line2D.Float(1.0f,1.0f,2.0f,2.0f);
        if (util.angleBetween2Lines(Line1, Line2) ==45.0f){
            System.out.println("grøn linje, knap en bajs");
        }else{
            System.out.println("ikke grøn linje");
        }
    }

    public static void testDownwardsAnglePositive() {
        Line2D Line1 = new Line2D.Float(0f,0f,0f,-1f);
        Line2D Line2 = new Line2D.Float(0f,0f,-1f,0f);
        if (util.angleBetween2Lines(Line1, Line2) ==90.0f){
            System.out.println("grøn linje2, knap en ny bajs");
        }else {
            System.out.println("ikke grøn linje nummer 2: dropud.nu");
        }
    }

    public static void testMiniExample(){
        List<Point> miniarr = pointGeneration.randomPointsSquare(10);
        System.out.println(miniarr);
        System.out.println("gram:" + Inc_ch.INC_CH(miniarr));
        //System.out.println("chan:" + CH_CH(miniarr));
        System.out.println("gift:" + Gift_ch.GIFT_CH(miniarr));

    }

    public static void threePointComparison(){
        Point p1 = new Point(0.373608f, 99.32321f);
        Point p2 = new Point(66.6946f,  94.77459f);
        Point p3 = new Point(83.47799f,  93.61499f);
        //should be strictly over or true
        System.out.println(util.isStriclyOver(p1,p3,p2));

        //ray comparison
        Line2D rayLine = new Line2D.Float(p1.x,p1.y,p2.x,p2.y);
        Line2D candidateLine = new Line2D.Float(p1.x,p1.y,p3.x,p3.y);
        System.out.println(util.angleBetween2Lines(rayLine,candidateLine));
    }
}
