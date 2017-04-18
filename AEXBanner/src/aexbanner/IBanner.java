package aexbanner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import aexbanner.fondsen.IFonds;
import fontys.observer.RemotePropertyListener;

/**
 * 
 * @author bramhoven
 *
 */
public interface IBanner extends Remote, RemotePropertyListener {
	public void setKoersen(List<IFonds> fondsen) throws RemoteException;
}
