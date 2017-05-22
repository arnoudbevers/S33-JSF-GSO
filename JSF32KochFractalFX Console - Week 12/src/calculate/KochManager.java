package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KochManager {
    private volatile List<Edge> edges;
    public int count = 0;
    public ExecutorService pool;

    public KochManager() {
        this.edges = new ArrayList<Edge>();
        this.pool = Executors.newFixedThreadPool(4);
    }

    public void addEdges(List<Edge> es) {
        for (Edge e : es) {
            edges.add(e);
        }
    }
    
    public void calculateLevel(int level) {        
        List<Future<List<Edge>>> tasks = new ArrayList<Future<List<Edge>>>();
        
        for (int i = 0; i < 3; i++) {
            KochTask run = new KochTask(i, level);
            tasks.add(pool.submit(run));
        }
        
        pool.execute(new Runnable() {
        	public void run() {
            	for(Future<List<Edge>> fut : tasks) {
            		try {
						addEdges(fut.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
            	}
            	saveFractal();
        	}
        });
    }
    
    public void saveFractal() {
    	System.out.println(edges.size());
    }
}
