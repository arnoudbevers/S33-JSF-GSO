package jsf32kochfractalfx;

import calculate.KochManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSF32KochFractal {

    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1090);
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                ServerSession serverSession = new ServerSession(clientSocket);
                pool.execute(serverSession);
            }
        } catch (IOException ioe) {
            Logger.getLogger(JSF32KochFractal.class.getName()).log(Level.SEVERE, null, ioe);
            System.out.println("IOException on creating serverSocket.");
                    
        }
    }
}
