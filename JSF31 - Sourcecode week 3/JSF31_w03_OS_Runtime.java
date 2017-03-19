/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.io.*;

/**
 *
 * @author jsf3
 */
public class JSF31_w03_OS_Runtime {

    static TimeStamp ts = new TimeStamp();
    static Runtime run = Runtime.getRuntime();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ts.setBegin("Begin van de meting");
        //testMem();
        //runProcesses();
        for(int i = 0; i < args.length; i+=2){
            Thread t = new Thread(new RunnableRuntime(args[i],args[i+1]));
            t.start();
            System.out.println("Ik ben bezig");
        }
        Thread.sleep(10000);
        ts.setEnd("Einde van de meting");
        System.out.print(ts.toString());
    }
    
    public static void runCommands(String cmd, String arg) throws InterruptedException, IOException{
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
        
//        Process exec = run.exec("ls");
//        // obtain the input and output streams
//        is = exec.getInputStream();
//        isr = new InputStreamReader(is);
//        br = new BufferedReader(isr);
//
//        String line1;
//        while((line1 = br.readLine()) != null){
//            System.out.println(line1);
//        }
//        br.close();
//
//        Thread.sleep(5000);
//        exec.destroy();
    }
    
    public static void runProcesses() throws IOException, InterruptedException{
        ProcessBuilder pb = new ProcessBuilder("gnome-calculator");
        
        Process proc = pb.start();
        Thread.sleep(5000);
        proc.destroy();
        
        Process exec = run.exec("gnome-calculator");
        Thread.sleep(5000);
        exec.destroy();
    }
    
    public static void printMem(){
        System.out.println("Aantal beschikbare processors: " + run.availableProcessors());
        System.out.println("Totaal aantal beschikbaar geheugen van het proces:" + run.totalMemory() / 1000 + "KB");
        System.out.println("Maximaal beschikbaar geheugen: " + run.maxMemory() / 1000 + "KB");
        System.out.println("Vrij geheugen dat aan een proces besteed kan worden: " + run.freeMemory() / 1000 + "KB");
        System.out.println("Gebruikt geheugen: " + (run.totalMemory() - run.freeMemory()) / 1000 + "KB");
    }
    
    public static void testMem(){
        System.out.println("-- Eerste status --");
        printMem();
        String s;
        for(int i=0; i<100000; i ++){
            s = "Hello" + i;
        }
        System.out.println("-- Status voor garbage collection --");
        printMem();
        System.out.println("-- Status na garbage collection --");
        run.gc();
        printMem();
    }
}
