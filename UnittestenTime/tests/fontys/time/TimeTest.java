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
	     * @return the largest time span which is part of this time span 
	     * and [timeSpan] will be returned; if the intersection is empty null will 
	     * be returned
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
	
	@Test
	public void testTimeSpanIsPartOf() {
		/**
	     * 
	     * @param timeSpan
	     * @return true if all moments within this time span are included within [timeSpan], 
	     * otherwise false
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time bt2 = new Time(2017, 3, 12, 8, 23);
		Time et2 = new Time(2017, 3, 17, 17, 25);
		TimeSpan span2 = new TimeSpan(bt2, et2);
		
		
		assertTrue(span1.isPartOf(span2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTimeSpanChangeLengthException() {
		/**
	     * the end time of this time span will be moved up with [minutes] minutes
	     * @param minutes  minutes + length of this period must be positive  
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		span1.changeLengthWith(-1);
	}
	
	@Test
	public void testTimeSpanChangeLength() {
		/**
	     * the end time of this time span will be moved up with [minutes] minutes
	     * @param minutes  minutes + length of this period must be positive  
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		span1.changeLengthWith(10);
		assertEquals(35, span1.getEndTime().getMinutes());
	}
	
	@Test
	public void testTimeSpanMove() {
		/**
	     * the begin and end time of this time span will be moved up both with [minutes]
	     * minutes
	     * @param minutes (a negative value is allowed)
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		span1.move(10);
		assertEquals(33, span1.getBeginTime().getMinutes());
		assertEquals(35, span1.getEndTime().getMinutes());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTimeSpanSetBeginTimeException() {
		/**
	     * beginTime will be the new begin time of this time span
	     * @param beginTime must be earlier than the current end time
	     * of this time span
	     */
		
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time bt2 = new Time(2017, 3, 26, 8, 23);
		
		span1.setBeginTime(bt2);
	}
	
	@Test
	public void testTimeSpanSetBeginTime() {
		/**
	     * beginTime will be the new begin time of this time span
	     * @param beginTime must be earlier than the current end time
	     * of this time span
	     */
		
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time bt2 = new Time(2017, 3, 22, 8, 23);
		
		span1.setBeginTime(bt2);
		assertEquals(25, span1.getBeginTime().getDay());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTimeSpanSetEndTimeException() {
		/**
	     * endTime will be the new end time of this time span
	     * @param endTime must be later than the current begin time
	     * of this time span
	     */
		
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time et2 = new Time(2017, 3, 7, 8, 23);
		
		span1.setEndTime(et2);
	}
	
	@Test
	public void testTimeSpanSetEndTime() {
		/**
	     * endTime will be the new end time of this time span
	     * @param endTime must be later than the current begin time
	     * of this time span
	     */
		
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 25, 17, 25);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		Time et2 = new Time(2017, 3, 12, 8, 23);
		
		span1.setEndTime(et2);
		assertEquals(12, span1.getEndTime().getDay());
	}
	
	@Test
	public void testTimeSpanLength() {
		/**
	     * 
	     * @return the length of this time span expressed in minutes (always positive)
	     */
		Time bt1 = new Time(2017, 3, 8, 8, 23);
		Time et1 = new Time(2017, 3, 8, 8, 35);
		TimeSpan span1 = new TimeSpan(bt1, et1);
		
		assertEquals(12, span1.length());
	}
}
