package threadrunner;

/**
 * Runnable klas met maar 1 commando in de run() methode
 * @author erik
 */
public class HelloRunnable implements Runnable {
    private boolean moreWork = true;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && moreWork) {
                // do work
                Thread.sleep(500);
                // print iets naar standaard output
                System.out.println("Thread: " + Thread.currentThread().getId() + " Hello! Zomaar wat output");
                // wacht even
                Thread.sleep(500);
                
                // stop thread
                this.noMoreWork();
                
            }
        } catch (InterruptedException e) {
            // thread interrupted during 'sleep' or 'wait'
            System.out.println("interrupted during sleep or wait");
        } finally {
            // cleanup, if required
        }

    }
    
    /**
     * Let the runnable know there is no more work to do
     */
    public void noMoreWork() {
        this.moreWork = false;
    }

}
