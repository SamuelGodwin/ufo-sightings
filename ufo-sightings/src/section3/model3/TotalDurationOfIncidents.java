/**
 * 
 */
package section3.model3;

import java.util.ArrayList;

import api.ripley.Incident;
import api.ripley.Ripley;
import ripleytest.RipleyData;

/**
 *
 * This class handles the statistic for calculating the total duration of incidents in a user's selected date range. 
 * Part of section 3's model (MVC).
 *
 */
public class TotalDurationOfIncidents {

	private ArrayList<Incident> memory;
	private int counter;
	// private static RipleyData ripleyData;

	private int totalSeconds;
	private int totalMinutes;
	private int totalHours;
	private int totalDays;
	private RipleyData ripleyData;

	/**
	 * @param ripleyData
	 */
	public TotalDurationOfIncidents(RipleyData ripleyData) {
		this.ripleyData = ripleyData;
		memory = ripleyData.getIncidents();
	}

	/**
	 * Calls calculation methods in this class.
	 * @return
	 */
	public String get() {

		return (calculateTotalSeconds() + calculateTotalMinutes() + calculateTotalHours() + calculateTotalDays());

	}

	/**
	 * Calculates total number of seconds of incidents within program's stored data.
	 * @return seconds format as a String
	 */
	public String calculateTotalSeconds() {

		String summary = "";
		totalSeconds = 0;
		for (int i = 0; i < memory.size(); i++) {

			summary = memory.get(i).getDuration();
			if (summary.matches(".*\\d.*") && summary.contains("seconds")) {

				String clean = summary.replaceAll("\\D+", "");
				totalSeconds += Integer.parseInt(clean);
			}
		}
		return (" Seconds: " + totalSeconds);
	}

	/**
	 * Calculates total number of minutes of incidents within program's stored data.
	 * @return minutes format as a String
	 */
	public String calculateTotalMinutes() {

		String summary = "";
		totalMinutes = 0;
		for (int i = 0; i < memory.size(); i++) {

			summary = memory.get(i).getDuration();
			if (summary.matches(".*\\d.*") && summary.contains("minutes")) {

				String clean = summary.replaceAll("\\D+", "");
				totalMinutes += Integer.parseInt(clean);
			}
		}
		return (" Minutes: " + totalMinutes);
	}

	/**
	 * Calculates total number of hours of incidents within program's stored data.
	 * @return hours format as a String
	 */
	public String calculateTotalHours() {

		String summary = "";
		totalHours = 0;
		for (int i = 0; i < memory.size(); i++) {

			summary = memory.get(i).getDuration();
			if (summary.matches(".*\\d.*") && summary.contains("hours")) {

				String clean = summary.replaceAll("\\D+", "");
				totalHours += Integer.parseInt(clean);
			}
		}
		return (" Hours: " + totalHours);
	}

	/**
	 * Calculates total number of days of incidents within program's stored data.
	 * @return days format as a String
	 */
	public String calculateTotalDays() {

		String summary = "";
		totalDays = 0;
		for (int i = 0; i < memory.size(); i++) {

			summary = memory.get(i).getDuration();
			if (summary.matches(".*\\d.*") && summary.contains("days")) {

				String clean = summary.replaceAll("\\D+", "");
				totalDays += Integer.parseInt(clean);
			}
		}
		return ("Days: " + totalDays);
	}

}
