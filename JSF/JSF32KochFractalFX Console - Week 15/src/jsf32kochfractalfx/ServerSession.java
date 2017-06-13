package jsf32kochfractalfx;

import calculate.Edge;
import calculate.KochManager;
import calculate.ZoomObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnoudbevers
 */
public class ServerSession implements Runnable {

    private InputStream inStream = null;
    private OutputStream outStream = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private boolean allEdges;
    
     //Canvas
    private final int kpWidth = 500;
    private final int kpHeight = 500;
    
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private Socket clientSocket;
    private int level;
    private List<Edge> edges;
    
    public ServerSession(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    public synchronized List<Edge> generateFractal(int level){
        KochManager manager = new KochManager();
        return manager.generateEdges(level);       
    }
    
    @Override
    public void run() {
        try{
            this.inStream = clientSocket.getInputStream();
            this.outStream = clientSocket.getOutputStream();
            
            this.in = new ObjectInputStream(inStream);
            this.out = new ObjectOutputStream(outStream);
            
            out.writeObject("Level: ");
            
            //Requesting the level from client
            Object inObject = in.readObject();
            System.out.println(inObject);
            
            //Setting the level
            level = (int)inObject;
            
            //Requesting whether all edges are to be read or one by one
            inObject = in.readObject();
            allEdges = (boolean)inObject;
            System.out.println("Edges sent how: " + allEdges);
            //Filling the list
            edges = generateFractal(level);
            
            //If all edges are to be sent in one time
            if(allEdges){
                out.writeObject(edges);
                System.out.println("All edges sent at once!");
            }else{
                //Else write one by one
                for(int i = 0; i < this.edges.size(); i++)
                {
                    out.writeObject(edges.get(i));
                    out.flush();
                }
                System.out.println("Edges sent one by one!");
            }
            System.out.println("Server edges count: " + edges.size());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerSession.class.getName()).log(Level.SEVERE, null, ex);
        }while(true)
        {
            try {
                Object inObject = in.readObject();

                System.out.println("Object received");
                ZoomObject zo = (ZoomObject)inObject;
                System.out.println(zo.getPrimary());

                Zoom(zo);

                this.edges = generateFractal(level);

                List<Edge> edgesAfterZoom = new ArrayList<>();
                for (Edge e : this.edges)
                {
                    e.convertColor();
                    edgesAfterZoom.add(edgeAfterZoomAndDrag(e));
                }

                edges = edgesAfterZoom;

                out.writeObject(edges);
                System.out.println("Edges sent (after zoom)");

            } catch (IOException ex) {
                Logger.getLogger(ServerSession.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoom = 1;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }
    
    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom,
                e.Y1 * zoom,
                e.X2 * zoom,
                e.Y2 * zoom,
                e.color);
    }
    
    private void Zoom(ZoomObject zo)
    {
        if (true) {//Math.abs(zo.getX() - startPressedX) < 1.0 && Math.abs(zo.getY() - startPressedY) < 1.0) {
            double originalPointClickedX = (zo.getX() - zoomTranslateX) / zoom;
            double originalPointClickedY = (zo.getY() - zoomTranslateY) / zoom;
            if (zo.getPrimary() == true) {
                zoom *= 2.0;
            } else {
                zoom /= 2.0;
            }
            zoomTranslateX = (int) (zo.getX() - originalPointClickedX * zoom);
            zoomTranslateY = (int) (zo.getY() - originalPointClickedY * zoom);
            
        }
    }
}
    
    

