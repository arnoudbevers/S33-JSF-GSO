package readenvirvar;

import java.io.*;
import java.util.Properties;

public class ReadEnvirVar{
  
    public static void main(String[] args){
        String envVar = System.getenv("TestEnviron");
        Properties prop = new Properties();
        prop.setProperty("TestEnviron", envVar);
        try{
            FileOutputStream out = new FileOutputStream("PropertyFile.properties");
            prop.store(out, envVar);
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