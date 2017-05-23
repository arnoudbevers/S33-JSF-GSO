package calculate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private String outputFile;
    private ObjectOutputStream ooStream;

    public KochManager(String outputFile) {
        this.edges = new ArrayList<Edge>();
        this.pool = Executors.newFixedThreadPool(4);
        this.outputFile = outputFile;
        
		try {
			OutputStream oStream = new FileOutputStream(outputFile);
			BufferedOutputStream boStream = new BufferedOutputStream(oStream);
			this.ooStream = new ObjectOutputStream(boStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void addEdges(List<Edge> es) {
        for (Edge e : es) {
            edges.add(e);
        }
    }
    
    public void calculateLevel(int level) {        
        List<Future<List<Edge>>> tasks = new ArrayList<Future<List<Edge>>>();
        
        for (int i = 0; i < 3; i++) {
            KochTask run = new KochTask(i, level, ooStream);
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
            	try {
            		ooStream.flush();
            		ooStream.close();
            		System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
    }
}
