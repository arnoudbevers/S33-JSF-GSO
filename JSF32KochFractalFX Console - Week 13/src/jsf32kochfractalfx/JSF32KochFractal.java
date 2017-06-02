package jsf32kochfractalfx;

import calculate.KochManager;
import timeutil.TimeStamp;

public class JSF32KochFractal {

	public static void main(String[] args) {
		int level = 10;
		
		String outputFile = "C:\\Users\\bramh\\Documents\\test" + level + ".bin";
		KochManager manager = new KochManager(outputFile);
		manager.calculateLevel(level);
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
