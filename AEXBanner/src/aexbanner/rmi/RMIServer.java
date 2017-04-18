package aexbanner.rmi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Timer;

import aexbanner.IEffectenBeurs;
import aexbanner.MockEffectenBeurs;

public class RMIServer {

	private static final int portNumber = 5081;

	private static final String bindingName = "effectenbeurs";

	private Registry registry = null;
	private IEffectenBeurs mockEffectenbeurs = null;

	public RMIServer() {
		System.out.println("Server: Port number  " + portNumber);

		try {
			mockEffectenbeurs = new MockEffectenBeurs();
			System.out.println("Server: MockEffectenBeurs created");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			registry = LocateRegistry.createRegistry(portNumber);
			System.out.println("Server: Registry created on port number " + portNumber);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			registry.rebind(bindingName, mockEffectenbeurs);
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("SERVER USING REGISTRY");
		RMIServer server = new RMIServer();
	}
}
