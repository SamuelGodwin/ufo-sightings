/**
 * 
 */
package section3.model3;

import ripleytest.RipleyData;

/**
 * @author Sean
 * 
 *         This class contains a number of getters for each statistic, and a
 *         means of choosing between them. Part of section 3's model (MVC).
 *
 */
public class StatCalc {

	private MostLikely mostlikely;
	Non_US_Sightings nonUS;
	SightingsViaGoogle google;
	TotalDurationOfIncidents totalduration;
	NumberOfHoaxes hoaxes;
	RipleyData ripleyData;
	private AverageTimeBetweenSightings averagetime;
	private NumberOfBlurry numberBlurry;

	/**
	 * 
	 * @param ripleyData
	 */
	public StatCalc(RipleyData ripleyData) {
		this.ripleyData = ripleyData;
	}

	/**
	 * This method determines the appropriate 'stat' method call.
	 * 
	 * @param stat
	 * @return an appropriate method call for a specific statistic
	 */
	public String getStat(int stat) {
		if (stat == 1)
			return stat1();
		else if (stat == 2)
			return stat2();
		else if (stat == 3)
			return stat3();
		else if (stat == 4)
			return stat4();
		else if (stat == 5)
			return stat5();
		else if (stat == 6)
			return stat6();
		else if (stat == 7)
			return stat7();
		return stat8();
	}

	/**
	 * @return statistic 1
	 */
	public String stat1() {
		hoaxes = new NumberOfHoaxes(ripleyData);
		return hoaxes.get();
	}

	/**
	 * @return statistic 2
	 */
	public String stat2() {
		nonUS = new Non_US_Sightings(ripleyData);
		return nonUS.get();
	}

	/**
	 * @return statistic 3
	 */
	public String stat3() {
		mostlikely = new MostLikely(ripleyData);
		return mostlikely.findModeState();
	}

	/**
	 * @return statistic 4
	 */
	public String stat4() {
		google = new SightingsViaGoogle();
		return google.get();
	}

	/**
	 * @return statistic 5
	 */
	public String stat5() {
		mostlikely = new MostLikely(ripleyData);
		return (mostlikely.findModeHour() + ":00");
	}

	/**
	 * @return statistic 6
	 */
	public String stat6() {
		totalduration = new TotalDurationOfIncidents(ripleyData);
		return totalduration.get();
	}

	/**
	 * @return statistic 7
	 */
	public String stat7() {
		averagetime = new AverageTimeBetweenSightings(ripleyData);
		return averagetime.get();

	}

	/**
	 * @return statistic 8
	 */
	public String stat8() {
		numberBlurry = new NumberOfBlurry(ripleyData);
		return numberBlurry.get();
	}
}
