import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inc_ch {
    public static List<Point> INC_CH(List<Point> points){
        List<Point> UH = new ArrayList<>();
        List<Point> pointsSorted = new ArrayList<>(List.copyOf(points));
        Collections.sort(pointsSorted);

        //making UH
        for (int i = 0; i < pointsSorted.size(); i++) {
            while(UH.size() >= 2 && util.determineOrientation(UH.get(UH.size()-2), UH.get(UH.size()-1), pointsSorted.get(i))){
                UH.remove(UH.size() - 1);
            }
            UH.add(pointsSorted.get(i));
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
}
