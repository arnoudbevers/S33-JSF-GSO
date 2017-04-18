package aexbanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author arnoudbevers
 */
public class AEXBanner extends Application {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 120;
    public static final int NANO_TICKS = 20000000; 
    public static final int SPEED = 3;
    // FRAME_RATE = 1000000000/NANO_TICKS = 50;

    private Text text;
    private Text text2;
    private double textLength;
    private double textPosition;
    private double text2Position;
    private BannerController controller;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage) {
        Font font = new Font("Arial", HEIGHT);
        text = new Text();
        text.setFont(font);
        text.setFill(Color.BLACK);
        
        text2 = new Text();
        text2.setFont(font);
        text2.setFill(Color.BLACK);

        Pane root = new Pane();
        root.getChildren().add(text);
        root.getChildren().add(text2);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("AEX banner");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();


        // Start animation: text moves from right to left
        animationTimer = new AnimationTimer() {
            private long prevUpdate;

            @Override
            public void handle(long now) {
                long lag = now - prevUpdate;
                if (lag >= NANO_TICKS) {
                	float amount = (SPEED/100000000000000.f)*now;
                	textPosition -= amount;
                	text2Position -= amount;
                	text.relocate(textPosition,-20);
                	text2.relocate(text2Position,-20);
                	
                	if(textPosition+textLength <= 0) {
                		textPosition = text2Position+textLength;
                	}
                	
                	if(text2Position+textLength <= 0) {
                		text2Position = textPosition+textLength;
                	}
                	
                	prevUpdate = now;
                }
            }
            @Override
            public void start() {
                prevUpdate = System.nanoTime();
                textPosition = WIDTH;
                text2Position = textPosition+textLength;
                text.relocate(textPosition, 0);
                text2.relocate(text2Position, 0);
                setKoersen("Nothing to display");
                super.start();
            }
        };
        animationTimer.start();
        
        controller = new BannerController(this);
    }

    public void setKoersen(String koersen) {
        text.setText(koersen);
        text2.setText(koersen);
        textLength = text.getLayoutBounds().getWidth();
        
        if(textPosition < text2Position) {
        	text2Position = textPosition+textLength;
        } else {
        	textPosition = text2Position+textLength;
        }
    }

    @Override
    public void stop() {
        animationTimer.stop();
        controller.stop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
