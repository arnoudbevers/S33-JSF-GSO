package threadrunner;

/**
 * Een runnable die continu blijft lopen totdat een interrupt() op de
 * thread aangeroepen wordt.
 * @author erik
 */
public class ContinuRunnable implements Runnable {

    private boolean moreWork = true;

    @Override
    public void run() {
        try {
            System.out.println("Thread: " + Thread.currentThread().getId() + "starting work");

            while (!Thread.currentThread().isInterrupted() && moreWork) {
                // do work
                // also cooperative. Generates InterruptedException when interrupted bit has been set.
                Thread.sleep(730);

                int total = 0;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    int a = 3;
                    int b = 5;
                    int c = a;
                    a = b;
                    b++;
                    b = c;
                    total = b + c;
                }
                System.out.print("("+Thread.currentThread().getId()+")");
            }
            System.out.println("Thread: " + Thread.currentThread().getId() + "interrupted tijdens werk");
        } catch (InterruptedException e) {
            // thread interrupted during 'sleep' or 'wait'
            System.out.println("Thread: " + Thread.currentThread().getId() + "interrupted tijdens sleep");
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
