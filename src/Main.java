import java.util.*;
import java.awt.geom.Line2D;

public class Main {

    private static int failCounter;

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
        //for(int i = 0; i < 50; i++){
        //    //System.out.println("Test number: " + (i+1));
            testVariableInputSize("compare", "xsqrd", 100000, "");
            testVariableInputSize("compare", "square", 100000, "");
            testVariableInputSize("compare", "circle", 100000, "");
        //    testVariableInputSize("time", "xsqrd", 100000,"");
        //    testVariableInputSize("time", "square", 100000,"");
        //    testVariableInputSize("time", "circle", 100000,"");
        //    //testVariableInputSize("compare", "circle", 100000);
        //}

        //System.out.println("Total fails = " + failCounter);
        //timeVSinputSizeToList("INC_CH","square");
        //timeVSinputSizeToList("INC_CH","circle");
        //timeVSinputSizeToList("INC_CH","xsqrd");
//
        //timeVSinputSizeToList("GIFT_CH","square");
        //timeVSinputSizeToList("GIFT_CH","circle");
        //timeVSinputSizeToList("GIFT_CH","xsqrd");
//
        //timeVSinputSizeToList("CH_CH","square");
        //timeVSinputSizeToList("CH_CH","circle");
        //timeVSinputSizeToList("CH_CH","xsqrd");
        //"xsqrd""square""circle"

    }

    private static void timeVSinputSizeToList(String method, String ArrType) {
        System.out.println(method+", "+ArrType+": input size, time in ms, hullsize:");
        for (int i = 6; i < 24; i++) {
            testVariableInputSize("timeToList", ArrType, (int) Math.pow(2,i),method);
        }
    }


    //option: compare or time, arrtype: xsqrd, square, circle, inputsize: number
    public static void testVariableInputSize(String option,String arrtype, int inputSize, String onlyAlgo){
        List<Point> testInput = new ArrayList<>();
        if (!option.equals("timeToList")){
            System.out.println(option+ " on "+arrtype + " of size "+inputSize);
        }
        switch (arrtype){
            case "xsqrd":
                testInput = pointGeneration.randomPointsXSqrdInverted(inputSize);
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
                if(!compareAlgorithms("INC_CH", "GIFT_CH", testInput)) failCounter++; //incrementing failCounter field if not same hull
                if(!compareAlgorithms("INC_CH", "CH_CH", testInput)) failCounter++;

                break;
            case "time":
                Long Duration = Long.parseLong(timeAlgo("INC_CH",testInput).split(";")[0]);
                System.out.println("INC_CH"+ " "+ Duration+"ms");

                Duration =Long.parseLong(timeAlgo("GIFT_CH",testInput).split(";")[0]);
                System.out.println("GIFT_CH"+ " "+ Duration+"ms");

                Duration =Long.parseLong(timeAlgo("CH_CH",testInput).split(";")[0]);
                System.out.println("CH_CH"+ " "+ Duration+"ms");
                break;
            case "timeToList":
                String res;
                String[] resSplit;
                String lineToPrint;
                res = timeAlgo(onlyAlgo, testInput);
                resSplit = res.split(";");
                long runningTimeInMs = Long.parseLong(resSplit[0]);
                int hullSize = Integer.parseInt(resSplit[1]);
                //long actualRunTimeDivTheoretical=0;
                //switch (onlyAlgo){
                //    case "INC_CH":
                //        actualRunTimeDivTheoretical =runningTimeInMs/((long) inputSize *util.log2(inputSize));
                //        break;
                //    case "GIFT_CH":
                //        actualRunTimeDivTheoretical = runningTimeInMs/((long) inputSize *hullSize);
                //        break;
                //    case "CH_CH":
                //        actualRunTimeDivTheoretical = runningTimeInMs/((long) inputSize *util.log2(hullSize));
                //        break;
                //}
                System.out.println(inputSize+".0,"+runningTimeInMs+".0,"+ hullSize+".0");

                break;

        }
        //System.out.println("");

    }

    public static String timeAlgo(String algo, List<Point> points){
        long startTime = System.nanoTime();
        List<Point> resArr = runAlgo(algo,points);
        long endTime = System.nanoTime();
        int resArrSize = resArr.size();
        long duration = (endTime - startTime)/1000000;
        String res = duration+";"+resArrSize;
        return res;
    }

    public static List<Point> runAlgo(String algo, List<Point> points){
        switch (algo) {
            case "INC_CH":
                return Inc_ch.INC_CH(points);
            case "GIFT_CH":
                return Gift_ch.GIFT_CH(points);
            case "CH_CH":
                return Ch_ch.CH_CH(points);
            default:
                return new ArrayList<>();
        }
    }


}

