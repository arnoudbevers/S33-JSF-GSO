/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsf3
 */
public class RunnableRuntime implements Runnable {
    
    public String cmd,arg;
    
    public RunnableRuntime(String cmd, String arg) {
        this.cmd = cmd;
        this.arg = arg;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd, arg);
            
            Process proc = pb.start();
            // obtain the input and output streams
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            br.close();
            proc.destroy();
        } catch (IOException ex) {
            Logger.getLogger(RunnableRuntime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
