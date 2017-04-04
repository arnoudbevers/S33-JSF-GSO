/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoball;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Bob Steers
 */
public class DemoBallController extends DemoBallMain implements Initializable
{

    Circle demoBall, centerDemoBall;
    Timer timer;
    TimerTask ballTask;
    AnimationTimer animTimer;

    // Items labelled with @FXML are loaded from the .fxml file.
    // Their name corresponds to the fx:id declared in Scene Builder 
    // (Select Item -> Inspector (right hand side of screen) -> Code)
    // Not every item in Scene Builder needs a fx:id, but every @FXML declared item here needs to match an fx:id in the .fxml
    @FXML
    Button btnPause;
    
    @FXML
    Label lblDemoBall;

    /**
     * For a class used as a controller for a .fxml this should be used in place of a constructor.
     * It is automatically called by the FXMLLoader if the class name is specified as Controller in Scene builder
     * (Left side in Scene Builder 2.0, Controller tab -> "Controller Class")
     * 
     * @param location can be ignored, but is mandatory
     * @param resources can be ignored, but is mandatory
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // initialises a new Circle (the ball), and sets its starting coordinates to be 100,100
        // default width is 2*radius
        demoBall = new Circle(100, 100, 20);
        // adds the circle to the window it should be displayed in
        ((AnchorPane) lblDemoBall.getParent()).getChildren().add(demoBall);
        
        // Adds a new (red) ball, to demonstrate the drawing origins of the balls
        // Because X and Y coords are bound to the first ball, this ball will move along
        centerDemoBall = new Circle(100, 100, 5, Color.RED);
        ((AnchorPane) lblDemoBall.getParent()).getChildren().add(centerDemoBall);
        centerDemoBall.toFront();
        centerDemoBall.centerXProperty().bind(demoBall.centerXProperty());
        centerDemoBall.centerYProperty().bind(demoBall.centerYProperty());
        

        // instantiates the timer, and adds functionality that whenever the ball is clicked for the first time,
        // the ball starts moving, and the label will start updating itself
        timer = new Timer();
        demoBall.setOnMouseClicked((MouseEvent e) ->
        {
            if (ballTask == null)
            {
                // schedules a new BallTimer as timertask. BallTimer is responsible for movement of given ball.
                ballTask = new BallTimerTask(demoBall);
                timer.scheduleAtFixedRate(ballTask, 100, 100);
                // Enables the pause button
                btnPause.setDisable(false);

                // starts the animation timer, which will continuously update the label.
                animTimer = new AnimationBallTimer(lblDemoBall, demoBall);
                animTimer.start();
            }
            else
            {
                // Action methods can be called from the class itself.
                // As the method itself does not use the Event parameter, it can be null.
                this.startStopBall(null);
            }
        });
    }
    
    /**
     * Pauses or resumes ball movement. 
     * This method's trigger is declared in Scene Builder under the Code section of the button ("On Action").
     * The Event parameter is mandatory. Subtypes (KeyEvent, MouseEvent) are not allowed
     * @param evt 
     */
    public void startStopBall(Event evt)
    {
        // Casts ballTask to BallTimerTask, because currently it's declared in its more generic form: TimerTask
        boolean isPaused = ((BallTimerTask)ballTask).setPaused();
        
        // Sets the button text to display the action taken by the next click
        if(isPaused)
        {
            this.btnPause.setText("Resume");
        }
        else
        {
            this.btnPause.setText("Pause");
        }
    }
}
