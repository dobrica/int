package beans;

import java.io.Serializable;

public class Record implements Serializable {

	/**
	 * 
	 */
	private transient static final long serialVersionUID = 4304220573985117452L;
	
	public String datetime;
	public Float temperature;

	public Record(String datetime, Float temperature) {
		this.datetime = datetime;
		this.temperature = temperature;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

}
