package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;

import Model.Forecast;
import Model.LocationForecast;
import Model.WeatherDataList;
import Model.ParserClass;
import View.YrWeatherGUI;

/**@author David Mjärdell*/

/**
 * Main program and controller.
 * 
 */

public class YrWeatherClientMain implements ActionListener {

	private YrWeatherGUI view;
	private WeatherDataList weatherDataList;
	private Client client;
	private ParserClass parser;
	private static long TTL = 3600; // DEFAULT TTL in seconds

	public YrWeatherClientMain(WeatherDataList weatherDataList,
			YrWeatherGUI view) {
		this.weatherDataList = weatherDataList;
		this.view = view;
		this.client = new Client();
	}

	private void addbuttonListener() {
		this.view.getRunButton().addActionListener(this);
	}

	/**
	 * This method is run everytime the user presses the compute button aswell
	 * as cointaining the main controller logic.
	 * 
	 * @param e Is passed on from the user when he presses the button for instance.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Possible memory leak.
		// Program in its current state does not remove old requests in memory
		// after their TTL has passed
		String locationName = this.view.getLocationField();
		String hour = this.view.getHourSelectField();
		parser = new ParserClass();
		// just checking if the value typed in to the TTL text field makes any
		// sense
		try {
			TTL = this.view.getTtlField();
		} catch (Exception NumberFormatException) {

			this.view.displayErrorMessage("Invalid TTL input!");
		}

		LocationForecast current = this.weatherDataList
				.getLocationByName(locationName);
		// if current is NULL it means that the location typed in did not exist
		// in places.xml and thus is invalid
		if (current != null) {
			int index = this.weatherDataList.doesExist(current
					.getLocationName());

			if (index != -1 && current.isValid(TTL)) {

				long age = System.currentTimeMillis() / 1000L
						- current.getTimestamp();
				try {
					String out = "CACHED FORECAST SINCE "
							+ Long.toString(age)
							+ " seconds ago.\n TTL: "
							+ TTL
							+ " seconds\n-------------------\n\n"
							+ this.weatherDataList.get(index).getFromTime(hour)
									.toString();

					this.view.setOutput(out);
				} catch (Exception NoSuchElementException) {
					this.view
							.displayErrorMessage("A forecast for the specified hour could not be found. Did you enter a correctly formatted time value?\nAccepted values: 00-23.");
				}

			} else {

				try {

					InputStream is = client.getLocInputstream(
							current.getLatitude(), current.getLongitude());
					ArrayList<Forecast> forecasts = parser.parse2YR(is);
					current.setList(forecasts);

					this.weatherDataList.add(current);

					String out = current.getFromTime(hour).toString();
					this.view.setOutput(out);

				} catch (Exception e1) {
					this.view.displayErrorMessage("Placeholder");
				}
			}
		}

		else {
			this.view
					.displayErrorMessage("Only locations in places.xml are supported!");
		}
	}

	public static void main(String[] args) throws Exception {

		WeatherDataList weatherDataList = new WeatherDataList();
		YrWeatherGUI view = new YrWeatherGUI();
		view.setPlaces(weatherDataList.getPlaces());

		YrWeatherClientMain yrMain = new YrWeatherClientMain(weatherDataList,
				view);

		yrMain.view.createAndShowGUI();

		view.setTtlField((int) TTL);
		view.setOutput("!!!!README!!!!\nSelect a time in hours and place to show temperature for.\nNote:"
				+ " Only locations that are in places.xml are valid. Time is represented\n by a value form 00-23 hours. The value must have\n two digits!");

		yrMain.addbuttonListener();

	}
}