package Model;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

/**
 * Responsible for parsing output that comes from making YR.no API requests.
 */
public class ParserClass {

	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document doc;

	// TODO: The number of places in places.xml is hardcoded FIX THIS to support
	// N:number of cities etc...

	/**
	 * Parses places.xml for latitude,longitude and name of a place/city. This
	 * is later used as input to the API-requests as only the locations
	 * specified in places.xml are valid locations for a weather forecast
	 * 
	 * @return Array of LocationsForecast objects. These contain the name and
	 *         coordinates for a location.
	 */
	public LocationForecast[] parsePlacesXML() throws Exception {
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		LocationForecast[] placesXML = new LocationForecast[3];

		doc = builder.parse("places.xml");
		// TODO: placesXML amount constant.
		NodeList tempList = doc.getElementsByTagName("locality");
		for (int i = 0; i < 3; i++) {
			Element temp = (Element) tempList.item(i);

			NodeList locationList = temp.getElementsByTagName("location");
			Element locNode = (Element) locationList.item(0);

			placesXML[i] = new LocationForecast(temp.getAttribute("name"),
					Double.parseDouble(locNode.getAttribute("longitude")),
					Double.parseDouble(locNode.getAttribute("latitude")));

		}

		return placesXML;
	}

	/**
	 * Parses the data retrieved from a "LocationForecast" API request made to
	 * YR.no into a List of Forecast objects that represents the retrieved
	 * weather data
	 * 
	 * @return ArrayList of Forecast objects. These contain the weather data at
	 *         a specific time.
	 */
	public ArrayList<Forecast> parse2YR(InputStream inputStream)
			throws Exception {
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		ArrayList<Forecast> list = new ArrayList<Forecast>();

		doc = builder.parse(inputStream);

		NodeList timeList = doc.getElementsByTagName("time");
		// Node rootNode = locNodes.item(0);
		for (int i = 0; i < timeList.getLength(); i++) {
			Element time = (Element) timeList.item(i);

			String to = time.getAttribute("to");
			String from = time.getAttribute("from");

			NodeList tempList = time.getElementsByTagName("temperature");

			if (tempList.item(0) != null) {
				Element temp = (Element) tempList.item(0);

				String tempValue = temp.getAttribute("value");

				if (tempValue != "") {
					list.add(new Forecast(Double.parseDouble(tempValue), to,
							from));
				}
			}
		}
		return list;
	}

}
