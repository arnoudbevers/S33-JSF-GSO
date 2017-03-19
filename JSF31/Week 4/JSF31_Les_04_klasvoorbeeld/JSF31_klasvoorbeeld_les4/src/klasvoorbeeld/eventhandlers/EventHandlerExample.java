/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.eventhandlers;

import javafx.application.Application;
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
public class EventHandlerExample extends Application {

    class MyHandlerB implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("Hello World from inner class MyHandlerB!");
        }
    }

    @Override
    public void start(Stage primaryStage) {

        // Handling events: 
        // You can use a separate class MyHandlerA that implements
        // the EventHandler<ActionEvent> interface
        Button btn0 = new Button();
        btn0.setText("Say 'Hello World' using separate class that handles event");
        btn0.setOnAction(new MyHandlerA());

        // Or you can use an inner class
        Button btn1 = new Button();
        btn1.setText("Say 'Hello World' using inner class that handles event");
        btn1.setOnAction(new MyHandlerB());

        // Or you can use an anonymous inner class
        Button btn2 = new Button();
        btn2.setText("Say 'Hello World' using anonymous inner class that handles event");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Say 'Hello World' using anonymous inner class that handles event"); 
            }
        });
        
        
//        btn2.addEventHandler(ActionEvent.ANY,new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Nice: another 'Hello World!' from another anonymous inner class that handles event");
//            }
//        });
        

        // Create a grid that holds the buttons
        GridPane grid = new GridPane();
        grid.add(btn0, 0, 0);
        grid.add(btn1, 0, 1);
        grid.add(btn2, 0, 2);

        Scene scene = new Scene(grid, 600, 500);

        primaryStage.setTitle("Several ways to implement handlers");
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
