package aexbanner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import aexbanner.fondsen.IFonds;
import fontys.observer.RemotePublisher;

/**
 *
 * @author arnoudbevers
 */
public interface IEffectenBeurs extends RemotePublisher {
    List<IFonds> getKoersen() throws RemoteException;
}
