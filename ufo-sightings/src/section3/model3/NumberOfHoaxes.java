/**
 * 
 */
package section3.model3;

import java.util.ArrayList;
import api.ripley.Incident;
import ripleytest.RipleyData;
import section3.view3.Panel3;


/**
 *
 * This class handles the statistic for the number of hoax sightings.
 * This is part of section 3's model (MVC).
 * 
 * @author ardag
 *
 */
public class NumberOfHoaxes {
	
	private ArrayList<Incident> memory;
	private int counter;
	private RipleyData ripleyData;
	
	/**
	 * @param ripleyData
	 */
	public NumberOfHoaxes(RipleyData ripleyData){
		this.ripleyData = ripleyData;
		memory = ripleyData.getIncidents();
		if (!memory.isEmpty()) {
			
			String summary = "";
			counter = 0;
			for (int i = 0; i < memory.size(); i++) {

				summary = memory.get(i).getSummary();
				if (summary.contains("HOAX") || summary.contains("Hoax") || summary.contains("hoax")) {
					counter += 1;
				}
			}	
			
		}
	}
	
	/**
	 * Returns number of hoaxes statistic
	 * @return number of hoaxes statistic as a String.
	 */
	public String get(){
		return "Number of Hoaxes: "+counter;
	}
}
