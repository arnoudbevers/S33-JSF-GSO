package aexbanner.fondsen;

public class OnePlus implements IFonds{
	
	private double koers;
	
	@Override
	public String getNaam() {
		return "OnePlus";
	}

	@Override
	public double getKoers() {
		return koers;
	}
	
	public void setKoers(double modifierDouble){
        if (koers + modifierDouble < 0) {
            koers += (modifierDouble * -1);
        }
        koers += modifierDouble;
    }
}
