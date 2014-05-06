package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

public class Record implements Serializable, Comparator<Date>{

	/**
	 * 
	 */
	private transient static final long serialVersionUID = 4304220573985117452L;
	
	public Date datetime;
	public Float temperature;

	public Record() {
		
		this.datetime = new Date();
		this.temperature = temp();
	}
	
	public Record(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		try {
			this.datetime = sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.temperature = temp();
	}

	public String getDatetimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm");
		String date = sdf.format(datetime); 
		return date;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Float getTemperature() {
		return temperature;
	}
	
	public Float temp(){
		float minX = -20.0f;
		float maxX = 40.0f;
		Random rand = new Random();
		float finalX = rand.nextFloat() * (maxX - minX) + minX;
		return finalX;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	
	public boolean inBetween(Date min, Date max, Date date){
		return date.after(min) && date.before(max);
	}

	public String toJSON(){
		return "{\"datetime\":\""+getDatetimeString()+"\", \"temperature\":\""+temperature+"\""+"}";
	}

	@Override
	public int compare(Date o1, Date o2) {
		return o1.compareTo(o2);
	}

}
