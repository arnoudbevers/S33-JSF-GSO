package calculate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.paint.Color;
import timeutil.TimeStamp;

public class KochManager {

	private final static Logger LOG = Logger.getLogger(KochManager.class.getName());

	private volatile List<Edge> edges;
	public static final ExecutorService pool = Executors.newFixedThreadPool(4);
	private String outputFile;
	private RandomAccessFile raf;
	private FileChannel fc;
	private TimeStamp ts = new TimeStamp();

	public int edgeCount = 0;

	public KochManager(String outputFile) {
		this.edges = new ArrayList<Edge>();
		this.outputFile = outputFile;

		try {
			raf = new RandomAccessFile(new File(outputFile), "rw");
			fc = raf.getChannel();
		} catch (FileNotFoundException ex) {
			LOG.log(Level.INFO, ex.getMessage(), ex);
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
		int amountEdges = (int) (3 * Math.pow(4, level - 1.0)) / 3;
		// Create temporary edge
		Edge tmp = new Edge(0, 0, 0, 0, Color.WHITE);
		long tmpLength = 0;

		// Get length edge as byte array
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
				oos.writeObject(tmp);
			} catch (IOException ex) {
				LOG.log(Level.INFO, ex.getMessage(), ex);
			}
			byte[] array = bos.toByteArray();
			tmpLength = array.length;
		} catch (IOException ex) {
			LOG.log(Level.INFO, ex.getMessage(), ex);
		}

		ts.init();
		ts.setBegin("Start Calcultating & Writing for level: " + level + "...");

		for (int i = 0; i < 3; i++) {
			// Create mapped byte buffer split in 3 sections
			// Each section for each kochtask
			// app me for explanation 06-21199476
			// MappedByteBuffer out = null;
			// try {
			// out = fc.map(MapMode.READ_WRITE, i * tmpLength * amountEdges,
			// tmpLength * amountEdges);
			// } catch (IOException ex) {
			// LOG.log(Level.INFO, ex.getMessage(), ex);
			// }

			// KochTask run = new KochTask(this, i, level, out);
			KochTask run = new KochTask(this, i, level, fc);
			tasks.add(pool.submit(run));
		}

		pool.execute(new Runnable() {
			public void run() {
				for (Future<List<Edge>> fut : tasks) {
					try {
						addEdges(fut.get());
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					} catch (ExecutionException ex) {
						LOG.log(Level.INFO, ex.getMessage(), ex);
					}
				}
				try {
					ts.setEnd("Ended Calculating & Writing for level: " + level + "...");
					System.out.println(ts.toString());
					raf.close();
					fc.close();

					System.exit(0);
				} catch (IOException ex) {
					LOG.log(Level.INFO, ex.getMessage(), ex);
				}
			}
		});
	}

	public void addEdgeCount() throws IOException {
		FileLock lock = fc.lock(0, 4, true);
		MappedByteBuffer map = fc.map(MapMode.READ_WRITE, 0, 4);

		synchronized (this) {
			edgeCount++;
			map.putInt(0, edgeCount);
		}

		lock.release();
		lock.close();
	}
}
