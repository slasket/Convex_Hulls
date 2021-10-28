import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Ch_ch {
    public static List<Point> CH_CH(List<Point> points){
        int numberOfPoints = points.size();
        //Point with rightmost/highest x coordinate
        float pXMax = Collections.max(points).x;
        //Point with point with leftmost/lowest x coordinate
        Point leftMostPoint = Collections.min(points);
        for (int i = 1; i <= Math.ceil(util.log2(util.log2(numberOfPoints))); i++) { //Ceiling to allow numbers that are not powers of two.
            int h = (int) Math.pow(2,Math.pow(2,i));
            List<Point> UH = UHWithSize(points, numberOfPoints, pXMax, h, leftMostPoint);
            if(UH.get(UH.size()-1).x == pXMax) {
                return UH;
            }
        }
        return new ArrayList<Point>();

    }

    private static List<Point> UHWithSize(List<Point> points, int numberOfPoints, float pXMax, int h, Point leftMostPoint) {
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
        for (int j = 1; j < m; j++) { //Compute upperhulls for all subsets
            List<Point> subsetHull = Inc_ch.INC_CH(points.subList((j-1)*h,j*h));
            subsetHullsCombined.add(subsetHull);
        }

        //get the remaining points that doesn't divide evenly
        //the remaining points are put into the last partition, this in worst case means that the last partition is almost 2x of all the others
        List<Point> subsetHull = Inc_ch.INC_CH(points.subList(h*(m-1), numberOfPoints));
        subsetHullsCombined.add(subsetHull);


        //Point with point with leftmost/lowest x coordinate
        Point p = leftMostPoint;

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
                while(iterator1.hasNext()) {
                    Point pToRemove = iterator1.next();
                    if (pToRemove.x < p.x) {
                        iterator1.remove();
                    }
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
