package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            tasks.add(run);
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
