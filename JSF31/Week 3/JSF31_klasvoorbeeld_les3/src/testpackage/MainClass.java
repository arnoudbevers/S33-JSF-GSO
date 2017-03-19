/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

/**
 *
 * @author marcel
 */
public class MainClass {
    public static void main(String[] args) {
        Runnable r1 = new MyRunnable();
        Runnable r2 = new MyRunnable();
        Runnable r3 = new MyRunnable();
        
        
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        
        t1.start();
        t2.start();
        t3.start();
    }
}
