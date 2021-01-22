/**
 * 
 */
package section3.model3;

import ripleytest.RipleyData;

/**
 * This class handles the statistic for the number of incidents described as blurry;
 * the number of questionable sightings. This is part of section 3's model (MVC).
 * 
 * @author Sean
 *
 */
public class NumberOfBlurry {

	private RipleyData ripleyData;

	/**
	 * @param ripleyData
	 */
	public NumberOfBlurry(RipleyData ripleyData) {
		this.ripleyData = ripleyData;
	}

	/**
	 * Accessor method for blurry incidents statistic
	 * @return number of blurry incidents as a String.
	 */
	public String get() {
		return ("" + ripleyData.getBlurIncidents());
	}

}
