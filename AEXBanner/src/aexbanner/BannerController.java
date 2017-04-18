package aexbanner;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aexbanner.fondsen.IFonds;
import aexbanner.rmi.RMIClient;

/**
 *
 * @author arnoudbevers
 */
public class BannerController extends UnicastRemoteObject implements IBanner {
	private AEXBanner banner;
    private IEffectenBeurs effectenbeurs;
    private List<IFonds> koersen;
    private Timer pollingTimer;
    private RMIClient client;
	
	public BannerController(AEXBanner banner) throws RemoteException {
		super();
        this.banner = banner;
        this.client = new RMIClient("localhost", 5081, this);
        try {
			this.effectenbeurs = new MockEffectenBeurs();
		} catch (RemoteException e) {
		}
        
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				String display = "";
				if(koersen != null) {
					for(IFonds f : koersen) {
						display += f.getNaam() + ": " + ((int) (f.getKoers())) + " ";
					}
				}
				banner.setKoersen(display);
			}
        }, 0, 1000);
    }

    public void stop() {
        pollingTimer.cancel();
    }

    public void setEffectenBeurs(IEffectenBeurs beurs) {
    	this.effectenbeurs = beurs;
    }
    
    public IEffectenBeurs getEffectenBeurs() {
    	return effectenbeurs;
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
		setKoersen((List<IFonds>) evt.getNewValue());
	}

	@Override
	public void setKoersen(List<IFonds> fondsen) throws RemoteException {
		koersen = fondsen;
	}
}
