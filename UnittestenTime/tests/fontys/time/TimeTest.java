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
	
	@Test
	public void testTimeSpanCreation() {
		/**
	     * 
	     * @param bt must be earlier than et
	     * @param et 
	     */
		try {
			Time bt = new Time(2017, 3, 25, 17, 25);
			Time et = new Time(2017, 3, 8, 8, 23);
			TimeSpan span = new TimeSpan(bt, et);
			fail("et can be later than bt");
		} catch(IllegalArgumentException e) {
			
		}
		
		try {
			Time et = new Time(2017, 3, 8, 8, 23);
			TimeSpan span = new TimeSpan(et, et);
			fail("et can be same than bt");
		} catch(IllegalArgumentException e) {
			
		}
		
		Time bt = new Time(2017, 3, 8, 8, 23);
		Time et = new Time(2017, 3, 25, 17, 25);
		TimeSpan span = new TimeSpan(bt, et);
		assertEquals(bt, span.getBeginTime());
		assertEquals(et, span.getEndTime());
	}
	
	@Test
	public void testTimeSpanUnion() {
		/**
	     * 
	     * @param timeSpan
	     * @return if this time span and [timeSpan] are consecutive or possess a
	     * common intersection, then the smallest time span ts will be returned, 
	     * whereby this time span and [timeSpan] are part of ts, 
	     * otherwise null will be returned 
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time bt2 = new Time(2017, 3, 12, 8, 23);
		Time et2 = new Time(2017, 3, 28, 17, 25);
		TimeSpan span2 = new TimeSpan(bt2, et2);
		
		TimeSpan ts = (TimeSpan) span1.unionWith(span2);
		assertNotNull(ts);
		assertEquals(8, ts.getBeginTime().getDay());
		assertEquals(28, ts.getEndTime().getDay());
	}
	
	@Test
	public void testTimeSpanIntersect() {
		/**
	     * 
	     * @param timeSpan
	     * @return if this time span and [timeSpan] are consecutive or possess a
	     * common intersection, then the smallest time span ts will be returned, 
	     * whereby this time span and [timeSpan] are part of ts, 
	     * otherwise null will be returned 
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time bt2 = new Time(2017, 3, 12, 8, 23);
		Time et2 = new Time(2017, 3, 28, 17, 25);
		TimeSpan span2 = new TimeSpan(bt2, et2);
		
		TimeSpan ts = (TimeSpan) span1.intersectionWith(span2);
		assertNotNull(ts);
		assertEquals(12, ts.getBeginTime().getDay());
		assertEquals(25, ts.getEndTime().getDay());
	}
}
