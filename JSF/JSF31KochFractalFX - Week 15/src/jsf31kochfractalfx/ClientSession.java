package jsf31kochfractalfx;

import calculate.Edge;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arnoudbevers
 */
class ClientSession {

    JSF31KochFractalFX kffx;
    List<Edge> edges;

    private InputStream inStream = null;
    private OutputStream outStream = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //Socket
    Socket socket = null;

    private boolean allEdges;

    public ClientSession(JSF31KochFractalFX kffx) {
        try {
            this.kffx = kffx;
            socket = new Socket("localhost", 1090);

            this.outStream = socket.getOutputStream();
            this.inStream = socket.getInputStream();

            this.out = new ObjectOutputStream(outStream);
            out.flush();
            this.in = new ObjectInputStream(inStream);

            Object inObject = in.readObject();
            System.out.println(inObject);

            Scanner scanner = new Scanner(System.in);
            String levelString = scanner.nextLine();
            int level = 1;
            
            try {
                level = Integer.parseInt(levelString);
                if (level < 1) {
                    level = 1;
                } else if (level >= 10) {
                    level = 10;
                }
                System.out.println(level);
            } catch (Exception ex) {
                System.out.println("No legal level filled in. Level set to 1!");
                Logger.getLogger(ClientSession.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Prompt user for way of reading
            System.out.println("[A]ll edges at once or [S]ingle edges?");
            String orderString = scanner.nextLine();

            if (orderString.trim().toLowerCase().equals("a")) {
                allEdges = true;
            } else {
                allEdges = false;
            }

            //Tell client level
            out.writeObject(level);
            out.flush();
            //Tell client way of reading
            out.writeObject(allEdges);
            out.flush();

            //All edges at once
            if (allEdges) {
                edges = (List<Edge>) in.readObject();
                System.out.println("All edges received at once!");
                for (Edge e : edges) {
                    e.convertColor();
                    kffx.drawEdge(e);
                    System.out.println(e);
                }
            } //One by one
            else {
                edges = new ArrayList<>();
                for (int i = 0; i < 3 * Math.pow(4, level - 1); i++) {
                    Edge e = (Edge) in.readObject();
                    edges.add(e);
                    e.convertColor();
                    kffx.drawEdge(e);
                }
                System.out.println("Edges received one by one!");
            }
            out.close();
            in.close();
            outStream.close();
            inStream.close();
            System.out.println("Client edges count: " + edges.size());

        } catch (Exception ex) {
            Logger.getLogger(ClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
