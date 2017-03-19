/*
 * Small example that simulates multiple parallel file downloads to show
 * the very basics of threads
 */
package downloadsimulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcel
 */
public class DownloadSimulator {

    public static void main(String[] args) {
        try {
            DownSimRunnable ds0 = new DownSimRunnable("Netbeans8.1.zip");
            DownSimRunnable ds1 = new DownSimRunnable("IntelliJIDEA15.3.zip");
            DownSimRunnable ds2 = new DownSimRunnable("EclipseMars.1.zip");
                            
            Thread thread0 = new Thread(ds0);
            Thread thread1 = new Thread(ds1);
            Thread thread2 = new Thread(ds2);
            
            System.out.println("Starting all downloads:");
            thread0.start();
            thread1.start();
            thread2.start();
            
            // The main program will block untill all threads are finished
            thread0.join();
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(DownloadSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
