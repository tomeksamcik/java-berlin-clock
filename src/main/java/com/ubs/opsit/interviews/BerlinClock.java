package com.ubs.opsit.interviews;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * Class representing Berlin Clock
 * 
 * @author Tomek Samcik
 *
 */
public class BerlinClock implements Clock {
	
	/**
	 * Class representing a single lamp
	 * 
	 * @author Tomek Samcik
	 *
	 */
	private static enum Lamp {
		YELLOW('Y'),
		RED('R'),
		OFF('O');
		
		private char value;
		
		private Lamp(char value) {
			this.value = value;
		}
		
	}
	
	/*
	 * Internal clock representation 
	 */
	private Lamp top = Lamp.OFF;	
	
	private Lamp[] topHours = {Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF};
	
	private Lamp[] bottomHours = {Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF};
	
	private Lamp[] topMinutes = {Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF};
	
	private Lamp[] bottomMinutes = {Lamp.OFF, Lamp.OFF, Lamp.OFF, Lamp.OFF};

	@Override
	public void setTime(LocalTime time) {
		top = parseSeconds(time.getSecond());
		topHours = parseTopHours(time.getHour());
		bottomHours = parseBottomHours(time.getHour());
		topMinutes = parseTopMinutes(time.getMinute());
		bottomMinutes = parseBottomMinutes(time.getMinute());
	}

	@Override
	public void setTime(int hour, int minute, int second) {
		top = parseSeconds(second);
		topHours = parseTopHours(hour);
		bottomHours = parseBottomHours(hour);
		topMinutes = parseTopMinutes(minute);
		bottomMinutes = parseBottomMinutes(minute);
	}
	
	@Override
	public String render() {
		final String newLine = System.getProperty("line.separator"); 
		StringBuilder sb = new StringBuilder();
		sb.append(top.value);
		for (Lamp[] arr : new Lamp[][] {topHours, bottomHours, topMinutes, bottomMinutes}) {
			sb.append(newLine);
			for (Lamp lamp : arr) {
				sb.append(lamp.value);
			}
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "BerlinClock [top=" + top + ", topHours="
				+ Arrays.toString(topHours) + ", bottomHours="
				+ Arrays.toString(bottomHours) + ", topMinutes="
				+ Arrays.toString(topMinutes) + ", bottomMinutes="
				+ Arrays.toString(bottomMinutes) + "]";
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
		return lamps(topHours.length, 
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
		return lamps(bottomHours.length, 
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
		return lamps(topMinutes.length, 
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
		return lamps(bottomMinutes.length, 
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
	 * lit with the corresponding colour and the rest turned off
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
							(i + 1) % 3 == 0)) {
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
	
}
