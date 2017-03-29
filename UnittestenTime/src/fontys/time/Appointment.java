/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author bramkut
 */
public class Appointment {
    
	private String subject;
	private ITimeSpan timespan;
	private List<Contact> invitees;

	/**
	 * Create an appointment with a subject and a timespan
	 * @param subject
	 * @param timespan
	 */
	public Appointment(String subject, ITimeSpan timespan) {
		this.subject = subject;
		this.timespan = timespan;
		this.invitees = new ArrayList<Contact>();
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public ITimeSpan getTimeSpan() {
		return this.timespan;
	}
	
	public List<Contact> getInvitees() {
		return this.invitees;
	}

	/**
	 * Adds a contact to the Appointment but only if the contact has no intervening appointments
	 * @param Contact cannot be null
	 * @return boolean if the addition was completed successfully
	 */
	public boolean addContact(Contact c) {
		if(c == null) {
			throw new IllegalArgumentException("Contact cannot be null");
		}
		boolean noConflict = true;
<<<<<<< HEAD
		while(c.appointments().hasNext()) {
			Appointment a = c.appointments().next();
			noConflict = a.getTimeSpan().intersectionWith(getTimeSpan()) != null;
=======
		Iterator<Appointment> it = c.appointments();
		while(it.hasNext()) {
			Appointment a = it.next();
			noConflict = a.getTimeSpan().intersectionWith(getTimeSpan()) == null;
>>>>>>> 7e28e81349c503448b0ae59cb51bd7057026426f
			if(!noConflict) break;
		}
		if(noConflict) {
			this.invitees.add(c);
		}
		return noConflict;
	}
	
	public boolean removeContact(Contact c) {
		return this.invitees.remove(c);
	}
}
