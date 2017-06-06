package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

	public static void main(String[] args) {
		int level = 5;
		
		String outputFile = "C:\\Users\\bramh\\Documents\\test" + level + ".bin";
		KochManager manager = new KochManager(outputFile);
		manager.calculateLevel(level);
	}
}
