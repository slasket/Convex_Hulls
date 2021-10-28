import java.awt.geom.Line2D;
import java.util.List;

public class util {
    public static boolean isStriclyOver(Point p1, Point p2, Point p3) {
        return p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y) > 0;
    }

    public static int isStriclyOverTemp(Point p1, Point p2, Point p3) {
        float orientationValue = p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y);
        if(orientationValue > 0) {
            return 1;
        }
        else if(orientationValue < 0){
            return -1;
        }
        else {
            return 0;
        }
    }

    public static boolean isStriclyOver1(Point p1, Point p2, Point p3) {
        return p1.x * p2.y - p1.x * p3.y + p2.x * p3.y - p2.x * p1.y + p3.x * p1.y - p3.x * p2.y > 0;
    }

    public static boolean isStriclyOver2(Point p1, Point p2, Point p3) {
        return p1.x * p2.y + p2.x * p3.y + p3.x * p1.y - p1.x * p3.y - p2.x * p1.y - p3.x * p2.y > 0;
    }

    public static float angleBetween2Lines(Line2D line1, Line2D line2) {
        double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                line1.getX1() - line1.getX2());
        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                line2.getX1() - line2.getX2());
        float angle = (float) (Math.abs(Math.toDegrees(angle1) - Math.toDegrees(angle2)));
        if (angle > 180) return 360 - angle;
        return angle;
    }
    //returns true if counter clockwise turn, false if clockwise
    public static boolean determineOrientation(Point p1, Point p2, Point p3){
        float orientationValue = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);
        return (orientationValue <= 0); //if the value is less than 0 return true, a counter clockwise turn
    }
    public static int indexOfSmallesElement(List<Float> list) {
        int indexOfMin = 0;
        float minVal = Float.POSITIVE_INFINITY;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < minVal) {
                minVal = list.get(i);
                indexOfMin = i;
            }
        }
        return indexOfMin;
    }

    public static float dist2Points(Point p1, Point p2){
        return (float) Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
    }

    //public static float angleBetween2Lines(float[] line1, float[] line2) {
    //    double angle1 = Math.atan2(line1[1]- line1[3],
    //            line1[0] - line1[2]);
    //    double angle2 = Math.atan2(line2[1] - line2[3],
    //            line2[0] - line2[2]);
    //    float angle = (float) (Math.abs(Math.toDegrees(angle1) - Math.toDegrees(angle2)));
    //    if (angle > 180) return 360 - angle;
    //    return angle;
    //}

    public static float angleBetween2Slopes(float slope1, float slope2) {
        float angle = (float) (Math.atan((slope2 - slope1) / (1 - slope2 * slope1)));
        boolean isDevil = angle < 0;
        if (isDevil) {
            System.out.println("angle is devil: " + angle + " slope1: " + slope1 + " slope2: " + slope2);
        }
        return angle;
    }

    public static boolean compareLists(List l1, List l2){
        boolean result = true;
        if(l1.size()!=l2.size()) {
            System.out.println("Lists are not even the same size");
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if(!l2.contains(l1.get(i))) {
                System.out.println("L2 did not contain: " + l1.get(i));
                result = false;
            }
        }
        for (int i = 0; i < l2.size(); i++) {
            if(!l1.contains(l2.get(i))) {
                System.out.println("L1 did not contain: " + l2.get(i));
                result = false;
            }
        }
        for (int i = 0; i < l1.size(); i++) {
            if(l1.get(i) != l2.get(i) && result){
                System.out.println("Lists are not in the same order");
                return false;
            }
        }
        return result;
    }

        //taken from https://www.techiedelight.com/calculate-log-base-2-in-java/
    public static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2) + 1e-11);
    }

    static int findLeftmostPointId(List<Point> points) {
        float leftMostXValue = Float.MAX_VALUE;
        int resultId = Integer.MAX_VALUE;
        for (Point p :
                points) {
            if (resultId == Integer.MAX_VALUE || p.x <= leftMostXValue) {
                if(p.x == leftMostXValue){
                    if(p.y < points.get(resultId).y){
                        leftMostXValue = p.x;
                        resultId = points.indexOf(p);
                    }
                    continue;
                }
                leftMostXValue = p.x;
                resultId = points.indexOf(p);
            }
        }
        return resultId;
    }

    public static int findRightmostPointId(List<Point> points) {
        float rightMostXValue = Float.MIN_VALUE;
        int resultId = Integer.MAX_VALUE;
        for (Point p :
                points) {
            if (resultId == Integer.MAX_VALUE || p.x >= rightMostXValue) {
                if(p.x == rightMostXValue){
                    if(p.y > points.get(resultId).y){
                        rightMostXValue = p.x;
                        resultId = points.indexOf(p);
                    }
                    continue;
                }
                rightMostXValue = p.x;
                resultId = points.indexOf(p);
            }
        }
        return resultId;
    }
}