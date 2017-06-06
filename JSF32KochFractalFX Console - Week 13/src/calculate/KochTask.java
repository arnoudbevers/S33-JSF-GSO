/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * @author arnoudbevers
 */
public class KochTask implements Callable<List<Edge>>, Observer {

	private KochManager manager;
	private int edge;
	private List<Edge> edges;
	private KochFractal f;
	private MappedByteBuffer out;
	private FileChannel fc;

	public KochTask(KochManager manager, int edge, int level, MappedByteBuffer out) {
		this.manager = manager;
		this.edge = edge;
		this.f = new KochFractal();
		this.f.setLevel(level);
		this.f.addObserver(this);
		this.edges = new ArrayList<Edge>();
		this.out = out;
	}

	public KochTask(KochManager manager, int edge, int level, FileChannel fc) {
		this.manager = manager;
		this.edge = edge;
		this.f = new KochFractal();
		this.f.setLevel(level);
		this.f.addObserver(this);
		this.edges = new ArrayList<Edge>();
		this.fc = fc;
	}

	public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        edges.add(e);
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
        	try(ObjectOutputStream oos = new ObjectOutputStream(bos)){
        		oos.writeObject(e);
        	} catch (IOException ex) {
			}
        	byte[] array = bos.toByteArray();
//        	ByteBuffer buf = ByteBuffer.allocate(array.length);
//        	buf.put(array);
        	// Put byte array in file fucking fast
        	out.put(array);
        } catch (IOException ex) {
        }
        
        
        
//        // Create byte array from edge
//    	try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
//            try(ObjectOutputStream oos = new ObjectOutputStream(bos)){
//                oos.writeObject(e);
//            } catch (IOException ex) {
//			}
//            
//            // Put byte array in file fucking fast
//            out.put(bos.toByteArray());
//        } catch (IOException ex) {
//		}
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
