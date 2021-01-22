/**
 * 
 */
package ripleytest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.ripley.Incident;
import api.ripley.Ripley;
import section3.model3.MostLikely;

/**
 * RipleyData class. Use the Ripley instance field in this class to make use of
 * Ripley's info elsewhere in our program. A maximum of one new object of this
 * class, ripleyData, will be made in MainGUI and passed in to other classes in
 * our program i.e. Panel1, Panel2, Panel3 & Panel4. This helps minimise Ripley
 * calls & thus, minimises time spent interacting with Ripley's API. This class
 * stores all necessary data from one Ripley call in fields. These fields have
 * accessor methods, where relevant.
 * 
 * @author Samuel
 *
 */
public class RipleyData {

	private Ripley ripley;

	// An ArrayList containing every year pertaining UFO data from Ripley.
	private ArrayList<String> ripleyYears;
	private ArrayList<Incident> incidentsInRange;
	private String[] yearsArray;

	private ArrayList<String> locations;
	private ArrayList<String> times;
	private ArrayList<String> hours;

	private HashMap<String, Integer> stateFrequencies;
	private HashMap<String, Integer> hourFrequencies;

	private double ripleyVersion;
	private String lastUpdated;

	private MostLikely mostLikely;

	private ArrayList<String> incidentStrings;

	private ArrayList<Integer> ripleyDates;

	private long fetchDuration;
	private String fetchDurationString;

	private String temp;

	private String acknowledgementString;

	private int startYear;

	private int latestYear;

	private int noOfBlurs;

	private Matcher timeMatcher;

	private Matcher dateMatcher;

	private long totalDifference;

	private long averageDifference;

	private long averageDifferenceAbs;

	/**
	 * Constructor method for RipleyData.
	 * 
	 * Sets ripley field of this class and initialises each of the ripleyYears,
	 * ripleyDates, incidentStrings, incidentsInRange, locations, times and
	 * hours ArrayLists. Also initialises the HashMaps 'stateFrequencies' and
	 * 'hourFrequencies', as well as the 'yearsArray' array (currently created
	 * via ripleyYears ArrayList).
	 * 
	 * Lastly this constructor instantiates 'MostLikely', calls 'fillYears' and
	 * sets the values of the fields 'ripleyVersion' & 'lastUpdated', also using
	 * one of these in a String 'ripleyVer'.
	 */
	public RipleyData() {

		ripley = new Ripley("10tLI3GXtdyyVD6ql2OMtA==", "tBgm4pVp8QzVqL46EnH7ew==");

		startYear = ripley.getStartYear();
		latestYear = ripley.getLatestYear();

		// AcknowledgementString is to be shown in all programs that use Ripley.
		acknowledgementString = ripley.getAcknowledgementString();
		System.out.println(acknowledgementString);

		// initiates ArrayList
		ripleyYears = new ArrayList<String>();

		/*
		 * In Java generic type arguments must be objects. Use the Integer class
		 * which is a wrapper for int. With autoboxing in Java the primitive
		 * type int will become an Integer when necessary.
		 */
		ripleyDates = new ArrayList<Integer>();

		// String contains information about Ripley version (only an initial draft).
		// Takes some time to collect Ripley info on program execution
		// currently.
		String ripleyVer = new String("Welcome to the Ripley API version " + ripley.getVersion());

		incidentStrings = new ArrayList<String>();

		mostLikely = new MostLikely(this);

		stateFrequencies = new HashMap<String, Integer>();

		hourFrequencies = new HashMap<String, Integer>();

		incidentsInRange = new ArrayList<Incident>();
		yearsArray = new String[0];
		locations = new ArrayList<String>();
		times = new ArrayList<String>();
		hours = new ArrayList<String>();

		fillYears();

		temp = "";

		ripleyVersion = ripley.getVersion();
		lastUpdated = ripley.getLastUpdated();

	}

	/**
	 * Method to populate the ripleyYears ArrayList with the years within the
	 * date range the user selects. Ensures that the first element in the
	 * ArrayList is "-"; the default JComboBox value that will show on screen to
	 * the user (JComboBoxes show first element in collection by default). After
	 * this, converts ArrayList to array for use.
	 */
	public void fillYears() {
		// clear ArrayList, in case already full.
		// ripleyYears.clear();

		ripleyYears.add("-");

		for (int i = ripley.getStartYear(); i <= ripley.getLatestYear(); i++) {
			String year = "" + i;
			ripleyYears.add(year);

		}

		// Converts this ArrayList to a normal array.
		// Our JComboBoxes are filled via an array.
		ripleyYearsToArray();
	}

	/**
	 * Converts ripleyYears ArrayList into an array, 'yearsArray'. Our
	 * JComboBoxes are filled with this array.
	 */
	// Testing to see if JComboBoxes easier to populate with array than ArrayList
	public void ripleyYearsToArray() {
		yearsArray = ripleyYears.toArray(new String[ripleyYears.size()]);

	}

	/**
	 * This method times the duration of the method 'storeIncidentsInRange',
	 * which fetches Ripley data. The immediate result, 'fetchDuration' is
	 * converted into a more readable format via the 'nanoToMinsSecs' method (it
	 * is passed as a parameter). The method uses nanoTime, as it is a more
	 * accurate means of measurement than currentTimeMillis.
	 * 
	 * @param firstJComboYear
	 * @param secondJComboYear
	 */
	public void timeRipleyFetch(Object firstJComboYear, Object secondJComboYear) {
		long startTime = System.nanoTime();
		storeIncidentsInRange(firstJComboYear, secondJComboYear);
		long endTime = System.nanoTime();

		fetchDuration = (endTime - startTime);

		nanoToMinsSecs(fetchDuration);

	}

	/**
	 * This method converts a passed value of type long - fetchDuration (equal
	 * to the number of nanoseconds storeIncidentsInRange takes to execute) -
	 * into a format which displays the amount in a mins, seconds format.
	 * 
	 * @param fetchDuration
	 * @return a String value in mins, seconds format.
	 */
	public String nanoToMinsSecs(long fetchDuration) {
		fetchDurationString = "Data grabbed in " + String.format("%d min, %d seconds.",
				TimeUnit.NANOSECONDS.toHours(fetchDuration), TimeUnit.NANOSECONDS.toSeconds(fetchDuration)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(fetchDuration)));
		return fetchDurationString;
	}

	/**
	 * Calls the Ripley method 'getIncidentsInRange', passing it the arguments
	 * passed as parameters to this method.
	 * 
	 * @param year1
	 * @param year2
	 */
	public void storeIncidentsInRange(Object year1, Object year2) {

		// From first day of first year, appends user's chosen year.
		String start = year1 + "-01-01 00:00:00";
		// Until last day of last year, appends user's chosen year.
		String end = year2 + "-12-31 00:00:00";
		// Code to store the result of the above as an ArrayList in this class.
		incidentsInRange = ripley.getIncidentsInRange(start, end);

		setBlurIncidents();

	}

	/**
	 * This function simply populates the ArrayList 'locations', containing the
	 * state of every *stored* incident. For this to run, ArrayList 'memory'
	 * needs to not be empty i.e. incident data MUST HAVE BEEN FETCHED from Panel1.
	 */
	public void fillTimes() {
		// Needs ArrayList 'incidentsInRange' of Ripley incidents to already be populated (not null).

		for (Incident incident : incidentsInRange)
			// Add to ArrayList of times.
			// Assuming it doesn't take up time to call getState on every incident
			// in our stored incidents.
			times.add(incident.getDateAndTime());
	}

	/**
	 * This method populates an ArrayList 'hours' of elements of type String,
	 * with the hour substring from each element in the ArrayList 'times' of DateTimes.
	 */
	public void fillHours() {

		for (String time : times) {
			hours.add(time.substring(11, 13));
		}
	}

	/**
	 * This function simply populates the ArrayList 'locations', containing the
	 * state of every *stored* incident. For this to run, ArrayList 'memory'
	 * needs to not be empty i.e. incident data MUST HAVE BEEN FETCHED from Panel1.
	 */
	public void fillLocations() {
		// Needs ArrayList 'memory' of Ripley incidents to already be populated (not null).

		for (Incident incident : incidentsInRange)
			if (!incident.getState().equals("Not specified."))
				locations.add(incident.getState());
	}

	public void fillIncidentStrings() {

		// populate new ArrayList with same number of empty strings as incidents
		// in incidentsInRange.

		for (Incident incident : incidentsInRange) {

			incidentStrings.add(incident.getCity() + ", " + incident.getDateAndTime() + ", " + incident.getDuration()
					+ ", " + incident.getIncidentID() + ", " + incident.getPosted() + ", " + incident.getShape() + ", "
					+ incident.getState() + ", " + incident.getSummary() + ", " + incident.toString());
		}

	}

	/**
	 * Function to count the number of incidents in each state in the
	 * 'locations' ArrayList (how frequently each state name appears in the
	 * ArrayList). Fills stateFrequencies with this data. Assumes 'locations'
	 * has already been passed data when fetch button pressed.
	 */
	public void fillStateFrequencies() {

		Set<String> unique = new HashSet<String>(locations);

		for (String key : unique) {
			// A TreeMap mapping location name keys to frequency Integer values.
			stateFrequencies.put(key, Collections.frequency(locations, key));
		}

	}

	/**
	 * Function to count the number of each hour in the 'hours' ArrayList (how
	 * frequently each hour appears in the ArrayList). Fills hourFrequencies
	 * with this data. Assumes 'hours' has been passed data when fetch button was pressed.
	 */
	public void fillHourFrequencies() {

		Set<String> unique = new HashSet<String>(hours);

		for (String key : unique) {
			// A TreeMap mapping state name keys to frequency Integer values.
			hourFrequencies.put(key, Collections.frequency(hours, key));
		}
	}

	// getter for highest frequency state
	/**
	 * The methods using locations ArrayList and stateFrequencies HashMap to
	 * find LIKELIEST STATE. Returns the state with the most sightings within
	 * selected date range.
	 */
	public String findModeState() {
		int max = 1;

		for (String location : locations) {

			if (stateFrequencies.get(location) != null) {
				int count = stateFrequencies.get(location);
				count = count + 1;
				stateFrequencies.put(location, count);

				if (count > max) {
					max = count;
					temp = location;
				}
			}

			else {
				stateFrequencies.put(location, 1);
			}
		}
		return temp;
	}

	/**
	 * This method uses locations ArrayList and stateFrequencies HashMap to
	 * find LIKELIEST STATE. Returns the state with the most sightings within
	 * selected date range. This methods uses hours ArrayList and
	 * hourFrequencies HashMap to find LIKELIEST HOUR. Returns hour of day
	 * of the most sightings (within the selected date range).
	 */
	public String findModeHour() {

		int max = 1;
		String modeHour = "";

		if (hours.size() == 0) {

		} else {
			for (String hour : hours) {
				if (hourFrequencies.get(hour) != null) {
					int count = hourFrequencies.get(hour);
					count += 1;
					hourFrequencies.put(hour, count);
					if (count > max) {
						max = count;
						modeHour = hour;
					}
				}

				else {
					hourFrequencies.put(hour, 1);
				}
			}
		}

		return modeHour;
	}

	/**
	 * Clears all collections, in case already full from an earlier fetch.
	 */
	public void clearCollections() {

		ripleyYears.clear();
		incidentsInRange.clear();
		locations.clear();
		times.clear();
		hours.clear();
		stateFrequencies.clear();
		hourFrequencies.clear();
		incidentStrings.clear();
		ripleyDates.clear();
		// Empties array, although this may not be necessary. JIC.
		yearsArray = new String[0];
	}

	/**
	 * @return 'ripley' field, an instantiation of Ripley.
	 */
	public Ripley getRipleyObject() {
		return ripley;
	}

	/**
	 * 
	 * @return an array containing the range of years in the Ripley database,
	 *         each in String representation - for use in populating JComboBoxes
	 *         in MainGUI.
	 */
	public String[] getYearsArray() {

		return yearsArray;
	}

	/**
	 * 
	 * @return an ArrayList containing the range of years in the Ripley
	 *         database, each in String representation.
	 */
	public ArrayList<String> getTimes() {
		return times;
	}

	/**
	 * Returns anyincidents already fetched.
	 * 
	 * @return incidentsInRange
	 */
	public ArrayList<Incident> getIncidents() {
		return incidentsInRange;
	}

	/**
	 * 
	 * @return a HashMap which maps location names to how frequently they appear
	 *         in 'locations'.
	 *
	 */
	public HashMap<String, Integer> getStateFrequencies() {
		return stateFrequencies;
	}

	/**
	 * 
	 * @return a HashMap which maps an hour of day to how frequently it appears
	 *         in 'hours'.
	 */
	public HashMap<String, Integer> getHourFrequencies() {
		return hourFrequencies;
	}

	/**
	 * 
	 * @return an ArrayList of Strings which represent hours i.e '21', '22' etc.
	 */
	public ArrayList<String> getHours() {
		return hours;
	}

	/**
	 * 
	 * @return an ArrayList of Strings which represent locations such as states.
	 */
	public ArrayList<String> getLocations() {
		return locations;
	}

	/**
	 * 
	 * @return a field which stores current Ripley version.
	 */
	public double getRipleyVersion() {
		return ripleyVersion;
	}

	/**
	 * The name of location for incidents.
	 * 
	 * @return an ArrayList 'locations' of String elements.
	 */
	public ArrayList<String> getStateNames() {
		return locations;
	}

	/**
	 * 
	 * @return A string representation of the last time there was an addition to
	 *         Ripley's database.
	 */
	public String getLastUpdatedInfo() {
		return lastUpdated;
	}

	/**
	 * This is an accessor method for a frequency of incidents in a single
	 * location.
	 * 
	 * @return the frequency for a state (passed as a parameter) from the stateFrequencies HashMap.
	 */
	public Integer getStateFrequency(String string) {
		return stateFrequencies.get(string);
	}

	/**
	 * 
	 * @return a field of type integer containing the date of the first reported
	 *         Ripley incident.
	 */
	public int getStartDate() {
		return startYear;
	}

	/**
	 * 
	 * @return A field of type integer containing the date of the last reported
	 *         Ripley incident.
	 */
	public int getEndDate() {
		return latestYear;
	}

	/**
	 * This is an accessor method for the 'acknowledgementString' field.
	 * AcknowledgementString is to be shown in all programs that use Ripley.
	 * 
	 * @return a Ripley acknowledgement String.
	 */
	public String getRipleyAcknowledgement() {
		return acknowledgementString;
	}

	/**
	 * This is an accessor method for highest incident frequency.
	 * 
	 * @return int value of frequency of incidents - from state with largest
	 *         number of incidents.
	 */
	public int getMaxFrequency() {
		if (getStateFrequency(findModeState()) == null) {
			return 1;
		}
		return (getStateFrequency(findModeState()));

	}

	/**
	 * 
	 * @return an ArrayList of String representations of elements,
	 *         incidentStrings.
	 */
	public ArrayList<String> getIncidentStrings() {
		return incidentStrings;
	}

	/**
	 * The method which uses incidentStrings in calculating the number of
	 * occurences of a decription containing "blur".
	 * 
	 * @return noOfBlurs
	 */
	public void setBlurIncidents() {
		noOfBlurs = 0;
		for (Incident incident : incidentsInRange) {

			if (incident.getSummary().toLowerCase().contains("blur")) {
				noOfBlurs++;
			}
		}
	}

	public int getBlurIncidents() {
		return noOfBlurs;
	}

	/**
	 * 
	 * @return a String representing the time taken for a Ripley data fetch
	 *		   in a presentable format.
	 */

	public String getDurationString() {
		return fetchDurationString;
	}

	/**
	 * @return an ArrayList containing the range of years selected by the user.
	 */
	public ArrayList<String> getRipleyYears() {
		return ripleyYears;
	}

	/**
	 * A method that works out the the average time between sightings based on the totalTime between sightings.
	 * the total time is obtained based on the sum of the differences between the date and time of each two successive incidents.
	 */
	public void setAverageTimeBetweenSightings() {

		int k = 0;
		for (int i = 0; i < incidentsInRange.size() - 1; i++) {

			k++;

			matchDate(i);
			matchDate(i + 1);
			long dateDiff = matchDate(i + 1).getTime() - matchDate(i).getTime();
			long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.SECONDS);

			matchTime(i);
			matchTime(i + 1);
			long timeDiff = matchTime(i + 1).getTime() - matchTime(i).getTime();
			long timeDifference = TimeUnit.DAYS.convert(timeDiff, TimeUnit.SECONDS);

			totalDifference += dateDifference + timeDifference;
		}
		if (k == 0) {
			k = 1;
		}
		averageDifference = (totalDifference / k);
		averageDifferenceAbs = Math.abs(averageDifference);

	}

	/**
	 * a getter for the average time between sightings
	 */
	public long GetAverageTimeBetweenSightings() {
		return averageDifferenceAbs;
	}

	/**
	 * Work out the date of the incident using regex
	 * @param i
	 * @return date of the incident
	 */
	private Date matchDate(int i) {

		Date dateObtained = new Date();
		String dateAndTime = getTimes().get(i);

		String format = "(\\d\\d-\\d\\d-\\d\\d) (\\d\\d:\\d\\d:\\d\\d)";

		// Match date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Pattern processDate = Pattern.compile(format);
		Matcher dateMatcher = processDate.matcher(dateAndTime);
		this.dateMatcher = dateMatcher;

		if (dateMatcher.find()) {
			String datee = dateMatcher.group(1);
			Date date;
			try {
				date = dateFormat.parse(datee);
				dateObtained = date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dateObtained;
	}

	/**
	 * Work out the time of the incident using regex
	 * @param i
	 * @return time of the incident
	 */
	private Date matchTime(int i) {

		Date timeObtained = new Date();
		String dateAndTime = getTimes().get(i);

		String format = "(\\d\\d-\\d\\d-\\d\\d) (\\d\\d:\\d\\d:\\d\\d)";

		// Match time
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		Pattern processTime = Pattern.compile(format);
		Matcher dateMatcher = processTime.matcher(dateAndTime);
		this.timeMatcher = dateMatcher;

		if (dateMatcher.find()) {
			String datee = dateMatcher.group(2);
			Date date;
			try {
				date = timeFormat.parse(datee);
				timeObtained = date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return timeObtained;
	}

}
