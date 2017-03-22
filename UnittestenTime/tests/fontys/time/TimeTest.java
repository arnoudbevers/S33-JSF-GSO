package fontys.time;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

	
	@Test
	public void testCreateTime() {
		/**
	     * creation of a Time-object with year y, month m, day d, hours h and
	     * minutes m; if the combination of y-m-d refers to a non-existing date 
	     * a correct value of this Time-object will be not guaranteed 
	     * @param y 
	     * @param m 1≤m≤12
	     * @param d 1≤d≤31
	     * @param h 0≤h≤23
	     * @param m 0≤m≤59
	     */
		Time time1 = new Time(2017, 3, 14, 14, 22);
		assertEquals(2017, time1.getYear());
		assertEquals(3, time1.getMonth());
		assertEquals(14, time1.getDay());
		assertEquals(14, time1.getHours());
		assertEquals(22, time1.getMinutes());
		
		try{
			Time time2 = new Time(2017, 13, 33, 14, 27);
			fail();
		}catch (IllegalArgumentException ex){
		}
	}
}
