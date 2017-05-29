package calculate;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import timeutil.TimeStamp;

public class KochManager {

    private volatile List<Edge> edges;
    public int count = 0;
    public ExecutorService pool;
    private String outputFile;
    private ObjectOutputStream ooStream;
    private BufferedWriter out;
    private FileWriter fw;
    private TimeStamp tsWrite;

    public KochManager(String outputFile) {
        this.edges = new ArrayList<Edge>();
        this.pool = Executors.newFixedThreadPool(4);
        this.outputFile = outputFile;
        tsWrite = new TimeStamp();
        try {
            fw = new FileWriter(outputFile);
            //out = new BufferedWriter(fw);

            //OutputStream oStream = new FileOutputStream(outputFile);
            //BufferedOutputStream boStream = new BufferedOutputStream(oStream);
            //this.ooStream = new ObjectOutputStream(boStream);
        } catch (IOException e) {
            LOG.warning(e.toString());
        }
    }

    private static final Logger LOG = Logger.getLogger(KochManager.class.getName());

    public void addEdges(List<Edge> es) {
        for (Edge e : es) {
            edges.add(e);
        }
    }

    public void calculateLevel(int level) {
        List<Future<List<Edge>>> tasks = new ArrayList<Future<List<Edge>>>();
        tsWrite.init();
        tsWrite.setBegin("Begin writing file");
        System.out.println("I am writing...");
        for (int i = 0; i < 3; i++) {
            //KochTask run = new KochTask(i, level, ooStream);
            KochTask run = new KochTask(i, level, fw);
            tasks.add(pool.submit(run));
        }

        pool.execute(new Runnable() {
            public void run() {
                for (Future<List<Edge>> fut : tasks) {
                    try {
                        addEdges(fut.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                try {
//                    ooStream.flush();
//                    ooStream.close();
//                    out.flush();
//                    out.close();
                    fw.flush();
                    fw.close();
                    tsWrite.setEnd("End writing file");
                    System.out.println("I am done writing...");
                    System.out.println("Writing for level " + level + ": " + tsWrite.toString());
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
