package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

public class KochManager {

	public JSF31KochFractalFX kffx;
	private TimeStamp tsCalc;
	private TimeStamp tsDraw;
	// Volatile = thread-safe
	private volatile List<Edge> edges;
	public int count = 0;

	public KochManager(JSF31KochFractalFX kffx) {
		this.kffx = kffx;
		this.tsCalc = new TimeStamp();
		this.tsDraw = new TimeStamp();
		this.edges = new ArrayList<Edge>();
	}

	public synchronized void addEdge(Edge e) {
		edges.add(e);
	}

	public void changeLevel(int level) {
		edges.clear();

		tsCalc.init();
		tsCalc.setBegin("Begin calculating fractals");

		kffx.setTextCalc("Calculating...");
		kffx.setTextDraw("Waiting for calculation...");

		// kffx.fractal.setLevel(level);
	
		for (int i = 0; i < 3; i++) {
			KochRunnable run = new KochRunnable(this, i, level);
			// kffx.fractal.addObserver(run);
			Thread t = new Thread(run);
			t.start();
		}
		// KochRunnable run = new KochRunnable(this, 3);
		// kffx.fractal.addObserver(run);
		// Thread t = new Thread(run);
		// t.start();
	}

	public void drawEdges() {
		this.kffx.clearKochPanel();

		tsDraw.init();
		tsDraw.setBegin("Begin drawing fractals");

		kffx.setTextDraw("Drawing...");

		for (Edge e : edges) {
			kffx.drawEdge(e);
		}

		tsDraw.setEnd("End drawing fractals");
		kffx.setTextDraw(tsDraw.toString());
	}

	public void updateCount() {
		count++;
		if (count >= 3) {
			kffx.requestDrawEdges();
			count = 0;

			Platform.runLater(new Runnable() {
				public void run() {
					tsCalc.setEnd("End calculating fractals");
					kffx.setTextCalc(tsCalc.toString());
					
				}
			});
		}
	}
}
