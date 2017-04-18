package aexbanner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import aexbanner.fondsen.IFonds;

/**
 *
 * @author arnoudbevers
 */
public interface IEffectenBeurs extends Remote {
    List<IFonds> getKoersen() throws RemoteException;
}
