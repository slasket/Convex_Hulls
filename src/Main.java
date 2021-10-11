import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.geom.Line2D;
import java.util.SortedMap;

public class Main {

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


    public static void compareAlgorithms(String alg1, String alg2, List<Point> points) {
        List<Point> alg1res = runAlgo(alg1,points);
        List<Point> alg2res = runAlgo(alg2,points);

        if (!alg1res.equals(alg2res)){
            System.out.println("NOTEQUAL: "+alg1+ ": "+ alg1res.size()+" " + alg2 + ": "+alg2res.size());
            System.out.println(alg1+" "+ alg1res);
            System.out.println(alg2+" "+ alg2res);
        }else {
            System.out.println(alg1 +", " + alg2 +" are equal.");
        }

    }


    public static void testMiniExample(){
        List<Point> miniarr = pointGeneration.randomPointsSquare(10);
        System.out.println(miniarr);
        System.out.println("gram:" +INC_CH(miniarr));
        System.out.println("chan:" + CH_CH(miniarr));
        System.out.println("gift:" + GIFT_CH(miniarr));

    }


    public static void main(String[] args) {
        //testStrictlyOver();
        //testAngleCalculation();
        //testDownwardsAnglePositive();
        //System.out.println(arrSquare);
        //testMiniExample();


        List<Point> arrXSqrd = pointGeneration.randomPointsXSqrd(10);
        List<Point> arrSquare = pointGeneration.randomPointsSquare(10);
        List<Point> arrCircle = pointGeneration.randomPointsCircle(10);

        compareAlgorithms("INC_CH", "CH_CH", arrSquare);
        compareAlgorithms("INC_CH", "CH_CH", arrCircle);
        compareAlgorithms("INC_CH", "CH_CH", arrXSqrd);

        compareAlgorithms("INC_CH", "GIFT_CH", arrSquare);
        compareAlgorithms("INC_CH", "GIFT_CH", arrCircle);
        compareAlgorithms("INC_CH", "GIFT_CH", arrXSqrd);


        //timeAlgo("INC_CH",arrXSqrd);
        //timeAlgo("INC_CH",arrSquare);
        //timeAlgo("INC_CH",arrCircle);
        //timeAlgo("CH_CH",arrXSqrd);
        //timeAlgo("CH_CH",arrSquare);
        //timeAlgo("CH_CH",arrCircle);
        //timeAlgo("GIFT_CH",arrXSqrd);
        //timeAlgo("GIFT_CH",arrSquare);
        //timeAlgo("GIFT_CH",arrCircle);

    }

    public static void timeAlgo(String algo, List<Point> points){
        long startTime = System.nanoTime();
        runAlgo(algo,points);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        System.out.println(algo+ " "+ points.size()+" "+ duration+"ms");

    }

    public static List<Point> runAlgo(String algo, List<Point> points){
        switch (algo) {
            case "INC_CH":
                return INC_CH(points);
            case "GIFT_CH":
                return GIFT_CH(points);
            case "CH_CH":
                return CH_CH(points);
            default:
                return new ArrayList<>();
        }
    }


    public static List<Point> INC_CH(List<Point> points){
        List<Point> UH = new ArrayList<>();
        int s = 1;
        UH.add(points.get(0));
        UH.add(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            while (s >= 1 && util.isStriclyOver(UH.get(s - 1), UH.get(s), points.get(i))){
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
            while (s >= 1 && !util.isStriclyOver(BH.get(s - 1), BH.get(s), points.get(i))){
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
                    float angle = util.angleBetween2Lines(rayLine, lineI);

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

    public static List<Point> CH_CH(List<Point> points){
        int n = points.size();
        for (int i = 1; i < (int) Math.log(n); i++) {
            ChansIdentifier result = hullsWithSize(points,(int) Math.pow(2,Math.pow(2,i)));

            if (result.getCondition().equals("success")){
                return result.getHull();
            }
        }
        return new ArrayList<Point>();
    }

    private static ChansIdentifier hullsWithSize(List<Point> points, int amountOfSubsets) {
        int n = points.size();
        //list of all points in the partial hulls
        List<Point> subsetHullsCombined = new ArrayList<>();
        int elementsInSubset = n/amountOfSubsets;
        for (int i = 1; i < amountOfSubsets; i++) {
            List<Point> subsetHull = INC_CH(points.subList((i-1)*elementsInSubset,i*elementsInSubset));
            subsetHullsCombined.addAll(subsetHull);
        }

        //get the remaining points that doesnt divide evenly
        //the remaining points are put into the last partition, this in worst case means that the last partition is almost 2x of all the others
        List<Point> subsetHull = INC_CH(points.subList(elementsInSubset*(amountOfSubsets-1)+1, n));
        subsetHullsCombined.addAll(subsetHull);


        List<Point> hull = GIFT_CH(subsetHullsCombined);
        if (hull.contains(points.get(0)) && hull.contains(points.get(n-1))){
            return new ChansIdentifier("success", hull);
        }

        return new ChansIdentifier("fail", new ArrayList<Point>());

    }

    public static class ChansIdentifier{
        public String condition;
        public List<Point> hull;

        public ChansIdentifier(String condition, List<Point> hull) {
            this.condition = condition;
            this.hull = hull;
        }

        public List<Point> getHull() {
            return hull;
        }

        public String getCondition() {
            return condition;
        }
    }

}

