package aexbanner.fondsen;

public class ING implements IFonds{

	@Override
	public String getNaam() {
		return "ING";
	}

	@Override
	public double getKoers() {
		return (double)(Math.random()*20.00 + 50);
	}

}
