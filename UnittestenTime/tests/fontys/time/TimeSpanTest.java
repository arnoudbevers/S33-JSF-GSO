package fontys.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TimeSpanTest {
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
		
		Time bt3 = new Time(2017, 3, 5, 8, 23);
		Time et3 = new Time(2017, 3, 6, 17, 25);
		TimeSpan span3 = new TimeSpan(bt3, et3);
		
		TimeSpan ts2 = (TimeSpan) span1.unionWith(span3);
		assertNull(ts2);
		
		Time bt4 = new Time(2017, 3, 7, 8, 23);
		Time et4 = new Time(2017, 3, 12, 17, 25);
		TimeSpan span4 = new TimeSpan(bt4, et4);
		TimeSpan ts3 = (TimeSpan) span1.unionWith(span4);
		assertNotNull(ts3);
		
		Time bt5 = new Time(2017, 3, 25, 8, 23);
		Time et5 = new Time(2017, 3, 29, 17, 25);
		TimeSpan span5 = new TimeSpan(bt5, et5);
		TimeSpan ts4 = (TimeSpan) span1.unionWith(span5);
		assertNotNull(ts4);
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
		
		
		Time bt3 = new Time(2017, 3, 5, 8, 23);
		Time et3 = new Time(2017, 3, 6, 17, 25);
		TimeSpan span3 = new TimeSpan(bt3, et3);
		
		TimeSpan ts2 = (TimeSpan) span1.intersectionWith(span3);
		assertNull(ts2);
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
		
		Time bt3 = new Time(2017, 3, 5, 8, 23);
		Time et3 = new Time(2017, 3, 17, 17, 25);
		TimeSpan span3 = new TimeSpan(bt3, et3);
		
		assertTrue(span1.isPartOf(span2));
		assertFalse(span1.isPartOf(span3));
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
		assertEquals(22, span1.getBeginTime().getDay());
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
