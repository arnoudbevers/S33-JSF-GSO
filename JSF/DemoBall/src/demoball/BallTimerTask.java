/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoball;

import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

/**
 *
 * @author Bob Steers
 */
public class BallTimerTask extends TimerTask
{

    private DoubleProperty xPos, yPos, radius, sceneWidth, sceneHeight;
    private double xMovement, yMovement;
    private boolean isPaused;
    
    /**
     * Toggles paused status. 
     * @return new paused status
     */
    public boolean setPaused()
    {
        this.isPaused = !isPaused;
        return this.isPaused;
    }

    /**
     * Instantiates a new BallTimer, with three properties to govern the given
     * javaFX circle shape (ball). Currently yPos is unused,
     *
     * @param ball
     */
    public BallTimerTask(Circle ball)
    {
        // initialises the properties controlling the x/y coordinates of the ball on the ball's coordinates
        xPos = new SimpleDoubleProperty(ball.getCenterX());
        yPos = new SimpleDoubleProperty(ball.getCenterY());
        // does the same for the ball's radius
        radius = new SimpleDoubleProperty(ball.getRadius());

        // binds the actual ball's settings to the instance properties
        // whenever the instance properties (xPos, yPos, radius) are changed, 
        // the ball's properties will automatically change themselves to the new value
        ball.centerXProperty().bind(xPos);
        ball.centerYProperty().bind(yPos);
        ball.radiusProperty().bind(radius);

        // binds sceneWidth and sceneHeight property to actual width and height
        // this prevents having to refresh or hardcode screen size properties
        this.sceneHeight = new SimpleDoubleProperty();
        this.sceneHeight.bind(ball.getScene().heightProperty());
        this.sceneWidth = new SimpleDoubleProperty();
        this.sceneWidth.bind(ball.getScene().widthProperty());
        

        // sets the default x/y distance the ball will change every time run() is called
        // this value will flip between -10 and +10, depending on whether the ball should go right/down or left/up
        xMovement = 10;
        yMovement = 10;
        
        // sets isPaused to initial status of false
        this.isPaused = false;
    }

    @Override
    public void run()
    {
        if(isPaused)
        {
            return;
        }
        
        // reverse x bounce movement whenever the ball would cross the right border on its current course
        if (xMovement > 0 && xPos.get() + xMovement + radius.get() > sceneWidth.get())
        {
            xMovement *= -1;
        }
        // reverses x bounce movement if ball would cross left border
        if (xMovement < 0 && xPos.get() + xMovement - radius.get() < 0)
        {
            xMovement *= -1;
        }
        // reverses y bounce movement if ball would cross bottom border
        if( yMovement > 0 && yPos.get() + yMovement + radius.get() > sceneHeight.get())
        {
            yMovement *= -1;
        }
        // reverses y bounce movement if ball would cross top border
        if (yMovement <0 && yPos.get() + yMovement - radius.get() < 0)
        {
            yMovement *= -1;
        }

        // Increments x/y coordinates with (positive or negative) movement.
        // Do so in Platform.runLater in order to avoid changing a javaFX object while not on application thread
        Platform.runLater(() ->
        {
            xPos.set(xPos.get() + xMovement);
            yPos.set(yPos.get() + yMovement);
        });
    }

}
