/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.*;

/**
 *
 * @author arnoudbevers
 */
public class KochRunnable implements Runnable, Observer {

    private KochManager manager;
    private KochFractal kochFractal;
    private int edge;
    
    public KochRunnable(KochManager manager, KochFractal kochFractal, int edge){
        this.manager = manager;
        this.kochFractal = kochFractal;
        this.edge = edge;
    }
    
    @Override
    public void run() {
        switch(edge){
            case 1:
                kochFractal.generateBottomEdge();
                break;
            case 2:
                kochFractal.generateLeftEdge();
                break;
            case 3:
                kochFractal.generateRightEdge();
                break;
        }
        manager.count++;
        
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        manager.edges.add(e);
        System.out.println(e.color);
        manager.kffx.setTextNrEdges(kochFractal.getNrOfEdges() + "");
    }
    
}
