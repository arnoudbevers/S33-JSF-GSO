/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author marcel
 * Ways to define a handler: make a separate class such as this class that
 * implements the interface of the handler
 */
public class MyHandlerA implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Hello World from class MyHandlerA!");
    }
    
}
