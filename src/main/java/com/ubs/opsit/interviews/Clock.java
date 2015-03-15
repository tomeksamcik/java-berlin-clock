package com.ubs.opsit.interviews;

import java.time.LocalTime;

/**
 * @author Tomek Samcik
 *
 */
public interface Clock {
	
	/**
	 * Sets clock's current time
	 * 
	 * @param time time to be set
	 */
	void setTime(LocalTime time);
	
	/**
	 * Sets clock's current time
	 * 
	 * @param hour hour to be set
	 * @param minute minute to be set
	 * @param second second to be set
	 */
	void setTime(int hour, int minute, int second);
	
	/**
	 * Renders time in clock's format
	 * 
	 * @return representation of clock's display
	 */
	String render();

}
