/**
 * 
 */
package section3.model3;

/**
 * This class handles the statistic for average time between sightings. 
 * This is part of section 3's model (MVC).
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ripleytest.RipleyData;

public class AverageTimeBetweenSightings {

	private Matcher dateMatcher;
	private Matcher timeMatcher;
	private long totalDifference;
	private long averageDifference;
	private RipleyData ripleyData;

	/**
	 * Sets ripleyData field.
	 * @param ripleyData
	 */
	public AverageTimeBetweenSightings(RipleyData ripleyData) {
		this.ripleyData = ripleyData;
	}

	/**
	 * Fetches the accessor method in ripleyData relevant to average time between sightings.
	 * @return a String representation of the average time between UFO sightings.
	 */
	public String get() {
		return ripleyData.GetAverageTimeBetweenSightings() + " Seconds";

	}

}
