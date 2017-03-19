/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fillcmdline;

import java.io.*;
import java.util.Properties;
/**
 *
 * @author jsf3
 */
public class FillCmdLine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Properties prop = new Properties();
        for(int i = 0; i < args.length; i+=2) {
            String name = args[i];
            String value = args[i+1];
            prop.setProperty(name, value);
        }
        try{
            FileOutputStream out = new FileOutputStream("PropertyFile2.properties");
            prop.store(out, null);
            System.out.println("Writing completed");
            out.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("The file was not found.");
        }
        catch(IOException exc){
            System.out.println("Something went wrong with writing the file.");
        }
    }
    
}
