package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

    public static void main(String[] args) {
        int level = 4;

        String outputFile = "/home/arnoudbevers/Desktop/edges4.bin";
        KochManager manager = new KochManager(outputFile);
        manager.calculateLevel(level);
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
