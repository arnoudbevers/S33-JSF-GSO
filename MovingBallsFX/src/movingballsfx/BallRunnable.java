/*

 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private Monitor mon;
    private int state = 1;
    
    public BallRunnable(Ball ball, Monitor mon) {
        this.ball = ball;
        this.mon = mon;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
            	if(ball.isEnteringCs()) {
            		state = 2;
            		if(ball.getColor() == Color.RED) mon.enterReader();
            		else mon.enterWriter();
            	}
            	
            	if(state == 2) state = 3;
                ball.move();
                   
                if(ball.isLeavingCs()) {
                	state = 1;
                	if(ball.getColor() == Color.RED) mon.exitReader();
            		else mon.exitWriter();
                }
                
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
            	switch(state) {
            	case 2:
            		if(ball.getColor() == Color.RED) mon.readersWaiting--;
            		else mon.writersWaiting--;
            		break;
            	case 3:
            		if(ball.getColor() == Color.RED) mon.readersActive--;
            		else mon.writersActive--;
            		break;
            	}
            			
                Thread.currentThread().interrupt();
            }
        }
    }
}
