package aexbanner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import aexbanner.fondsen.Apple;
import aexbanner.fondsen.IFonds;
import aexbanner.fondsen.ING;
import aexbanner.fondsen.OnePlus;

/**
 *
 * @author arnoudbevers
 */
public class MockEffectenBeurs extends UnicastRemoteObject implements IEffectenBeurs {

	private List<IFonds> koersen;
	
	public MockEffectenBeurs() throws RemoteException {
		koersen = new ArrayList<IFonds>();
		koersen.add(new ING());
		koersen.add(new Apple());
		koersen.add(new OnePlus());
	}
	
    @Override
    public List<IFonds> getKoersen() {
        return koersen;
    }
    
}
