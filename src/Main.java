import java.util.*;
import java.awt.geom.Line2D;

public class Main {

    private static int failCounter;

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

    //returns true if the algorithms outputted the same hull
    public static boolean compareAlgorithms(String alg1, String alg2, List<Point> points) {
        List<Point> alg1res = runAlgo(alg1,points);
        List<Point> alg2res = runAlgo(alg2,points);
        if (!util.compareLists(alg1res, alg2res)){
            System.out.println("NOTEQUAL: "+alg1+ ": "+ alg1res.size()+" " + alg2 + ": "+alg2res.size());
            System.out.println(alg1);
            System.out.println(alg1res);
            System.out.println(alg2);
            System.out.println(alg2res);
            return false;
        }else {
            System.out.println(alg1 +", " + alg2 +" are equal.");
        }
        return true;
    }


    public static void testMiniExample(){
        List<Point> miniarr = pointGeneration.randomPointsSquare(10);
        System.out.println(miniarr);
        System.out.println("gram:" + INC_CH(miniarr));
        //System.out.println("chan:" + CH_CH(miniarr));
        System.out.println("gift:" + GIFT_CH(miniarr));

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


    public static void main(String[] args) {
        //threePointComparison();
        //testStrictlyOver();
        //testAngleCalculation();
        //testDownwardsAnglePositive();
        //testMiniExample();

        //List<Point> arrXSqrd = pointGeneration.randomPointsXSqrd(100);
        //Random rand = new Random();
        //for(int i = 0; i < 1000; i++){
        //    Point p1 = arrXSqrd.get(rand.nextInt(100));
        //    Point p2 = arrXSqrd.get(rand.nextInt(100));
        //    Point p3 = arrXSqrd.get(rand.nextInt(100));
        //    float orientationValue1 = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);
        //    float orientationValue2 = p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y);
//
        //    if(orientationValue2 != orientationValue1 && Math.abs(orientationValue2 + orientationValue1) > 0.01){
        //        System.out.println("They are different");
        //        System.out.println(orientationValue2 + " : " + orientationValue1);
        //    }
//
        //}

        //List<Point> arrSquare = pointGeneration.randomPointsSquare(10);
        //List<Point> arrCircle = pointGeneration.randomPointsCircle(10);
        //compareAlgorithms("INC_CH", "CH_CH", arrSquare);
        //compareAlgorithms("INC_CH", "CH_CH", arrCircle);
        //compareAlgorithms("INC_CH", "CH_CH", arrXSqrd);
        //System.out.println("");
        //compareAlgorithms("INC_CH", "GIFT_CH", arrSquare);
        //compareAlgorithms("INC_CH", "GIFT_CH", arrCircle);
        //compareAlgorithms("INC_CH", "GIFT_CH", arrXSqrd);

        //testVariableInputSize("compare", "square",200);

        for(int i = 0; i < 50; i++){
            System.out.println("Test number: " + (i+1));
            testVariableInputSize("compare", "xsqrd", 100000);
            testVariableInputSize("compare", "square", 100000);
            testVariableInputSize("compare", "circle", 100000);

            //testVariableInputSize("time", "xsqrd", 100000);
            //testVariableInputSize("time", "square", 100000);
            //testVariableInputSize("time", "circle", 100000);

            //testVariableInputSize("compare", "circle", 100000);
        }
        System.out.println("Total fails = " + failCounter);
        //for (int i = 1; i < 25; i++) {
        //    //8 means we start from 256
        //    //testVariableInputSize("time","square",(int) Math.pow(2,i));
        //    testVariableInputSize("compare","square",(int) Math.pow(2,i));
        //    testVariableInputSize("compare","circle",(int) Math.pow(2,i));
        //    testVariableInputSize("compare","xsqrd",(int) Math.pow(2,i));
        //}

    }
    //option: compare or time, arrtype: xsqrd, square, circle, inputsize: number
    public static void testVariableInputSize(String option,String arrtype, int inputSize){
        List<Point> testInput = new ArrayList<>();
        System.out.println(option+ " on "+arrtype + " of size "+inputSize);
        switch (arrtype){
            case "xsqrd":
                testInput = pointGeneration.randomPointsXSqrd(inputSize);
                break;
            case "square":
                testInput = pointGeneration.randomPointsSquare(inputSize);
                break;
            case "circle":
                testInput = pointGeneration.randomPointsCircle(inputSize);
                break;

        }

        switch (option){
            case "compare":
                //compareAlgorithms("INC_CH", "CH_CH", testInput);
                if(!compareAlgorithms("INC_CH", "GIFT_CH", testInput)) failCounter++; //incrementing failCounter field if not same hull
                if(!compareAlgorithms("INC_CH", "CH_CH", testInput)) failCounter++;

                break;
            case "time":
                timeAlgo("INC_CH",testInput);

                timeAlgo("GIFT_CH",testInput);

                timeAlgo("CH_CH",testInput);

        }
        System.out.println("");

    }


    public static void timeAlgo(String algo, List<Point> points){
        long startTime = System.nanoTime();
        runAlgo(algo,points);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        System.out.println(algo+ " "+ duration+"ms");

    }

    public static List<Point> runAlgo(String algo, List<Point> points){
        switch (algo) {
            case "INC_CH":
                return INC_CH(points);
            case "GIFT_CH":
                return GIFT_CH(points);
            case "CH_CH":
                return CH_CHNEW(points);
            default:
                return new ArrayList<>();
        }
    }


    public static List<Point> INC_CH(List<Point> points){
        List<Point> UH = new ArrayList<>();

        //making UH
        for (int i = 0; i < points.size(); i++) {
            while(UH.size() >= 2 && util.determineOrientation(UH.get(UH.size()-2), UH.get(UH.size()-1), points.get(i))){
                UH.remove(UH.size() - 1);
            }
            UH.add(points.get(i));
        }

        //making LH
        //List<Point> LH = new ArrayList<>();
        //for (int i = points.size()-1; i >= 0; i--) {
        //    while(LH.size() >= 2 && util.determineOrientation(LH.get(LH.size()-2), LH.get(LH.size()-1), points.get(i))){
        //        LH.remove(LH.size()-1);
        //    }
        //    LH.add(points.get(i));
        //}
        //LH.remove(LH.size()-1); LH.remove(0);
        //UH.addAll(LH);

        return UH;
    }

    public static List<Point> GIFT_CH(List<Point> points){ //DENNE METODE ER BETYDELIG LANGSOMMERE END INC_CH
        List<Point> CH = new ArrayList<>();
        //int idOfLeftmost = util.findLeftmostPointId(points);
        //int idOfRightMost = util.findRightmostPointId(points);
        CH.add(points.get(0));

        int idOfLeftmost = 0;
        int idOfRightMost = points.size()-1;

        int idOfCurrentPoint = idOfLeftmost;
        Point rayEnd = new Point(points.get(idOfCurrentPoint).x, Integer.MAX_VALUE);
        Line2D line1 = new Line2D.Float(points.get(idOfCurrentPoint).x, points.get(idOfCurrentPoint).y, rayEnd.x, rayEnd.y);
        Line2D line2;
        Line2D bestLineSoFar = null;
        do{
            float bestSoFarAngle = Integer.MAX_VALUE;
            int bestPointIdxSoFar = Integer.MAX_VALUE;
            for (int i = 0; i < points.size(); i++) { //iterate over all points
                line2 = new Line2D.Float(points.get(idOfCurrentPoint).x, points.get(idOfCurrentPoint).y, points.get(i).x, points.get(i).y);
                float angle = util.angleBetween2Lines(line1, line2);

                if(angle < bestSoFarAngle && !CH.contains(points.get(i)) || points.get(i) == points.get(0)){ //minimize v
                    bestSoFarAngle = angle;
                    bestPointIdxSoFar = i;  // v
                    bestLineSoFar = line2;  // pv
                }
            }
            //System.out.println(bestPointIdxSoFar);
            if(CH.contains(points.get(bestPointIdxSoFar)) && bestPointIdxSoFar == idOfLeftmost) break;
            CH.add(points.get(bestPointIdxSoFar)); //add v to hull
            line1 = bestLineSoFar;  // set r to pv
            idOfCurrentPoint = bestPointIdxSoFar; // set p to v


        } while(idOfCurrentPoint != idOfRightMost);

        return CH;
    }

    public static List<Point> CH_CHSIMONERGAY(List<Point> points){

        int n = points.size();
        for (int i = 1; i <= util.log2(util.log2(n)); i++) {
            int amountOfSubsets = (int) Math.pow(2,Math.pow(2,i));

            //list of all points in the partial hulls
            List<Point> subsetHullsCombined = new ArrayList<>();
            int elementsInSubset = n/amountOfSubsets;
            for (int j = 1; j < amountOfSubsets; j++) {
                List<Point> subsetHull = INC_CH(points.subList((j-1)*elementsInSubset,j*elementsInSubset));
                subsetHullsCombined.addAll(subsetHull);
            }

            //get the remaining points that doesnt divide evenly
            //the remaining points are put into the last partition, this in worst case means that the last partition is almost 2x of all the others
            List<Point> subsetHull = INC_CH(points.subList(elementsInSubset*(amountOfSubsets-1), n));
            subsetHullsCombined.addAll(subsetHull);




            List<Point> hull = GIFT_CH(subsetHullsCombined);
            //if the hull contains the left most and right most point return the final hull
            if (hull.contains(points.get(0)) && hull.contains(points.get(n-1))){

                return hull;
            }

        }
        return new ArrayList<Point>();
    }

    public static List<Point> CH_CHNEW(List<Point> points){

        int numberOfPoints = points.size();
        for (int i = 1; i <= Math.ceil(util.log2(util.log2(numberOfPoints))); i++) { //Ceiling to allow numbers that are not powers of two.
            //Point with rightmost/highest x coordinate
            float pXMax = points.get(numberOfPoints-1).x;
            int h = (int) Math.pow(2,Math.pow(2,i));
            List<Point> UH = UHWithSize(points, numberOfPoints, pXMax, h);
            if(UH.get(UH.size()-1).x == pXMax) {
                return UH;
            }
        }
        return new ArrayList<Point>();


    }

    private static List<Point> UHWithSize(List<Point> points, int numberOfPoints, float pXMax, int h) {
        List<Point> UH = new ArrayList<>();
        //list of lists of all points in the partial hulls
        List<List<Point>> subsetHullsCombined = new ArrayList<>();
        int m;
        if(numberOfPoints/h < 1) {
            m = 1;
        }
        else {
            m = numberOfPoints / h;
        }
        //int m = numberOfPoints / h; //If division is less than 1, return 1 instead to fix shit
        for (int j = 1; j < m; j++) { //Compute upperhulls for all subsets
            List<Point> subsetHull = INC_CH(points.subList((j-1)*h,j*h));
            subsetHullsCombined.add(subsetHull);
        }

        //get the remaining points that doesn't divide evenly
        //the remaining points are put into the last partition, this in worst case means that the last partition is almost 2x of all the others
        List<Point> subsetHull = INC_CH(points.subList(h*(m-1), numberOfPoints));
        subsetHullsCombined.add(subsetHull);


        //Initialize p to point with leftmost/lowest x coordinate
        Point p = subsetHullsCombined.get(0).get(0);
        Point rayEnd = new Point(p.x, Integer.MAX_VALUE);
        Line2D ray = new Line2D.Float(p.x,p.y,rayEnd.x, rayEnd.y);
        for(int j = 0; j < h; j++){ //Loop h times
            UH.add(p);
            //Break if p is point with max x coordinate
            if(p.x == pXMax) {
                break;
            }

            //Perhaps substitute the angle stuff into utility method?
            float bestAngleSoFar = Float.MAX_VALUE;
            Line2D bestLineSoFar = ray;
            Point bestCandPSoFar = rayEnd;
            for(int k = 0; k < m; k++){ //Compute best upper tangent from p to all subhulls
                List<Point> subsetHullK = subsetHullsCombined.get(k);
                if(subsetHullK.size() > 0){
                    Point candP = BinarySearchForBestTangentPoint(p, subsetHullK);
                    Line2D rayCand = new Line2D.Float(p.x, p.y, candP.x, candP.y);
                    if(p != candP){
                        float angleCand = util.angleBetween2Lines(ray, rayCand);
                        if(angleCand < bestAngleSoFar){
                            bestAngleSoFar = angleCand;
                            bestLineSoFar = rayCand;
                            bestCandPSoFar = candP;
                        }
                    }
                }
            }
            p = bestCandPSoFar;
            ray = bestLineSoFar;

            //Remove any vertex in subhulls to the left of new p
            Iterator<List<Point>> iterator = subsetHullsCombined.iterator();
            while(iterator.hasNext()){
                List<Point> subHull = iterator.next();
                Iterator<Point> iterator1 = subHull.iterator();
                boolean removalComplete = false;
                while(iterator1.hasNext()){
                    Point pToRemove = iterator1.next();
                    if(pToRemove.x < p.x){
                        iterator1.remove();
                    }
                    else {
                        removalComplete = true;
                        break;
                    }
                }
                if(removalComplete){
                    break;
                }
            }
        }
        return UH;
    }

    private static Point BinarySearchForBestTangentPoint(Point p, List<Point> points) {
        switch(points.size()) {
            case 1:
                return points.get(0);
            case 2:
                Point p0 = points.get(0);
                Point p1 = points.get(1);
                if(p == p0) {
                    return p1;
                }
                if(util.determineOrientation(p, p0, p1)){
                    return p1;
                }
                else {
                    return p0;
                }
            default:
                int medianIndex = points.size()/2;
                Point medianPoint = points.get(medianIndex);
                Point nextPoint = points.get(medianIndex + 1);
                Point prevPoint = points.get(medianIndex - 1);
                if(prevPoint == p) {
                    return medianPoint;
                }
                if(medianPoint == p) {
                    return nextPoint;
                }
                if(nextPoint == p) {
                    return BinarySearchForBestTangentPoint(p, points.subList(medianIndex + 1, points.size()));
                }
                boolean isNextAbove = util.determineOrientation(p, medianPoint, nextPoint);
                boolean isPrevBelow = !util.determineOrientation(p, medianPoint, prevPoint);
                if(isNextAbove && isPrevBelow){
                    //Move right and call recursively
                    return BinarySearchForBestTangentPoint(p, points.subList(medianIndex + 1, points.size()));
                }
                else if(!isNextAbove && !isPrevBelow) {
                    //Move left and call recursively
                    return BinarySearchForBestTangentPoint(p, points.subList(0, medianIndex));
                }
                else {
                    return medianPoint;
                }
        }
    }
}

