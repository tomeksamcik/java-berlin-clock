package com.ubs.opsit.interviews;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ubs.opsit.interviews.BerlinClock.Lamp;

/**
 * @author Tomek Samcik
 *
 */
public class BerlinClockTimeConverter implements TimeConverter {
	
	private static final Logger LOG = LoggerFactory.getLogger(BerlinClockTimeConverter.class);	
	
	private BerlinClock clock;	
	
	/**
	 * Constructing converter
	 * 
	 * @param clock clock instance
	 */
	@Inject
	public BerlinClockTimeConverter(BerlinClock clock) {
		this.clock = clock;
	}	

	@Override
	public String convertTime(String aTime) {		
		setClockTime(aTime);		
		
		LOG.debug(clock.toString());
		
		return clock.render();		
	}
	
	/**
	 * Sets clock time, handles special case of aTime=24:00:00 
	 * that is not allowed in ISO time format and would otherwise
	 * get converted to 00:00:00 (in SMART/LENIENT mode) or throw
	 * an exception (in STRICT mode)
	 * 
	 * @param aTime time string
	 */
	private void setClockTime(String aTime) {
		if (!aTime.equals("24:00:00")) {
			LocalTime time = LocalTime.parse(aTime, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.STRICT));
			
			setTime(time.getHour(), time.getMinute(), time.getSecond());
		} else {
			/*
			 * Work around to enforce passing 24:00:00 to clock instance
			 */
			setTime(24, 0, 0);
		}
	}
	
	/**
	 * Sets clock's current time
	 * 
	 * @param hour hour to be set
	 * @param minute minute to be set
	 * @param second second to be set
	 */
	public void setTime(int hour, int minute, int second) {
		clock.setTop(parseSeconds(second));
		clock.setTopHours(parseTopHours(hour));
		clock.setBottomHours(parseBottomHours(hour));
		clock.setTopMinutes(parseTopMinutes(minute));
		clock.setBottomMinutes(parseBottomMinutes(minute));
	}
	
	/**
	 * Setting top seconds lamp
	 * 
	 * @param second number of seconds
	 * @return top lamp
	 */
	private Lamp parseSeconds(int second) {
		if (second % 2 == 0) {
			return Lamp.YELLOW;
		} else {
			return Lamp.OFF;
		}
	}

	/**
	 * Setting top hours lamps
	 * 
	 * @param hour number of hours
	 * @return top hours lamps
	 */
	private Lamp[] parseTopHours(int hour) {
		boolean higherOrder = true;
		boolean minutes = false;
		return lamps(clock.getTopHours().length, 
				getNumberOfLitLamps(hour, higherOrder), 
				minutes,
				higherOrder);
	}

	/**
	 * Setting bottom hours lamps
	 * 
	 * @param hour number of hours
	 * @return bottom hours lamps
	 */
	private Lamp[] parseBottomHours(int hour) {
		boolean higherOrder = false;
		boolean minutes = false;
		return lamps(clock.getBottomHours().length, 
				getNumberOfLitLamps(hour, higherOrder), 
				minutes,
				higherOrder);
	}

	/**
	 * Setting top minutes lamps
	 * 
	 * @param minute number of minutes
	 * @return top minutes lamps
	 */
	private Lamp[] parseTopMinutes(int minute) {
		boolean higherOrder = true;
		boolean minutes = true;
		return lamps(clock.getTopMinutes().length, 
				getNumberOfLitLamps(minute, higherOrder), 
				minutes,
				higherOrder);
	}

	/**
	 * Setting bottom minutes lamps
	 * 
	 * @param minute number of minutes
	 * @return bottom minutes lamps
	 */
	private Lamp[] parseBottomMinutes(int minute) {
		boolean higherOrder = false;
		boolean minutes = true;
		return lamps(clock.getBottomMinutes().length, 
				getNumberOfLitLamps(minute, higherOrder), 
				minutes,
				higherOrder);
	}
	
	/**
	 * Gets the number of lit lamps
	 * 
	 * @param number input value of seconds/minutes
	 * @param higherOrder true if top row, false if bottom row
	 * @return number of lit lamps in the given row
	 */
	private int getNumberOfLitLamps(int number, boolean higherOrder) {
		if (higherOrder) {
			return (number - (number % 5)) / 5;
		} else { 
			return number % 5;
		}
	}
	
	/**
	 * Returns an array of lamps in a row with the given number of lamps 
	 * lit with the corresponding color and the rest turned off
	 * 
	 * @param length length of an array of lamps
	 * @param numberOfLitLamps number of lit lamps
	 * @param minutes indicates if the corresponding array represents hours or minutes
	 * @param higherOrder indicates if the corresponding array represents top or bottom row
	 * @return an array of lamps for the given row
	 */
	private Lamp[] lamps(int length, int numberOfLitLamps, boolean minutes, boolean higherOrder) {
		Lamp[] lamps = new Lamp[length];
		for (int i = 0; i < length; i++) {
			if (i < numberOfLitLamps) {
				if (!minutes ||
						(minutes &&
							higherOrder &&
							isQuarter(i))) {
					lamps[i] = Lamp.RED;
				} else {
					lamps[i] = Lamp.YELLOW;
				}
			} else {
				lamps[i] = Lamp.OFF;
			}
		}
		return lamps;
	}
	
	/**
	 * Indicates if a higher order minute lamp denotes 15, 30 or 45 minutes
	 * 
	 * @param minute position of a higher order minute lamp
	 * @return true if lamp denotes 15, 30, 45 minutes, false otherwise 
	 */
	private boolean isQuarter(int minute) {
		return (minute + 1) % 3 == 0;		
	}

}
