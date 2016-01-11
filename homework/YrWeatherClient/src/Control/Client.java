package Control;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class contains all relevant methods and data for making API requests to
 * YR.no
 */
/** Contains methods and variables necessary for making API requests to YR.no.
 * @author David Mjärdell
 */

class Client {

	/**
	 * Returns an InputStream of XML code from an YR.no API request.
	 * 
	 * @param latitude
	 *            a geographic coordinate needed for the API request.
	 * @param longitude
	 *            a geographic coordinate needed for the API request.
	 * @return InputStream to the output of the API-request.
	 */
	InputStream getLocInputstream(double latitude, double longitude)
			throws IOException, MalformedURLException {

		URL url;
		InputStream is = null;

		String requestURL = "http://api.yr.no/weatherapi/locationforecast/1.9/?lat="
				+ latitude + ";lon=" + longitude;

		url = new URL(requestURL);
		is = url.openStream();

		return is;
	}
}
