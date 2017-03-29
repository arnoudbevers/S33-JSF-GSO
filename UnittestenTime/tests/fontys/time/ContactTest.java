package fontys.time;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Test;

/**
 *
 * @author arnoudbevers
 */
public class ContactTest {

    Contact contact;

    @Before
    public void setUp() {
        contact = new Contact("Karel Ankerboef");
    }

    @Test
    public void TestContactCreation() {
        contact = new Contact("Arie");
        assertEquals(contact.getName(), "Arie");
        assertNotNull(contact);
    }
    
    @Test
    public void TestSuccessfulAddAppointment() {
        Time bt1 = new Time(2017, 04, 22, 16, 30);
        Time et1 = new Time(2017, 04, 22, 17, 00);
        Time bt2 = new Time(2017, 05, 22, 16, 30);
        Time et2 = new Time(2017, 05, 22, 17, 00);
        Appointment a1 = new Appointment("Afspraak 1", new TimeSpan(bt1, et1));
        contact.addAppointment(a1);
        Appointment a2 = new Appointment("Afspraak 2", new TimeSpan(bt2, et2));
        contact.addAppointment(a2);
    }
    @Test
    public void TestFailedAddAppointment(){
        Time bt1 = new Time(2017, 04, 22, 16, 30);
        Time et1 = new Time(2017, 04, 22, 17, 00);
        Time bt2 = new Time(2017, 04, 22, 16, 35);
        Time et2 = new Time(2017, 05, 22, 17, 20); 
        Appointment a1 = new Appointment("Afspraak 1", new TimeSpan(bt1, et1));
        contact.addAppointment(a1);
        Appointment a2 = new Appointment("Afspraak 2", new TimeSpan(bt2, et2));
        contact.addAppointment(a2);
        assertEquals(contact.addAppointment(a2), false);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void TestFailedAddAppointmentNull(){
        Appointment a1 = null;
        contact.addAppointment(a1);
    }
    
    

}
