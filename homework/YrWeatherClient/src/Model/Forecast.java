package Model;

/**
 * Representation of a typical Weather Forecast from YR.no with no regards to
 * location
 */

public class Forecast extends AbstractWeatherClass {

	/**Initiates a Forecast object
	 * 
	 * @param temperature The temperate of the predicted weather
	 * @param to what time period is the forecast prediction accurate
	 * @param from what time is the forecast prediction accurate
	 */
	public Forecast(double temperature, String to, String from) {
		super(temperature, to, from);
		// TODO Auto-generated constructor stub
	}

}
