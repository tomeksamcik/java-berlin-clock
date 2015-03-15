package com.ubs.opsit.interviews;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * @author Tomek Samcik
 *
 */
public class ClockTimeConverter implements TimeConverter {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClockTimeConverter.class);	
	
	private Clock clock;	
	
	/**
	 * Constructing converter
	 * 
	 * @param clock clock instance
	 */
	@Inject
	public ClockTimeConverter(Clock clock) {
		this.clock = clock;
	}	

	@Override
	public String convertTime(String aTime) {		
		if (!aTime.isEmpty()) {		
			setClockTime(aTime);		
			
			LOG.debug(clock.toString());
		} else {
			LOG.error("Input string empty");
		}
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
			LocalTime time = LocalTime.parse(aTime, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.SMART));
			
			clock.setTime(time);
		} else {
			clock.setTime(24, 0, 0);
		}
	}

}
