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
//        if (args.length == 3 && args[0].equals("-l") && isInteger(args[1])) {
//            int level = Integer.parseInt(args[1]);
//            String outputFile = args[2];
//            KochManager manager = new KochManager(outputFile);
//            manager.calculateLevel(level);
//        }
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

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
