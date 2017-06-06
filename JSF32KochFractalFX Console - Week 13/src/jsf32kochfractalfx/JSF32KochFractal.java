package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

    public static void main(String[] args) {
        int level = 4;

        String outputFile = "/home/arnoudbevers/Desktop/edges" + level + ".bin";
        KochManager manager = new KochManager(outputFile);
        manager.calculateLevel(level);
    }

  

}
