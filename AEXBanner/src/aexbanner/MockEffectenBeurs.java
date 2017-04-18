package aexbanner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import aexbanner.fondsen.Apple;
import aexbanner.fondsen.IFonds;
import aexbanner.fondsen.ING;
import aexbanner.fondsen.OnePlus;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;

/**
 *
 * @author arnoudbevers
 */
public class MockEffectenBeurs extends UnicastRemoteObject implements IEffectenBeurs {
	private static final long serialVersionUID = -3345627302256729101L;

	private List<IFonds> koersen;
	private BasicPublisher publisher;
	private Timer timer;

	public MockEffectenBeurs() throws RemoteException {
		koersen = new ArrayList<IFonds>();
		koersen.add(new ING());
		koersen.add(new Apple());
		koersen.add(new OnePlus());

		String[] properties = new String[1];
		properties[0] = "koersen";
		publisher = new BasicPublisher(properties);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 1000);
	}

	@Override
	public List<IFonds> getKoersen() {
		return koersen;
	}

	public void update() {
		Random r = new Random();
		for (IFonds f : koersen) {
			double modifier = (r.nextInt(4) - 2) + r.nextDouble();
			f.setKoers(modifier);
		}
		publisher.inform(this, "koersen", null, koersen);
	}

	@Override
	public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
		publisher.addListener(listener, property);
	}

	@Override
	public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
		publisher.removeListener(listener, property);
	}

}
