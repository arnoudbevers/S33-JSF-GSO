package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

    public static void main(String[] args) {
        int level = 5;

        String outputFile = System.getProperty("user.home") + "//Desktop//edges" + level + ".bin";
        KochManager manager = new KochManager(outputFile);
        manager.calculateLevel(level);
    }

  

}
