/**
 * 
 */
package section3.model3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import api.ripley.Incident;
import ripleytest.RipleyData;

/**
 * @author Samuel 
 *
 *		   This class handles the statistic for the hour of day that
 *         is most common for UFO sightings in the selected date range;
 *         the time of day you are most likely to see a UFO. This is part of
 *         section 3's model (MVC).
 */
public class MostLikely {

	RipleyData ripleyData;

	/**
	 * @param ripleyData
	 */
	public MostLikely(RipleyData ripleyData) {
		this.ripleyData = ripleyData;
	}

	/**
	 * The method using states and stateFrequencies to find LIKELIEST HOUR. 
	 * Returns the state with the most sightings within a selected date range.
	 */
	public String findModeHour() {
		return ripleyData.findModeHour();
	}

	/**
	 * The method using states and stateFrequencies to find LIKELIEST STATE.
	 * Returns the state with the most sightings within a selected date range.
	 */
	public String findModeState() {
		return ripleyData.findModeState();
	}
}
