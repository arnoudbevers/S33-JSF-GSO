/*      
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.guiupdate;

import java.util.logging.Level;
import java.util.logging.Logger;
import klasvoorbeeld.eventhandlers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author marcel
 */
public class GUIUpdateExample extends Application {

    Button btn0;
    Button btn1;

    class IncorrectHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        try {
                            // Do something ...
                            Thread.sleep(1000);
                            // Update GUI
                            btn0.setText("Update!");
                            System.out.println("IncorrectHandler: Another update!!");
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GUIUpdateExample.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            t.start();
        }
    }

    class CorrectHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        try {
                            // Do something ...
                            Thread.sleep(1000);
                            // Update GUI
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    btn1.setText("Update!");
                                    System.out.println("CorrectHandler: Another update!!");
                                }
                            });
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GUIUpdateExample.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            t.start();
        }
    }

    
    @Override
    public void start(Stage primaryStage) {

        // Handling events: 
        // You can use a separate class MyHandlerA that implements
        // the EventHandler<ActionEvent> interface
        btn0 = new Button();
        btn0.setText("Incorrect usage of GUI in thread!");
        btn0.setOnAction(new IncorrectHandler());

        // Or you can use an anonymous inner class
        btn1 = new Button();
        btn1.setText("Correct usage of GUI in thread");
        btn1.setOnAction(new CorrectHandler());

        
        Button btn2 = new Button();
        btn2.setText("Example why we must keep handling code short...");
        btn2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Before sleep!");
                    Thread.sleep(10000);
                    System.out.println("After sleep!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUIUpdateExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // Create a grid that holds the buttons
        GridPane grid = new GridPane();
        grid.add(btn0, 0, 0);
        grid.add(btn1, 0, 1);
        grid.add(btn2, 0, 2);

        Scene scene = new Scene(grid, 600, 500);

        primaryStage.setTitle("GUI and Threads");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
