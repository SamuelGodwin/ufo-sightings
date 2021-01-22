/**
 * 
 */
package section3.model3;

/**
 * @author ardag
 * This class handles the Google API statistic.
 * Part of section 3's model (MVC).
 */

import java.io.*;
import java.net.*;

public class SightingsViaGoogle {

	/**
	 * This method handles interaction with Youtube's API.
	 * @param urlYoutube
	 * @return resulting statistic info
	 * @throws Exception
	 */
	public static String getHTML(String urlYoutube) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlYoutube);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	/**
	 * Accessor method for our Google API statistic.
	 * @return statistic.
	 */
	public String get() {
		try {
			return (getHTML(
					"https://www.googleapis.com/youtube/v3/search?part=snippet&q=UFO%20sightings&type=video&key=AIzaSyBlOj1IPafVhj1S92esNJamS20E4qMajrM"));
		} catch (Exception e) {
			return "Error.";
		}
	}
}
