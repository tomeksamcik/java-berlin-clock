package com.ubs.opsit.interviews;

import java.util.Arrays;

/**
 * Class representing Berlin Clock
 * 
 * @author Tomek Samcik
 *
 */
public class BerlinClock {
	
	/**
	 * Class representing a single lamp
	 * 
	 * @author Tomek Samcik
	 *
	 */
	static enum Lamp {
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
	
	/**
	 * Sets top seconds lamp
	 * 
	 * @param top lamp
	 */
	public void setTop(Lamp top) {
		this.top = top;
	}

	/**
	 * Sets top hours lamps
	 * 
	 * @param topHours lamps row
	 */
	public void setTopHours(Lamp[] topHours) {
		this.topHours = topHours;
	}

	/**
	 * Sets bottom hours lamps
	 * 
	 * @param bottomHours lamps row
	 */
	public void setBottomHours(Lamp[] bottomHours) {
		this.bottomHours = bottomHours;
	}

	/**
	 * Sets top minutes lamps
	 * 
	 * @param topMinutes lamps row
	 */
	public void setTopMinutes(Lamp[] topMinutes) {
		this.topMinutes = topMinutes;
	}

	/**
	 * Sets bottom minutes lamps
	 * 
	 * @param bottomMinutes lamps row
	 */
	public void setBottomMinutes(Lamp[] bottomMinutes) {
		this.bottomMinutes = bottomMinutes;
	}

	/**
	 * Gets top lamp
	 * 
	 * @return top lamp
	 */
	public Lamp getTop() {
		return top;
	}

	/**
	 * Gets top hours lamps
	 * @return top hours lamps
	 */
	public Lamp[] getTopHours() {
		return topHours;
	}

	/**
	 * Gets bottom hours lamps
	 * @return bottom hours lamps
	 */
	public Lamp[] getBottomHours() {
		return bottomHours;
	}

	/**
	 * Gets top minutes lamps
	 * 
	 * @return top minutes lamps
	 */
	public Lamp[] getTopMinutes() {
		return topMinutes;
	}

	/**
	 * Gets bottom minutes lamps
	 * 
	 * @return bottom minutes lamps
	 */
	public Lamp[] getBottomMinutes() {
		return bottomMinutes;
	}

	/**
	 * Renders time in clock's format
	 * 
	 * @return representation of clock's display
	 */
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

}
