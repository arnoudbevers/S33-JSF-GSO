package fontys.time;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppointmentTest {
	@Test
	public void testAppointmentCreation() {
		/**
		 * Create an appointment with a subject and a timespan
		 * @param subject
		 * @param timespan
		 */
		Time bt = new Time(2017, 3, 8, 8, 23);
		Time et = new Time(2017, 3, 25, 17, 25);
		TimeSpan span = new TimeSpan(bt, et);
		Appointment ap = new Appointment("Test Appointment", span);
		
		assertEquals("Test Appointment", ap.getSubject());
		assertSame(span, ap.getTimeSpan());
	}
	
	@Test
	public void testAppointmentAddContact() {
		/**
		 * Adds a contact to the Appointment but only if the contact has no intervering appointments
		 * @param Contact 
		 * @return boolean if the addition was completed succesfully
		 */
		Time bt = new Time(2017, 3, 8, 8, 23);
		Time et = new Time(2017, 3, 25, 17, 25);
		TimeSpan span = new TimeSpan(bt, et);
		Appointment ap = new Appointment("Test Appointment", span);
	}
}
