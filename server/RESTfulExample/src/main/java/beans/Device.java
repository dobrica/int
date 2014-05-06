package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import utility.DateCompare;

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
		generateRecords();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Iterator<Record> getRecordsIter() {
		return records.iterator();
	}

	public void addRecord(Record r) {
		records.add(r);
	}

	public void generateRecords() {
		Random rand = new Random();
		for (int i = 1; i < rand.nextInt(100); i++) {
			records.add(new Record());
		}
	}

	public void sortRecords() {
		Collections.sort(records, new DateCompare());
	}

	public ArrayList<Record> sortRecordsByCriteria(String criteria) {
		ArrayList<Record> sorted = new ArrayList<Record>();

		Date max = new Date();
		Calendar cal = Calendar.getInstance();
		switch (criteria) {
		case "lastday":
			cal.add(Calendar.DATE, -1);
			break;
		case "lastweek":
			cal.add(Calendar.DATE, -7);
			break;
		case "lastmonth":
			cal.add(Calendar.DATE, -30);
			break;
		case "last3montns":
			cal.add(Calendar.DATE, -90);
			break;
		case "last6montns":
			cal.add(Calendar.DATE, -180);
			break;
		case "lastyear":
			cal.add(Calendar.DATE, -365);
			break;
		default:
			break;
		}

		Date min = cal.getTime();
		for (Record record : records) {
			if (record.inBetween(min, max, record.getDatetime()))
				sorted.add(record);
		}
		Collections.sort(sorted, new DateCompare());
		return sorted;
	}

	public String recordsToJson(ArrayList<Record> records) {
		if (records == null)
			records = this.records;
		String res = ",\"_records\": [ ";
		for (Record r : records) {
			res += r.toJSON() + ",";
		}
		res = res.substring(0, res.length() - 1);
		res += "]";
		return res;
	}

	public String toJSON(ArrayList<Record> records) {
		if (records == null)
			records = this.records;
		return "{\"name\":\"" + name + "\"" + recordsToJson(records) + "}";
	}

}
