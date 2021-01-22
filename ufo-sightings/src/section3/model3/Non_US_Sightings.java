
package section3.model3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.ripley.Incident;
import ripleytest.RipleyData;

/**
 *
 * This class handles the statistic for the number of non-US sightings. This is part of
 * section 3's model (MVC).
 *
 * @author ardag
 *
 */
public class Non_US_Sightings {

	private ArrayList<Incident> memory;
	private ArrayList<String> listOfStates;
	private RipleyData ripleyData;
	private int counter;
	
	/**
	 * @param ripleyData
	 */
	public Non_US_Sightings(RipleyData ripleyData){
		this.ripleyData = ripleyData;
		
		listOfStates = new ArrayList<String>();
		Scanner s;
		try {
			s = new Scanner(new File("src/section3/model3/States.txt"));
			while (s.hasNext()) {
				listOfStates.add(s.next());
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks for sightings from countries which do not have a state name/
	 * are outside the US.
	 * @return number of non-US sightings, String format.
	 */
	public String checkPostsFromOtherCountries() {

		memory = ripleyData.getIncidents();

		String stateName = "";
		counter = 0;
		for (int i = 0; i < memory.size(); i++) {

			stateName = memory.get(i).getState();

			for (int j = 0; j < listOfStates.size(); j++) {
				if (listOfStates.get(j).equals(stateName)) {
					
					counter += 1;
				}
			}
		}

		return ("" +(memory.size() - counter));

	}

	/**
	 * @return result of the checkPostsFromOtherCountries method.
	 */
	public String get(){
		return checkPostsFromOtherCountries();
	}
}
