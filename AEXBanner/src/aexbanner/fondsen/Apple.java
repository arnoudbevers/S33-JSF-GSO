package aexbanner.fondsen;

public class Apple implements IFonds {

	@Override
	public String getNaam() {
		return "Apple";
	}

	@Override
	public double getKoers() {
		return (double)(Math.random()*50.00 + 100);
	}

}
