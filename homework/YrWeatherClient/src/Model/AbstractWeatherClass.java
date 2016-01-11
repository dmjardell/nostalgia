package Model;

abstract class AbstractWeatherClass {

	public AbstractWeatherClass(double temperature, String to, String from) {
		super();
		this.temperature = temperature;
		this.to = to;
		this.from = from;
	}

	private double temperature;
	private String to, from;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "Temperature= " + temperature + " celsius\nTo= " + to
				+ "\nFrom= " + from;
	}

}
