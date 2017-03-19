package downloadsimulator;

import java.util.Random;

/**
 * Default structuur voor Runnable die gebruikt kan worden als basis
 * voor je eigen threads.
 * @author erik
 */
public class DownSimRunnable implements Runnable {
    
    private boolean moreWork = true;
    private String url;
    private long id;

    public DownSimRunnable(String url) {
        this.url = url;
    }
        
    @Override
    public void run() {
        this.id = Thread.currentThread().getId();
        String header = "Thread{" + Long.toString(id) + "} : ";
        Random r = new Random();
        try{
            for (int i = 0; i <= 30; i++) {
                // do work
                // download chunk of data
                Thread.sleep(r.nextInt(1000));
                // Output
                int percentage = i*100/30;
                System.out.println(header + Integer.toString(percentage) + "% " + url);
            }
        } catch(InterruptedException e){
            // thread interrupted during 'sleep' or 'wait'
            System.out.println("interrupted during sleep or wait");        
        } finally {
            // cleanup, if required
        }

    }
    
    /**
     * Let the runnable know there is no more work to do
     */
    public void noMoreWork(){
        this.moreWork = false;
    }

}
