package calculate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

public class KochManager {

    public JSF31KochFractalFX kffx;
    private TimeStamp tsCalc;
    private TimeStamp tsDraw;
    private volatile List<Edge> edges;
    public int count = 0;
    public ExecutorService pool;
    private int num = 0;
    private List<KochTask> tasks = new ArrayList<KochTask>();

    public KochManager(JSF31KochFractalFX kffx) {
        this.kffx = kffx;
        this.tsCalc = new TimeStamp();
        this.tsDraw = new TimeStamp();
        this.edges = new ArrayList<Edge>();
        this.pool = Executors.newFixedThreadPool(4);
    }

    public void loadFile(String file) {
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
    	
        TimeStamp ts = new TimeStamp();
        ts.init();
        ts.setBegin("Start reading....");
        
//    	RandomAccessFile raf;
//		try {
//			raf = new RandomAccessFile(new File(file), "r");
//			FileChannel fc = raf.getChannel();
//			
//			MappedByteBuffer mbb = fc.map(MapMode.READ_ONLY, 0, raf.length());
//			for(int i = 0; i <= raf.length()-tmpLength; i+= tmpLength) {
//				byte[] edgeBytes = new byte[tmpLength];
//				mbb.get(edgeBytes, 0, tmpLength);
//				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(edgeBytes));
//				Edge e = (Edge) ois.readObject();
//				e.convertColor();
//				edges.add(e);
//			}
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		
		ts.setEnd("Stopped Reading...");
		System.out.println(ts.toString());
		
    	int amount = edges.size();
    	int amountEdges = (int) ((Math.log(amount/3)/Math.log(4))+1)+1;
    	kffx.setCurrentLevel(amountEdges);
    	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				kffx.setTextNrEdges(amountEdges + "");
				drawEdges();
			}
		});
    }
    
    public synchronized void addEdges(List<Edge> es) {
        for (Edge e : es) {
            edges.add(e);
        }
    }
    
    public void changeLevel(int level) {
    	for(KochTask task : tasks) {
    		task.cancel(true);
    	}
        edges.clear();

        tsCalc.init();
        tsCalc.setBegin("Begin calculating fractals");

        kffx.setTextCalc("Calculating...");
        kffx.setTextDraw("Waiting for calculation...");
        
        num = 0;
        
        for (int i = 0; i < 3; i++) {
            KochTask run = new KochTask(this, i, level);
            switch(i){
                case 0:
                    //fleff
                    kffx.getProgressbarLeft().progressProperty().bind(run.progressProperty());
                    break;
                case 1:
                    //righff
                    kffx.getProgressbarRight().progressProperty().bind(run.progressProperty());
                    break;
                case 2: 
                    //boffomf
                    kffx.getProgressbarBottom().progressProperty().bind(run.progressProperty());
                    break;
                case 3:
                    //do nuffinf
                    break;
            }
            tasks.add(run);
            
            pool.execute(run);
            run.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					if(run.getValue().size() > 0) {
						addEdges(run.getValue());
						
						num++;
						
						if(num == 3) {
							tsCalc.setEnd("End calculating fractals");
			                kffx.setTextCalc(tsCalc.toString());
			                kffx.setTextNrEdges(edges.size() + "");
			                kffx.requestDrawEdges();
			                tasks.clear();
						}
					}
				}
			});
        }
    }

    public void drawEdges() {
        kffx.clearKochPanel();

        tsDraw.init();
        tsDraw.setBegin("Begin drawing fractals");

        kffx.setTextDraw("Drawing...");

        for (Edge e : edges) {
            kffx.drawEdge(e);
        }

        tsDraw.setEnd("End drawing fractals");
        kffx.setTextDraw(tsDraw.toString());

    }
}
