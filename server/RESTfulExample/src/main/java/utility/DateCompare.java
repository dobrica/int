package utility;

import java.util.Comparator;

import beans.Record;

public class DateCompare implements Comparator<Record> {

	@Override
	public int compare(Record o1, Record o2) {
		return o1.getDatetime().compareTo(o2.getDatetime());
	}
}
