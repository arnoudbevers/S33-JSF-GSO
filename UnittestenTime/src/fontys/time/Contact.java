/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.*;

/**
 *
 * @author arnoudbevers
 */
public class Contact {

    private String name;
    private List<Appointment> agenda;

    /**
     * Initializes the Contact class
     *
     * @param name the name of the contact
     */
    public Contact(String name) {
        this.name = name;
        this.agenda = new ArrayList<Appointment>();
    }

    /**
     * Gets the name of the contact
     *
     * @return name of contact
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds appointment to the agenda, considering there is no overlap with
     * existing appointments in the agenda
     *
     * @param a the appointment to be added to the agenda, considering it is not
     * null
     * @return boolean that represents the result of the method (true if
     * success, false if failed)
     */
    boolean addAppointment(Appointment a) {
        if (a == null) {
            throw new IllegalArgumentException("Appointment can't be null!");
        }
        boolean isConflict = false;
        for (Appointment ap : this.agenda) {
            if (isConflict = ap.getTimeSpan().intersectionWith(a.getTimeSpan()) != null) {
                return false;
            }
        }
        this.agenda.add(a);
        return true;
    }

    /**
     * Removes an appointment from the agenda
     *
     * @param a appointment to be added to the agenda, considering it is not
     * null
     * @return boolean that represents the result of the method (true if
     * success, false if failed)
     */
    protected boolean removeAppointment(Appointment a) {
        if (a == null) {
            throw new IllegalArgumentException("Appointment can't be null!");
        }
        if (!agenda.contains(a)) {
            return false;
        }

        this.agenda.remove(a);
        return true;

    }

    /**
     * Gets the agenda
     *
     * @return agenda in the form of an iterator
     */
    public Iterator<Appointment> appointments() {
        return this.agenda.iterator();
    }

}
