/**
 * 
 */
package section2.view2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;

import api.ripley.Incident;
import ripleytest.RipleyData;
import section2.controller.JComboxListener;
import section2.controller.ListListener;

/**
 * This class is used to display the incident details of one
 * specific state, the one whose button is clicked. It sorts the
 * data according to whatever the user chooses (date and time of the
 * incident, city of the incident, the shape, the date the incident was
 * posted). When clicking on any of the incidents displayed, a
 * message window pops up showing a summary of that specific incident.
 *
 */

public class SightingsWindow extends JFrame {

	private TreeMap<String, String> city;
	private TreeMap<String, String> shape;
	private TreeMap<Integer, String> duration;
	private TreeMap<Date, String> timeOfSighting;
	private TreeMap<Date, String> datePosted;
	private ArrayList<String> data;
	private JPanel panel;
	private JComboBox select;
	private JTextArea sightingsList;
	private String[] jComb = { "-", "Date", "City", "Shape", "Duration", "Posted" };
	private String stateName;
	private ArrayList<Incident> incidents;
	private RipleyData ripleyData;
	private JList incidentJList;
	private String[] dataArray;
	private ListListener listListener;
	private JComboxListener comboxListener;
	private DefaultListModel dataListModel;
	private TreeMap<String, Incident> incidentCityData;
	private TreeMap<String, Incident> incidentShapeData;
	private TreeMap<String, Incident> incidentPostedData;
	private TreeMap<String, Incident> incidentDurationData;
	private TreeMap<String, Incident> incidentTimeData;

	/**
	 * Builds the main GUI, also creates an object of RipleyData and uses its data. 
	 * The window's name is the string, state, passed to the constructor.
	 * A JList is also created in order to display the
	 * data so that the each incident data is clickable.
	 * 
	 * @param state
	 * @param ripleyData
	 */
	public SightingsWindow(String state, RipleyData ripleyData) {

		this.setTitle(state);
		this.stateName = state;
		this.ripleyData = ripleyData;
		incidents = new ArrayList<>();
		incidents = ripleyData.getIncidents();
		setSize(new Dimension(700, 500));
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		select = new JComboBox(jComb);
		panel.add(select, BorderLayout.NORTH);
		dataListModel = new DefaultListModel();
		city = new TreeMap<>();
		shape = new TreeMap<>();
		duration = new TreeMap<>();
		timeOfSighting = new TreeMap<>();
		datePosted = new TreeMap<>();
		incidentCityData = new TreeMap<>();
		incidentPostedData = new TreeMap<>();
		incidentDurationData = new TreeMap<>();
		incidentShapeData = new TreeMap<>();
		incidentTimeData = new TreeMap<>();
		incidentJList = new JList(dataListModel);

		appendTextArea();

		incidentJList.setModel(dataListModel);
		incidentJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		incidentJList.setLayoutOrientation(JList.VERTICAL);
		listListener = new ListListener(this);
		incidentJList.addMouseListener(listListener);
		panel.add(incidentJList);
		panel.add(new JScrollPane(incidentJList), BorderLayout.CENTER);
		comboxListener = new JComboxListener(this);
		select.addActionListener(comboxListener);
		this.setVisible(true);

	}

	/**
	 * A method that adds the data to the JList and displays it.
	 * Five TreeMaps are used to sort the data, based on the element chosen to
	 * sort the data (shape, city, duration, time posted and date and time of
	 * the sighting).
	 */

	// Display the states information
	private void appendTextArea() {

		for (int i = 0; i < ripleyData.getIncidents().size(); i++) {

			Date dateAndTime = new Date();
			Date postedOn = new Date();

			if (stateName.equalsIgnoreCase(incidents.get(i).getState())) {

				// parse duration into an integer
				int durationInt = (getSeconds(i) / 60) + getMinutes(i) + getHours(i) * 60 + (getDays(i) * 24 * 60);

				// parse date and time string into a date format
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dateAndTimeOfSighting = df.parse(ripleyData.getIncidents().get(i).getDateAndTime());
					dateAndTime = dateAndTimeOfSighting;
				} catch (ParseException pe) {
					pe.printStackTrace();
				}

				// parse date posted string into a date format
				try {
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
					Date posted = df2.parse(ripleyData.getIncidents().get(i).getPosted());
					postedOn = posted;
				} catch (ParseException pe) {
					pe.printStackTrace();
				}

				// Store data based on City
				String cityInput = " City: " + ripleyData.getIncidents().get(i).getCity() + " Time: "
						+ ripleyData.getIncidents().get(i).getDateAndTime() + " Duration: "
						+ /* ripleyData.getIncidents().get(i).getDuration() */ durationInt + " Shape: "
						+ ripleyData.getIncidents().get(i).getShape() + " Posted: "
						+ ripleyData.getIncidents().get(i).getPosted() + "";
				city.put(incidents.get(i).getCity(), cityInput);
				incidentCityData.put(cityInput, incidents.get(i));

				// Store data based on shape
				String shapeInput = " Shape: " + ripleyData.getIncidents().get(i).getDateAndTime() + " Time: "
						+ ripleyData.getIncidents().get(i).getDateAndTime() + " Duration: "
						+ /* ripleyData.getIncidents().get(i).getDuration() */ durationInt + " City: "
						+ ripleyData.getIncidents().get(i).getCity() + " Posted: "
						+ ripleyData.getIncidents().get(i).getPosted() + "";
				shape.put(incidents.get(i).getShape(), shapeInput);
				incidentShapeData.put(shapeInput, incidents.get(i));

				// Store data based on Duration
				String durationInput = "Duration: "
						+ /* ripleyData.getIncidents().get(i).getDuration() */ durationInt + " Time: "
						+ ripleyData.getIncidents().get(i).getDateAndTime() + " Shape: "
						+ ripleyData.getIncidents().get(i).getShape() + " City: "
						+ ripleyData.getIncidents().get(i).getCity() + " Posted: "
						+ ripleyData.getIncidents().get(i).getPosted() + "";
				duration.put(durationInt, durationInput);
				incidentDurationData.put(durationInput, incidents.get(i));

				// Store data based on Time
				String timeInput = " Time: " + ripleyData.getIncidents().get(i).getDateAndTime() + " Duration: "
						+ /* ripleyData.getIncidents().get(i).getDuration() */ durationInt + " City: "
						+ ripleyData.getIncidents().get(i).getCity() + " Shape: "
						+ ripleyData.getIncidents().get(i).getShape() + " Posted: "
						+ ripleyData.getIncidents().get(i).getPosted() + "";
				timeOfSighting.put(dateAndTime, timeInput);
				incidentTimeData.put(timeInput, incidents.get(i));

				// Store data based on date posted
				String postedInput = " Posted: " + ripleyData.getIncidents().get(i).getPosted() + " City: "
						+ ripleyData.getIncidents().get(i).getCity() + " Duration: "
						+ /* ripleyData.getIncidents().get(i).getDuration() */ durationInt + " Shape: "
						+ ripleyData.getIncidents().get(i).getShape() + " Time: "
						+ ripleyData.getIncidents().get(i).getDateAndTime() + "";
				datePosted.put(postedOn, postedInput);

				incidentPostedData.put(postedInput, incidents.get(i));

			}
		}

		// append
		String choice = (String) select.getSelectedItem();

		switch (choice) {

		case "City":
			String key = city.firstKey();
			for (int i = 0; i < city.size(); i++) {

				String cityOrder = city.get(key);
				dataListModel.addElement(cityOrder);
				key = city.higherKey(key);
			}
			break;

		case "Shape":
			String key2 = shape.firstKey();
			for (int i = 0; i < shape.size(); i++) {

				String shapeOrder = shape.get(key2);
				dataListModel.addElement(shapeOrder);
				key = shape.higherKey(key2);
			}
			break;

		case "Duration":
			for (Map.Entry<Integer, String> entry : duration.entrySet()) {
				dataListModel.addElement(entry.getValue());
			}
			break;

		case "Posted":
			Date key4 = datePosted.firstKey();
			for (int i = 0; i < datePosted.size(); i++) {

				String postedOrder = datePosted.get(key4);
				dataListModel.addElement(postedOrder);
				key4 = datePosted.higherKey(key4);
			}
			break;

		case "Date":
			Date key5 = timeOfSighting.firstKey();
			for (int i = 0; i < timeOfSighting.size(); i++) {

				String postedOrder = timeOfSighting.get(key5);
				dataListModel.addElement(postedOrder);
				key5 = timeOfSighting.higherKey(key5);
			}
			break;

		case "-":

			Date key6 = datePosted.firstKey();
			for (int i = 0; i < datePosted.size(); i++) {

				String postedOrder = datePosted.get(key6);

				dataListModel.addElement(postedOrder);
				key6 = datePosted.higherKey(key6);
			}
			break;
		}
	}

	/**
	 * the method used to get seconds from the duration. uses regex
	 * 
	 * @param i
	 * @return seconds from duration
	 */
	public int getSeconds(int i) {
		int secondsInt = 0;

		String duration = ripleyData.getIncidents().get(i).getDuration();

		String format = "(.*) ([S|s]econds)";

		// Match seconds
		Pattern processSeconds = Pattern.compile(format);
		Matcher secondsMatcher = processSeconds.matcher(duration);

		if (secondsMatcher.find()) {
			String seconds = secondsMatcher.group(1);

			if ((seconds).contains("-")) {
				seconds = seconds.substring(0, seconds.indexOf("-"));
			}

			try {
				secondsInt = Integer.parseInt(seconds);
			} catch (Exception e) {
				secondsInt = 0;
			}
		}
		return secondsInt;
	}

	/**
	 * the method used to get minutes from the duration. uses regex
	 * 
	 * @param i
	 * @return minutes from duration
	 */
	public int getMinutes(int i) {

		int minutesInt = 0;
		String duration = incidents.get(i).getDuration();

		String format = "(.*) ([M|m]inutes)";

		// Match seconds
		Pattern processMinutes = Pattern.compile(format);
		Matcher minutesMatcher = processMinutes.matcher(duration);

		if (minutesMatcher.find()) {
			String minutes = minutesMatcher.group(1);

			if (((String) minutes).contains("-")) {
				minutes = minutes.substring(0, minutes.indexOf("-"));
			}

			try {
				minutesInt = Integer.parseInt(minutes);
			} catch (Exception e) {
				minutesInt = 0;
			}
		}
		return minutesInt;

	}

	/**
	 * the method used to get hours from the duration. uses regex
	 * 
	 * @param i
	 * @return hours from duration
	 */
	public int getHours(int i) {

		int hoursInt = 0;
		String duration = ripleyData.getIncidents().get(i).getDuration();
		String format = "(.*) ([H|h]ours)";

		// Match seconds
		Pattern processHours = Pattern.compile(format);
		Matcher hoursMatcher = processHours.matcher(duration);

		if (hoursMatcher.find()) {
			String hours = hoursMatcher.group(1);

			if (((String) hours).contains("-")) {
				hours = hours.substring(0, hours.indexOf("-"));
			}

			try {
				hoursInt = Integer.parseInt(hours);
			} catch (Exception e) {
				hoursInt = 0;
			}
		}
		return hoursInt;

	}

	/**
	 * the method used to get days from the duration. uses regex
	 * 
	 * @param i
	 * @return days from duration
	 */
	public int getDays(int i) {

		int daysInt = 0;
		String duration = ripleyData.getIncidents().get(i).getDuration();
		String format = "(.*) ([D|d]ays)";

		// Match seconds
		Pattern processDays = Pattern.compile(format);
		Matcher daysMatcher = processDays.matcher(duration);

		if (daysMatcher.find()) {
			String days = daysMatcher.group(1);

			if (((String) days).contains("-")) {
				days = days.substring(0, days.indexOf("-"));

			}

			try {
				daysInt = Integer.parseInt(days);

			} catch (Exception e) {
				daysInt = 0;
			}
		}

		return daysInt;

	}

	/**
	 * a method for making a message pop up after choosing an incident. the
	 * message displays the incident's summary.
	 * 
	 * @param string
	 */

	public void messagePopUp(String string) {

		if (select.getSelectedItem().equals("-")) {

			JOptionPane.showMessageDialog(null, incidentPostedData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (select.getSelectedItem().equals("Duration")) {
			JOptionPane.showMessageDialog(null, incidentDurationData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (select.getSelectedItem().equals("Posted")) {
			JOptionPane.showMessageDialog(null, incidentPostedData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (select.getSelectedItem().equals("Time")) {
			JOptionPane.showMessageDialog(null, incidentTimeData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (select.getSelectedItem().equals("City")) {
			JOptionPane.showMessageDialog(null, incidentCityData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);

		} else { // shape
			JOptionPane.showMessageDialog(null, incidentShapeData.get(string).getSummary(), "Event Details",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * The method used to reset the jList after the option on the JComboBox is
	 * changed i.e the user chooses another option for sorting.
	 * Method is called by JComboBox listener
	 */

	public void newSelection() {

		dataListModel.clear();
		city.clear();
		shape.clear();
		duration.clear();
		datePosted.clear();
		timeOfSighting.clear();
		incidentCityData.clear();
		incidentShapeData.clear();
		incidentDurationData.clear();
		incidentPostedData.clear();
		incidentTimeData.clear();
		appendTextArea();
		incidentJList.setModel(dataListModel);
	}

}
