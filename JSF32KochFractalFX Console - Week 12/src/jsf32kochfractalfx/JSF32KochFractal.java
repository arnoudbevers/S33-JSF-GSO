package jsf32kochfractalfx;

import calculate.KochManager;

public class JSF32KochFractal {

	public static void main(String[] args) {
		if(args.length == 3 && args[0].equals("-l") && isInteger(args[1])) {
			int level = Integer.parseInt(args[1]);
			String outputFile = args[2];
			KochManager manager = new KochManager(outputFile);
			manager.calculateLevel(level);
		}
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
