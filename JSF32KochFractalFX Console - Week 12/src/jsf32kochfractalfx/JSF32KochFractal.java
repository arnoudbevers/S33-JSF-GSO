package jsf32kochfractalfx;

import calculate.KochManager;

public class JSF32KochFractal {

	public static void main(String[] args) {
		if(args.length == 2 && args[0].equals("-l") && isInteger(args[1])) {
			int level = Integer.parseInt(args[1]);
			KochManager manager = new KochManager();
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
