import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.geom.Line2D;

import static java.lang.Math.*;

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
        ArrayList<Point> points = randomPointsXSqrd(n);

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!isStriclyOver2(points.get(i-2), points.get(i-1), points.get(i))){
                System.out.println(i);
                count++;
            }
        }
        System.out.println("count: " + count);
    }

    private static void testAngleCalculation() {
        Line2D Line1 = new Line2D.Float(1.0f,1.0f,1.0f,Float.POSITIVE_INFINITY);
        Line2D Line2 = new Line2D.Float(1.0f,1.0f,2.0f,2.0f);
        if (angleBetween2Lines(Line1,Line2)==45.0f){
            System.out.println("grøn linje, knap en bajs");
        }else{
            System.out.println("ikke grøn linje");
        }
    }
//
    private static void testDownwardsAnglePositive() {
        Line2D Line1 = new Line2D.Float(0f,0f,0f,-1f);
        Line2D Line2 = new Line2D.Float(0f,0f,-1f,0f);
        if (angleBetween2Lines(Line1,Line2)==90.0f){
            System.out.println("grøn linje2, knap en ny bajs");
        }else {
            System.out.println("ikke grøn linje nummer 2: dropud.nu");
        }
    }



    public static void main(String[] args) {
        //testStrictlyOver();
        //testAngleCalculation();
        //testDownwardsAnglePositive();


        List<Point> arrXSqrd = randomPointsXSqrd(1000000);
        List<Point> arrSquare = randomPointsSquare(1000000);
        List<Point> arrCircle = randomPointsCircle(1000000);

        //System.out.println(arrSquare);

        timeAlgo("INC_CH",arrXSqrd);
        timeAlgo("INC_CH",arrSquare);
        timeAlgo("INC_CH",arrCircle);

        timeAlgo("GIFT_CH",arrXSqrd);
        timeAlgo("GIFT_CH",arrSquare);
        timeAlgo("GIFT_CH",arrCircle);

    }


    public static void timeAlgo(String algo, List<Point> points){
        long startTime = System.nanoTime();
        switch (algo){
            case "INC_CH":
                INC_CH(points);
                break;
            case "GIFT_CH":
                GIFT_CH(points);
                break;
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        System.out.println(algo+ " "+ points.size()+" "+ duration+"ms");

    }

    private static ArrayList<Point> randomPointsXSqrd(int n) {
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
        Collections.sort(points);
        return points;
    }


    public static List<Point> randomPointsSquare(int n){
        ArrayList<Point> points = new ArrayList<>();
        Random r = new Random(0);

        for (int i = 0; i < n; i++) {
            Point p = new Point(100 * r.nextFloat(), 100 * r.nextFloat());
            points.add(p);
        }
        Collections.sort(points);
        return points;
    }

    //implementation idea taken from stackoverflow comment: https://stackoverflow.com/questions/5837572/generate-a-random-point-within-a-circle-uniformly
    public static List<Point> randomPointsCircle(int n){
        ArrayList<Point> points = new ArrayList<>();
        Random r = new Random(0);

        for (int i = 0; i < n; i++) {
            float radius = 2*(float) PI*r.nextFloat();
            float u = r.nextFloat()+r.nextFloat();
            float num;
            if (u>1){
                num=2-u;
            }else{
                num=u;
            }
            Point p = new Point(num*(float) cos(radius),  (num*(float) sin(radius)));
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

    public static List<Point> GIFT_CH(List<Point> points){
        //init vals
        List<Point> CH = new ArrayList<>();
        CH.add(points.get(0));
        Point pivot = CH.get(0);
        int pivotIdx = 0;
        Point q1 = CH.get(0);
        Point rayEnd = new Point(pivot.x,Float.POSITIVE_INFINITY);
        Line2D rayLine = new Line2D.Float(pivot.x,pivot.y,rayEnd.x,rayEnd.y);

        //looping
        while (true){
            int candidateIdx = 0;
            Line2D candidateLine = null;
            float smallestAngleSeen = Float.POSITIVE_INFINITY;

            for (int i = 0; i < points.size(); i++) {
                if (i != pivotIdx){
                    Point ithPoint = points.get(i);
                    Line2D lineI = new Line2D.Float(pivot.x,pivot.y,ithPoint.x,ithPoint.y);
                    float angle = angleBetween2Lines(rayLine, lineI);

                    if (angle < smallestAngleSeen){
                        candidateIdx = i;
                        candidateLine = lineI;
                        smallestAngleSeen = angle;
                    }
                }
            }
            if (candidateIdx==0){
                break;
            }
            CH.add(points.get(candidateIdx));
            pivotIdx = candidateIdx;
            pivot = points.get(candidateIdx);
            rayLine = candidateLine;
        }

        return CH;
    }

    public static float angleBetween2Slopes(float slope1, float slope2 ){
        float angle = (float) (Math.atan((slope2-slope1)/(1-slope2*slope1)));
        boolean isDevil = angle<0;
        if (isDevil){
            System.out.println("angle is devil: "+ angle +" slope1: "+slope1+ " slope2: "+slope2);
        }
        return angle;
    }
    public static float angleBetween2Lines(Line2D line1, Line2D line2) {
        double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                line1.getX1() - line1.getX2());
        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                line2.getX1() - line2.getX2());
        float angle = (float) (abs(toDegrees(angle1) - toDegrees(angle2)));
        if(angle > 180) return 360-angle;
        return angle;
    }

    public static int indexOfSmallesElement(List<Float> list){
        int indexOfMin=0;
        float minVal=Float.POSITIVE_INFINITY;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < minVal){
                minVal= list.get(i);
                indexOfMin = i;
            }
        }
        return indexOfMin;
    }
}

