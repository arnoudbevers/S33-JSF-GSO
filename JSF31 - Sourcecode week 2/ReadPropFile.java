/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readpropfile;

import java.io.*;
import java.util.Properties;
/**
 *
 * @author jsf3
 */
public class ReadPropFile {

    
    public static void main(String[] args) {
        try{
            FileInputStream in = new FileInputStream("PropertyFile.properties");
            Properties prop = new Properties();
            prop.load(in);
            System.out.println(prop);
            in.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
        catch(IOException ex2) {
            
        }
        
    }
    
}
