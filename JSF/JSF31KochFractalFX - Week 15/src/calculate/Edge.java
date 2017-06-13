/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class Edge implements Serializable {
    public double X1, Y1, X2, Y2;
    public transient Color color;
    private double r,g,b,o;
    
    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
//        this.r = color.getRed();
//        this.g = color.getGreen();
//        this.b = color.getBlue();
//        this.o = color.getOpacity();
    }
    
    public void convertColor() {
    	this.color = new Color(r, g, b, o);
    }
}
