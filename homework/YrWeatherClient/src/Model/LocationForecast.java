package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

/** Representation of a Weather Forecast corresponding to a specific Location and
 * time when the data was retrieved by Forecast provider
 */
public class LocationForecast extends AbstractPointLocationClass {

	private ArrayList<Forecast> list = new ArrayList<Forecast>();
	private Long timestamp;

	public LocationForecast(String locationName, Double longitude,
			Double latitude) {
		super(locationName, longitude, latitude);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Forecast> getList() {
		return list;
	}

	public void setList(ArrayList<Forecast> list) {
		this.list = list;
	}

	public void add(Forecast e) {
		list.add(e);
	}

	public Forecast getForecast(int index) {
		return list.get(index);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Use this to retrieve a forecast for a specified hour 00-23
	 * 
	 * @param hour
	 *            A string that represents a 24-hour format such as 00, 01, 22,
	 *            23.
	 * @return A forecast object that represents that weather at the hour
	 *         specified. If there is no forecast for that hour a
	 *         NoSuchElementException is thrown.
	 */

	public Forecast getFromTime(String hour) throws NoSuchElementException {
		// "2014-09-14T18:00:00Z"
		Calendar c = Calendar.getInstance();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String s = ft.format(dNow);
		c.setTime(dNow);
		c.add(Calendar.DATE, 1); // Increment calendar by one day
		String oneDayLater = ft.format(c.getTime());

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFrom().equals(s + "T" + hour + ":00:00Z")) {
				return list.get(i);
			} else if (list.get(i).getFrom()
					.equals(oneDayLater + "T" + hour + ":00:00Z")) {
				return list.get(i);
			}
		}
		throw new NoSuchElementException();
	}

	/**Check whether this LocationRequest stored in memory is still valid, or if its TTL has passed
	 * 
	 * @param TTL
	 *            is the value for how long this piece of forecast data should
	 *            be valid after it was retrieved from forecast provider in
	 *            seconds LONG.
	 * @return Returns false if (currentTime - timeOfForecastRetrievement) is
	 *         equal or greater than the parameter TTL(time to live)
	 */
	public boolean isValid(long TTL) {

		long currentTime = System.currentTimeMillis() / 1000L;

		if ((currentTime - this.timestamp) >= TTL) {
			return false;
		} else {
			return true;
		}
	}

}