/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcel
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        for(;;) {
            System.out.println("Hello World!" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
