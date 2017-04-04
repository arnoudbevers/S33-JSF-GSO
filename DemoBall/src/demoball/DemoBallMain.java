/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoball;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Bob Steers
 */
public class DemoBallMain extends Application
{
    
    /**
     * Overall hierarchy is Screen (your own monitor) -> Window -> Stage -> Scene -> Parent -> Control
     * 
     * - If you want to show multiple windows on screen, you'd need to start multiple stages.
     * - If you want to switch what is shown in the window, without losing its position on the screen, 
     *      you need to load a new scene into an existing stage.
     * - The Parent contains all the javaFX elements contained in the .fxml, and corresponds to the AnchorPane in Scene Builder
     * @param stage
     * @throws IOException 
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        // loads FXML file into the root parent
        // without a full path (eg: C://Users/UserName/My Documents/...) 
        // the loader will assume the fxml file is in the same folder / package as this class
        Parent root = FXMLLoader.load(getClass().getResource("demoball.fxml"));
        
        // starts a new scene linked to the root, links it to the active window (through stage), and makes it visible
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        // closes the entire application when the exit button is pressed - if not the timers will stay active
        stage.setOnCloseRequest((WindowEvent event) ->
        {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * The entry point of the application. 
     * In a javaFX application, this is merely used to launch the JavaFX application by calling launch(args)
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
