package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

public class KochManager {

	public JSF31KochFractalFX kffx;
	private TimeStamp tsCalc;
	private TimeStamp tsDraw;
        //Volatile = thread-safe
	public volatile List<Edge> edges;
        public int count=0;
	
	public KochManager(JSF31KochFractalFX kffx) {
		this.kffx = kffx;
		this.tsCalc = new TimeStamp();
		this.tsDraw = new TimeStamp();
		this.edges = new ArrayList<Edge>();
	}
	
	public void changeLevel(int level) {
		edges.clear();
		
		tsCalc.init();
		tsCalc.setBegin("Begin calculating fractals");
		
		this.kffx.fractal.setLevel(level);
                edges.clear();
                for(int i = 1; i <=3;i++) {
                    KochRunnable run = new KochRunnable(this, this.kffx.fractal, i);
                    this.kffx.fractal.addObserver(run);
                    Thread t = new Thread(run);
                    t.start();
                }		
		tsCalc.setEnd("End calculating fractals");
		this.kffx.setTextCalc(tsCalc.toString());
	}
	
	public void drawEdges() {
		this.kffx.clearKochPanel();
		
		tsDraw.init();
		tsDraw.setBegin("Begin drawing fractals");
		
		for(Edge e : edges) {
                    this.kffx.drawEdge(e);
		}
		
		tsDraw.setEnd("End drawing fractals");
		this.kffx.setTextDraw(tsDraw.toString());
	}
}
