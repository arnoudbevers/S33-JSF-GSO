package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

public class KochManager {

	public JSF31KochFractalFX kffx;
	private TimeStamp tsCalc;
	private TimeStamp tsDraw;
	private volatile List<Edge> edges;
    public int count=0;
	public ExecutorService pool;
	private List<Future<List<Edge>>> futures;
    
	public KochManager(JSF31KochFractalFX kffx) {
		this.kffx = kffx;
		this.tsCalc = new TimeStamp();
		this.tsDraw = new TimeStamp();
		this.edges = new ArrayList<Edge>();
		this.pool = Executors.newFixedThreadPool(4);
		this.futures = new ArrayList<>();
	}
	
	public synchronized void addEdges(List<Edge> es) {
		for(Edge e : es) {
			if(e != null) {
				if(!edges.contains(e)) {
					edges.add(e);
				}
			}
		}
	}
	
	public void changeLevel(int level) {
		edges.clear();
		
		tsCalc.init();
		tsCalc.setBegin("Begin calculating fractals");
		
		kffx.setTextCalc("Calculating...");
		kffx.setTextDraw("Waiting for calculation...");
		
		kffx.fractal.setLevel(level);
        edges.clear();
        
        for(int i = 0; i < 3; i++) {
            KochRunnable run = new KochRunnable(this, kffx.fractal, i);
            Future<List<Edge>> fut = pool.submit(run);
            futures.add(fut);
        }
        
        pool.execute(new Runnable() {
			public void run() {
				for(Future<List<Edge>> fut : futures) {
		        	try {
		        		addEdges(fut.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
		        }
		        
		        futures.clear();
		        
		        tsCalc.setEnd("End calculating fractals");
				kffx.setTextCalc(tsCalc.toString());
		        
		        kffx.requestDrawEdges();
			}
        	
        });
	}
	
	public void drawEdges() {
		kffx.clearKochPanel();
		
		tsDraw.init();
		tsDraw.setBegin("Begin drawing fractals");
		
		kffx.setTextDraw("Drawing...");
		
		for(Edge e : edges) {
			kffx.drawEdge(e);
		}
		
		tsDraw.setEnd("End drawing fractals");
		kffx.setTextDraw(tsDraw.toString());
	}
}
