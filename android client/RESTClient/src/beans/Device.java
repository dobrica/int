package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Device implements Serializable {
	
	/**
	 * 
	 */
	private transient static final long serialVersionUID = 1331584213721033997L;
	
	public String name;
	public ArrayList<Record> records;
	
	public Device(String name) {
		this.name = name;
		records = new ArrayList<Record>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<Record> getRecordsIter(){
		return records.iterator();
	}
	
	public void addRecord(Record r){
		records.add(r);
	}
	
	public String toJSON(){
		return "{\"name\":\""+name+"\""+"}";
	}
	
}
