/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.List;

/**
 *
 * @author arnoudbevers
 */
public class Contact {
    private String name;
    public List<Appointment> appointments;
    
    public void Contact(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    protected boolean addAppointment(Appointment a){
        return false;
    }
}   
