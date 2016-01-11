package Model;

import java.util.ArrayList;
/** A container class that stores and manages all the Forecasts for a given location.
 * */
public class WeatherDataList {
	// HARDCODED NROFPLACES FIX NEEDED AT SOME POINT
	final int NROFPLACES = 3;
	private LocationForecast[] places;
	private ArrayList<LocationForecast> list = new ArrayList<LocationForecast>();

	public WeatherDataList() {
		this.setLocations();
	}

	private void setLocations() {
		ParserClass factory = new ParserClass();
		try {
			places = factory.parsePlacesXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(LocationForecast e) {
		e.setTimestamp(System.currentTimeMillis() / 1000L);
		list.add(e);
	}

	public LocationForecast get(int index) {
		return list.get(index);
	}

	public LocationForecast[] getPlaces() {
		return places;
	}

	/**
	 * Checks if there exists an already retrieved LocationForecast by name
	 * 
	 * @param name
	 *            Name of the location to check for
	 * @return -1 if not found or a positive integer corresponding to the index
	 *         of the object that was found
	 */
	public int doesExist(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLocationName().toLowerCase()
					.equals(name.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}

	public LocationForecast getLocationByName(String name) {

		for (int i = 0; i < places.length; i++) {

			if (places[i].getLocationName().toLowerCase()
					.equals(name.toLowerCase())) {
				return places[i];
			}
		}
		return null;
	}

}
