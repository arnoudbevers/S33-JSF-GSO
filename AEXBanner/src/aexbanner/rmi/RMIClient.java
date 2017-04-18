package aexbanner.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import aexbanner.BannerController;
import aexbanner.IEffectenBeurs;

/**
 * 
 * @author bramhoven
 *
 */
public class RMIClient {
	private static final String bindingName = "effectenbeurs";
	private Timer pollingTimer;

	private Registry registry = null;
	private BannerController bannerController;
	private IEffectenBeurs effectenbeurs = null;

	public RMIClient(String ipAddress, int portNumber, BannerController bc) {
		bannerController = bc;
		System.out.println("Client: IP Address: " + ipAddress);
		System.out.println("Client: Port number " + portNumber);

		try {
			registry = LocateRegistry.getRegistry(ipAddress, portNumber);
		} catch (RemoteException ex) {
			System.out.println("Client: Cannot locate registry");
			System.out.println("Client: RemoteException: " + ex.getMessage());
			registry = null;
		}

		if (registry != null) {
			System.out.println("Client: Registry located");
		} else {
			System.out.println("Client: Cannot locate registry");
			System.out.println("Client: Registry is null pointer");
		}

		pollingTimer = new Timer();
		pollingTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (registry != null) {
					try {
						effectenbeurs = (IEffectenBeurs) registry.lookup(bindingName);
						bannerController.setEffectenBeurs(effectenbeurs);
						//System.out.println("Client: Effectenbeurs bound");
					} catch (RemoteException ex) {
						System.out.println("Client: Cannot bind effectenbeurs");
						System.out.println("Client: RemoteException: " + ex.getMessage());
						bannerController = null;
					} catch (NotBoundException ex) {
						System.out.println("Client: Cannot bind effectenbeurs");
						System.out.println("Client: NotBoundException: " + ex.getMessage());
						bannerController = null;
					}
				}
			}
		}, 0, 1000);
	}

	public IEffectenBeurs getEffectenbeurs() {
		return bannerController.getEffectenBeurs();
	}
}
