/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author arnoudbevers
 */
public class KochTask implements Callable<List<Edge>>, Observer {

    private int edge;
    private List<Edge> edges;
    private KochFractal f;
    private ObjectOutputStream ooStream;
    private BufferedWriter out;

    public KochTask(int edge, int level, ObjectOutputStream ooStream) {
        this.edge = edge;
        this.f = new KochFractal();
        this.f.setLevel(level);
        this.f.addObserver(this);
        this.edges = new ArrayList<Edge>();
        this.ooStream = ooStream;
    }
    
    public KochTask(int edge, int level, BufferedWriter out) {
        this.edge = edge;
        this.f = new KochFractal();
        this.f.setLevel(level);
        this.f.addObserver(this);
        this.edges = new ArrayList<Edge>();
        this.out = out;
    }

    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        edges.add(e);
        synchronized(out.getClass()){
            try{
                out.write(e.X1 + ";");
                out.write(e.Y1 + ";");
                out.write(e.X2 + ";");
                out.write(e.Y2 + ";");
                out.write(e.color.toString());
                out.append("\n");
            } catch (IOException ex) {
                Logger.getLogger(KochTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        synchronized(ooStream.getClass()) {
//	        try {
//                        ooStream.writeObject(e);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//        }
    }

    public List<Edge> call() throws Exception {
        switch (edge) {
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
