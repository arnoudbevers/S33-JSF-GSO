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
        Edge tmp = new Edge(0, 0, 0, 0, Color.WHITE);
        int tmpLength = 0;
        int start = 4;

        // Get length edge as byte array
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
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

        RandomAccessFile raf;

        try {
            raf = new RandomAccessFile(new File(file), "r");
            FileChannel fc = raf.getChannel();
            MappedByteBuffer mb1 = fc.map(FileChannel.MapMode.READ_ONLY, 0, 4);
            while (true) {
                int edgesWritten = mb1.get(0);
                if (lastWritten < edgesWritten) {
                    FileLock lock = null;
                    MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, lastWritten * tmpLength, (edgesWritten - lastWritten) * tmpLength);
                    for (int i = 0; i <= raf.length() - tmpLength; i += tmpLength) {
                        lock = fc.lock(lastWritten * tmpLength, (edgesWritten - lastWritten) * tmpLength, true);
                        byte[] edgeBytes = new byte[tmpLength];
                        mbb.get(edgeBytes, 0, tmpLength);
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(edgeBytes));
                        Edge e = (Edge) ois.readObject();
                        e.convertColor();
                        manager.edges.add(e);
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                manager.kffx.setTextLevel("Level: " + level + "");
                manager.kffx.setTextNrEdges(amount + "");
                manager.drawEdges();
            }
        });
    }

}
