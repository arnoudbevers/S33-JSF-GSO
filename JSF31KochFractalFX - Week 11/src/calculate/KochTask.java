/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * @author arnoudbevers
 */
public class KochTask implements Observer, Callable<List<Edge>> {

    private KochManager manager;
    private int edge;
    private List<Edge> edges;
    private KochFractal f;
    
    public KochTask(KochManager manager, int edge, int level){
        this.manager = manager;
        this.edge = edge;
        this.f = new KochFractal();
        this.f.setLevel(level);
        this.f.addObserver(this);
        this.edges = new ArrayList<Edge>();
    }

    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        edges.add(e);
    }

	public List<Edge> call() throws Exception {
		switch(edge){
	        case 0:
	        	f.generateLeftEdge();
	            break;
	        case 1:
	        	f.generateBottomEdge();
	            break;
	        case 2:
	        	f.generateRightEdge();
	            break;
	        case 3:
	        	f.generateLeftEdge();
	        	f.generateBottomEdge();
	        	f.generateRightEdge();
	        	break;
	    }
		
		return edges;
	}
    
}
