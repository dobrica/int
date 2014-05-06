package com.example.restclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Device;
import beans.Element;
import beans.Link;
import beans.PageMessage;
import beans.Record;

public class Parser {

	public PageMessage pageMessage;

	// JSON Node names
	public static final String TAG_TITLE = "title";
	public static final String TAG_TOKEN = "param";
	public static final String TAG_LINKS = "_links";
	public static final String TAG_HREF = "href";
	public static final String TAG_REL = "rel";
	public static final String TAG_METHOD = "method";
	public static final String TAG_ACCESS = "access";
	public static final String TAG_BACKGROUND = "background";
	public static final String TAG_MARGINTOP = "margintop";

	public static final String TAG_ELEMENTS = "_elements";
	public static final String TAG_TEXT = "text";
	public static final String TAG_TYPE = "type";
	public static final String TAG_FCOLOR = "fcolor";
	public static final String TAG_BCOLOR = "bcolor";
	public static final String TAG_BCKGA = "bckga";

	public static final String TAG_DEVICES = "_devices";
	public static final String TAG_NAME = "name";

	public static final String TAG_RECORDS = "_records";
	public static final String TAG_DATETIME = "datetime";
	public static final String TAG_TEMP = "temperature";

	public static final String TAG_FILTERS = "_filters";
	public static final String TAG_SHOW = "show";

	// JSONArrays
	public JSONArray links = null;
	public JSONArray elements = null;
	public JSONArray devices = null;
	public JSONArray records = null;
	public JSONArray filters = null;

	public PageMessage parse(String result) {
		JSONObject json = null;

		// parsing
		try {
			json = new JSONObject(result);
			pageMessage = new PageMessage();
			if (json != null) {

				// Getting JSON Array node
				links = json.getJSONArray(TAG_LINKS);
				String title = json.getString(TAG_TITLE);
				pageMessage.setTitle(title);
				String token = json.getString(TAG_TOKEN);
				pageMessage.setToken(token); // use this as param for auth.
				String backgroundUrl = json.getString(TAG_BACKGROUND);
				pageMessage.setBackgroundUrl(backgroundUrl);
				String mTop = json.getString(TAG_MARGINTOP);
				int marginTop = Integer.parseInt(mTop);
				pageMessage.setMarginTop(marginTop);

				for (int i = 0; i < links.length(); i++) {
					JSONObject lnk = links.getJSONObject(i);
					String href = lnk.getString(TAG_HREF);
					String rel = lnk.getString(TAG_REL);
					String method = lnk.getString(TAG_METHOD);
					String access = lnk.getString(TAG_ACCESS);

					Link link = new Link();
					link.setHref(href);
					link.setRel(rel);
					link.setMethod(method);
					link.setAccess(access);
					pageMessage.addToLinks(link);
				}

				elements = json.getJSONArray(TAG_ELEMENTS);
				for (int i = 0; i < elements.length(); i++) {
					JSONObject el = elements.getJSONObject(i);
					String type = el.getString(TAG_TYPE);
					String text = el.getString(TAG_TEXT);
					String fcolor = el.getString(TAG_FCOLOR);
					String bcolor = el.getString(TAG_BCOLOR);
					String bckga = el.getString(TAG_BCOLOR);

					Element element = new Element();
					element.setType(type);
					element.setText(text);
					element.setBcolor(bcolor);
					element.setFcolor(fcolor);
					element.setBckga(bckga);
					pageMessage.addToElements(element);
				}

				filters = json.getJSONArray(TAG_FILTERS);
				for (int i = 0; i < filters.length(); i++) {
					JSONObject el = filters.getJSONObject(i);
					String show = el.getString(TAG_SHOW);

					pageMessage.addToFilters(show);
				}

				devices = json.getJSONArray(TAG_DEVICES);
				for (int i = 0; i < devices.length(); i++) {
					JSONObject el = devices.getJSONObject(i);
					String name = el.getString(TAG_NAME);

					Device d = new Device(name);
					pageMessage.addToDevices(d);

					records = el.getJSONArray(TAG_RECORDS);
					for (int j = 0; j < records.length(); j++) {
						JSONObject rec = records.getJSONObject(j);
						String datetime = rec.getString(TAG_DATETIME);
						String temp = rec.getString(TAG_TEMP);
						Float temperature = Float.parseFloat(temp);

						pageMessage.findDeviceByName(name).addRecord(
								new Record(datetime, temperature));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pageMessage;
	}
}
