package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

public class KochManager implements Observer {

	private JSF31KochFractalFX kffx;
	private TimeStamp tsCalc;
	private TimeStamp tsDraw;
	private List<Edge> edges;
	
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

	@Override
	public void update(Observable o, Object arg) {
		Edge e = (Edge) arg;
		edges.add(e);
		//System.out.println("Begin punt: (" +e.X1 + ", " + e.Y1 + "); Eind punt: ("+e.X2+", "+e.Y2+") Color: " + e.color);
	}
}
