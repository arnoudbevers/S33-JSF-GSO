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
    private KochFractal fractal;
    private int level;
    
    public KochRunnable(KochManager manager, int edge, int level){
        this.manager = manager;
        this.edge = edge;
        this.fractal = new KochFractal();
        this.fractal.setLevel(level);
        this.fractal.addObserver(this);
    }
    
    @Override
    public void run() {
        switch(edge){
            case 0:
            	this.fractal.generateLeftEdge();
                break;
            case 1:
            	this.fractal.generateBottomEdge();
                break;
            case 2:
            	this.fractal.generateRightEdge();
                break;
            case 3:
            	this.fractal.generateLeftEdge();
            	this.fractal.generateBottomEdge();
            	this.fractal.generateRightEdge();
            	break;
        }
        manager.updateCount();
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        //System.out.println(edge + " " + e.color);
    	manager.addEdge(e);
    	//manager.kffx.setTextNrEdges(manager.kffx.fractal.getNrOfEdges() + "");
    }
    
}
