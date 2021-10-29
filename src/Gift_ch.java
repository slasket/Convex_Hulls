import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Gift_ch {
    public static void setCount(int count) {
        Gift_ch.count = count;
    }

    private static int count = 0;
    public static List<Point> GIFT_CH(List<Point> points){ //DENNE METODE ER BETYDELIG LANGSOMMERE END INC_CH
        List<Point> CH = new ArrayList<>();
        int idOfLeftmost = util.findLeftmostPointId(points);
        count += points.size();
        int idOfRightMost = util.findRightmostPointId(points);
        count += points.size();

        CH.add(points.get(idOfLeftmost));

        int idOfCurrentPoint = idOfLeftmost;
        Point rayEnd = new Point(points.get(idOfCurrentPoint).x, Integer.MAX_VALUE);
        Line2D line1 = new Line2D.Float(points.get(idOfCurrentPoint).x, points.get(idOfCurrentPoint).y, rayEnd.x, rayEnd.y);
        Line2D line2;
        Line2D bestLineSoFar = null;
        do{
            float bestSoFarAngle = Integer.MAX_VALUE;
            int bestPointIdxSoFar = Integer.MAX_VALUE;
            for (int i = 0; i < points.size(); i++) { //iterate over all points
                count++;
                line2 = new Line2D.Float(points.get(idOfCurrentPoint).x, points.get(idOfCurrentPoint).y, points.get(i).x, points.get(i).y);
                float angle = util.angleBetween2Lines(line1, line2);

                if(angle < bestSoFarAngle && !CH.contains(points.get(i)) || points.get(i) == points.get(0)){ //minimize v
                    bestSoFarAngle = angle;
                    bestPointIdxSoFar = i;  // v
                    bestLineSoFar = line2;  // pv
                }
            }
            if(CH.contains(points.get(bestPointIdxSoFar)) && bestPointIdxSoFar == idOfLeftmost) break;
            CH.add(points.get(bestPointIdxSoFar)); //add v to hull
            line1 = bestLineSoFar;  // set r to pv
            idOfCurrentPoint = bestPointIdxSoFar; // set p to v


        } while(idOfCurrentPoint != idOfRightMost);

        return CH;
    }

    public static int getCount() {
        return count;
    }
}
