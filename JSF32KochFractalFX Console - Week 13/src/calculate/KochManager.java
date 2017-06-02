package calculate;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.scene.paint.Color;
import timeutil.TimeStamp;

public class KochManager {
    private volatile List<Edge> edges;
    public int count = 0;
    public ExecutorService pool;
    private String outputFile;
    private RandomAccessFile raf;
    private FileChannel fc;
    private TimeStamp ts = new TimeStamp();
	

    public KochManager(String outputFile) {
        this.edges = new ArrayList<Edge>();
        this.pool = Executors.newFixedThreadPool(4);
        this.outputFile = outputFile;
        
        try {
			raf = new RandomAccessFile(new File(outputFile), "rw");
			fc = raf.getChannel();
		} catch (FileNotFoundException e) {
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
        // Get amount edges per side
        int amountEdges = (int) (3 * Math.pow(4, level - 1))/3;
        // Create temporary edge
        Edge tmp = new Edge(0, 0, 0, 0, Color.WHITE);
        int tmpLength = 0;
        
        // Get length edge as byte array
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            try(ObjectOutputStream oos = new ObjectOutputStream(bos)){
                oos.writeObject(tmp);
            } catch (IOException ex) {
			}
            byte[] array = bos.toByteArray();
            tmpLength = array.length;
        } catch (IOException ex) {
		}
        
        ts.init();
    	ts.setBegin("Start Calcultating & Writing for level: " + level + "...");
        
        for (int i = 0; i < 3; i++) {
        	// Create mapped byte buffer split in 3 sections
        	// Each section for each kochtask
        	// app me for explanation 06-21199476
        	MappedByteBuffer out = null;
			try {
				out = fc.map(MapMode.READ_WRITE, i*tmpLength*amountEdges, tmpLength*amountEdges);
			} catch (IOException e) {
			}
            //KochTask run = new KochTask(this, i, level, out);
			KochTask run = new KochTask(this, i, level, fc);
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
            		ts.setEnd("Ended Calculating & Writing for level: " + level + "...");
            		System.out.println(ts.toString());
            		fc.close();
            		raf.close();
            		System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
    }
}
