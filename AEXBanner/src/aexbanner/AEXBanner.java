package aexbanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author arnoudbevers
 */
public class AEXBanner extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("AEXBanner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void stop(){}
    
}
