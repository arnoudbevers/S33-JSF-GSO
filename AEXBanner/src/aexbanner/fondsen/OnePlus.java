package aexbanner.fondsen;

public class OnePlus implements IFonds{

	@Override
	public String getNaam() {
		return "OnePlus";
	}

	@Override
	public double getKoers() {
		return (double)(Math.random()*10.00 + 113);
	}

}
