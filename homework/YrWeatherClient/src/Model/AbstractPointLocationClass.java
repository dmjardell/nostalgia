package Model;

abstract class AbstractPointLocationClass {

	public AbstractPointLocationClass(String locationName, Double longitude,
			Double latitude) {
		super();
		this.locationName = locationName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	private String locationName;
	private Double longitude, latitude;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
