/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.*;

import com.sun.media.jfxmediaimpl.platform.Platform;

/**
 *
 * @author arnoudbevers
 */
public class KochRunnable implements Runnable, Observer {

    private KochManager manager;
    private int edge;
    
    public KochRunnable(KochManager manager, int edge){
        this.manager = manager;
        this.edge = edge;
    }
    
    @Override
    public void run() {
        switch(edge){
            case 0:
            	manager.kffx.fractal.generateLeftEdge();
                break;
            case 1:
            	manager.kffx.fractal.generateBottomEdge();
                break;
            case 2:
            	manager.kffx.fractal.generateRightEdge();
                break;
            case 3:
            	manager.kffx.fractal.generateLeftEdge();
            	manager.kffx.fractal.generateBottomEdge();
            	manager.kffx.fractal.generateRightEdge();
            	break;
        }
        manager.updateCount();
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        //System.out.println(edge + " " + e.color);
    	manager.addEdge(e);
    	manager.kffx.setTextNrEdges(manager.kffx.fractal.getNrOfEdges() + "");
    }
    
}
