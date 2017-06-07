package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

    public static void main(String[] args) {
        int level = 4;

        String outputFile = System.getProperty("user.home").toString() + "\\Desktop\\edges" + level + ".bin";
        System.out.println(outputFile);
        KochManager manager = new KochManager(outputFile);
        manager.calculateLevel(level);
    }

  

}
