/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoball;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

/**
 *
 * @author Bob Steers
 */
public class AnimationBallTimer extends AnimationTimer
{
    Label myLabel;
    Circle ball;
    long prev;
    
    public AnimationBallTimer(Label label, Circle ball)
    {
        this.ball = ball;
        this.myLabel = label;
    }

    
    /**
     * Demonstrates use of an animationtimer, set to update once every 
     * 100 000 000 nanoseconds = every 100 ms.
     * @param now 
     */
    @Override
    public void handle(long now)
    {
        if(now - prev > (long) 100000000)
        {
            myLabel.setText("X: " + this.ball.centerXProperty().get() 
                    + System.getProperty("line.separator") // the safest way to insert a new line on all operating systems
                    + "Y: " + this.ball.centerYProperty().get());
            prev = now;
        }
    }
    
}
