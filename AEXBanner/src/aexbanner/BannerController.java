package aexbanner;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aexbanner.fondsen.IFonds;
import aexbanner.rmi.RMIClient;

/**
 *
 * @author arnoudbevers
 */
public class BannerController {
	private AEXBanner banner;
    private IEffectenBeurs effectenbeurs;
    private Timer pollingTimer;
    private RMIClient client;
	
	public BannerController(AEXBanner banner) {
        this.banner = banner;
        this.client = new RMIClient("localhost", 5081, this);
        try {
			this.effectenbeurs = new MockEffectenBeurs();
		} catch (RemoteException e) {
		}
        
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					String display = "";
					List<IFonds> koersen = client.getEffectenbeurs().getKoersen();
					for(IFonds f : koersen) {
						display += f.getNaam() + ": " + ((int) (f.getKoers())) + " ";
					}
					banner.setKoersen(display);
				} catch(RemoteException e) {
					
				}
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
}
