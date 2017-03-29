/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.List;

/**
 *
 * @author arnienoob
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
	 * Adds a contact to the Appointment but only if the contact has no intervering appointments
	 * @param Contact 
	 * @return boolean if the addition was completed succesfully
	 */
	public boolean addContact(Contact c) {
		boolean noConflict = true;
		for(Appointment a : c.appointments) {
			noConflict = a.getTimeSpan().intersectionWith(getTimeSpan()) != null;
			if(!noConflict) break;
		}
		if(noConflict) {
			this.invitees.add(c);
		}
		return noConflict;
	}
	
	public void removeContact(Contact c) {
		this.invitees.remove(c);
	}
}
