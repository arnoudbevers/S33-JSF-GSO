package calculate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.locks.Lock;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import timeutil.TimeStamp;

/**
 *
 * @author arnoudbevers
 */
public class ReadRunnable implements Runnable {

    private KochManager manager;
    private String file;
    private int lastWritten = 0;
    

    public ReadRunnable(KochManager man, String file) {
        this.manager = man;
        this.file = file;
    }

    @Override
    public void run() {
        Edge tmpEdge = new Edge(0, 0, 0, 0, Color.WHITE);
        int tmpLength = 0;

        // Get length edge as byte array
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(tmpEdge);
            } catch (IOException ex) {
            }
            byte[] array = bos.toByteArray();
            tmpLength = array.length;
        } catch (IOException ex) {
        }

        TimeStamp ts = new TimeStamp();
        ts.init();
        ts.setBegin("Start reading....");

        RandomAccessFile raf;

        try {
            while(!new File(file).exists()) {}
        	
            raf = new RandomAccessFile(new File(file), "r");
            FileChannel fc = raf.getChannel();
            
            while(raf.length() < 4) {}
            
            Platform.runLater(new Runnable() {
            	public void run(){
                	manager.drawEdges();
                }
            });
            
            MappedByteBuffer mb1 = fc.map(FileChannel.MapMode.READ_ONLY, 0, 4);
            while (true) {
            	FileLock fl = fc.lock(0, 4, true);
                int edgesWritten = mb1.getInt(0);
                fl.release();
                
                if (lastWritten < edgesWritten) {
                    FileLock lock = null;
                    MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 4 + lastWritten * tmpLength, (edgesWritten - lastWritten) * tmpLength);
                    for (int i = 0; i <= mbb.limit() - tmpLength; i += tmpLength) {
                        lock = fc.lock(4 + lastWritten * tmpLength + i, tmpLength, true);
                        byte[] edgeBytes = new byte[tmpLength];
                        mbb.get(edgeBytes, 0, tmpLength);
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(edgeBytes));
                        Edge e = (Edge) ois.readObject();
                        e.convertColor();
                        
                        synchronized(manager.edges) {
                            manager.edges.add(e);
                        }

                        Platform.runLater(new Runnable() {
                        	public void run(){
                        		manager.kffx.drawEdge(e);
	                        }
	                    });
                        
                        lastWritten++;
                        lock.release();
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
        	e.printStackTrace();
        }

        ts.setEnd("Stopped Reading...");
        System.out.println(ts.toString());
        
        int amount = manager.edges.size();
        int level = (int) ((Math.log(amount / 3) / Math.log(4)) + 1);
        manager.kffx.setCurrentLevel(level);
    }
}
