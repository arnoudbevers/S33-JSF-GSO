package fontys.time;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

	@Test
	public void testSuccessfulCreateTime() {
		/**
		 * creation of a Time-object with year y, month m, day d, hours h and
		 * minutes m; if the combination of y-m-d refers to a non-existing date
		 * a correct value of this Time-object will be not guaranteed
		 * 
		 * @param y
		 * @param m
		 *            1≤m≤12
		 * @param d
		 *            1≤d≤31
		 * @param h
		 *            0≤h≤23
		 * @param m
		 *            0≤m≤59
		 */
		Time time1 = new Time(2017, 3, 14, 14, 22);
		assertEquals(2017, time1.getYear());
		assertEquals(3, time1.getMonth());
		assertEquals(14, time1.getDay());
		assertEquals(14, time1.getHours());
		assertEquals(22, time1.getMinutes());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testErrorMonthCreateTime() {
		Time time1 = new Time(2017, 13, 31, 14, 27);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorDayCreateTime() {
		Time time1 = new Time(2017, 12, 33, 14, 27);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorHourCreateTime() {
		Time time1 = new Time(2017, 12, 31, 25, 27);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorMinutesCreateTime() {
		Time time1 = new Time(2017, 12, 31, 14, 61);
	}
	
	@Test
	public void testGettingDay(){
		Time monday = new Time(2017, 3, 20, 15, 12);
		Time tuesday = new Time(2017, 3, 21, 15, 12);
		Time wednesday = new Time(2017, 3, 22, 15, 12);
		Time thursday = new Time(2017, 3, 23, 15, 12);
		Time friday = new Time(2017, 3, 24, 15, 12);
		Time saturday = new Time(2017, 3, 25, 15, 12);
		Time sunday = new Time(2017, 3, 26, 15, 12);
		
		assertEquals(monday.getDayInWeek(), DayInWeek.MON);
		assertEquals(tuesday.getDayInWeek(), DayInWeek.TUE);
		assertEquals(wednesday.getDayInWeek(), DayInWeek.WED);
		assertEquals(thursday.getDayInWeek(), DayInWeek.THU);
		assertEquals(friday.getDayInWeek(), DayInWeek.FRI);
		assertEquals(saturday.getDayInWeek(), DayInWeek.SAT);
		assertEquals(sunday.getDayInWeek(), DayInWeek.SUN);
	
	}
	
	@Test
	public void testTimePlus(){
		Time time = new Time(2017, 3, 20, 15, 12);
		time = (Time) time.plus(10);
		
		Time time2 = new Time(2017, 3, 20, 15, 22);
		
		assertEquals(0, time.compareTo(time2));
	}
	
	@Test
	public void testTimeDifference(){
		Time time = new Time(2017, 3, 20, 15, 12);
		
		Time time2 = new Time(2017, 3, 20, 15, 22);
		
		assertEquals(-10, time.difference(time2));
	}
	
	@Test
	public void testTimeToString(){
		Time time = new Time(2017, 3, 20, 15, 12);
		
		assertEquals("20-3-2017, 15:12", time.toString());
	}
}
