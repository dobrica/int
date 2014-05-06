package com.example.restclient;

import java.util.ArrayList;
import java.util.Iterator;

import Util.ColorUtil;
import Util.GraphViewData;
import Util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import beans.Device;
import beans.Element;
import beans.Link;
import beans.PageMessage;
import beans.Record;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;

public class Interpreter {

	public static final String TYPE_BUTTON = "button";
	public static final String TYPE_LABEL = "label";
	public static final String TYPE_TEXTFIELD = "textfield";
	public static final String TYPE_PASSWORDFIELD = "passwordfield";
	public static final String TYPE_EMAILFIELD = "emailfield";
	public static final String TYPE_SPINNER = "spinner";
	public static final String TYPE_GRAPH = "graph";

	// elemText as description
	public static final String TEXT_DEVICE = "device";
	public static final String TEXT_FILTER = "filter";
	
	public static final String ACCESS_REGISTRED = "registred";
	public static final String ACCESS_GUEST = "guest";
	public static final String ACCESS_ALL = "all";
	
	// if there is no registred user token.equals(ACCESS_DENIED)
	public static final String ACCESS_DENIED = "1"; // access code true

	public ArrayList<EditText> textFields;
	public ArrayList<Spinner> spinners;

	public Interpreter() {
		textFields = new ArrayList<EditText>();
		spinners = new ArrayList<Spinner>();
	}

	public void init(LinearLayout layout, final Context context,
		PageMessage pageMessage, final Intent intent) {
		
		if (pageMessage.links.size() > 0) {
		
			layout.removeAllViewsInLayout();
			LinearLayout container = new LinearLayout(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,      
					LinearLayout.LayoutParams.MATCH_PARENT
			);
			container.setOrientation(LinearLayout.VERTICAL);
			params.setMargins(0, pageMessage.getMarginTop(), 0, 0);
			container.setLayoutParams(params);
			
			for (int i = 0; i < pageMessage.elements.size(); i++) {

				final Element elem = pageMessage.elements.get(i);
				final Link lnk = pageMessage.findLinkByRel(elem.getText());
				final String token = pageMessage.getToken();
				EditText textField = new EditText(context);
				textField.setId(i);
				
				switch (elem.getType()) {
				
				case TYPE_BUTTON:
					
					final Button newBtn = new Button(context);
					newBtn.setText(elem.getText());
					newBtn.setId(i);
					newBtn.setTextColor(ColorUtil.getColor(elem.getFcolor()));
					
					newBtn.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							
							intent.putExtra("method", lnk.getMethod());
							intent.putExtra("rel", lnk.getRel());
							
							ArrayList<String> parts= StringUtil.splitUrl(lnk.getHref());
							
							String url = "";
							if (parts.size()>0){
								ArrayList<String> params = getParams(token);
								url = StringUtil.addParamsToUrl(parts, params);
							}else{
								url = lnk.getHref();
							}
							
							intent.putExtra("url", url);
							
							// Fire next activity
							context.startActivity(intent);
						}
					});
					
					if (isLinkAccessible(lnk, token))	
						container.addView(newBtn);
		
					break;
					
				case TYPE_LABEL:
					
					TextView label = new TextView(context);
					label.setTextColor(ColorUtil.getColor(elem.getFcolor()));
					label.setText(elem.getText());
					label.setId(i);
					container.addView(label);
					
					break;
					
				case TYPE_TEXTFIELD:
					
					textField.setTextColor(ColorUtil.getColor(elem.getFcolor()));
					textFields.add(textField);
					container.addView(textField);
					
					break;
		
				case TYPE_PASSWORDFIELD:
					
					textField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					textField.setTextColor(ColorUtil.getColor(elem.getFcolor()));
					textFields.add(textField);
					container.addView(textField);
					
					break;
					
				case TYPE_EMAILFIELD:
					
					textField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
					textField.setTextColor(ColorUtil.getColor(elem.getFcolor()));
					textFields.add(textField);
					container.addView(textField);
					
					break;
					
				case TYPE_GRAPH:
					
					ArrayList<Record> records = new ArrayList<Record>();
					
					for (Iterator<Device> iter = pageMessage.getDevicesIter(); iter.hasNext();) {
						Device dev = (Device) iter.next();
						for (Iterator<Record> it = dev.getRecordsIter(); it.hasNext();) {
							Record record = (Record) it.next();
							records.add(record);
						}
					}
					
					GraphViewData[] graphViewData = new GraphViewData[records.size()];
					int counter = 1;
					for (int j = 0; j < records.size(); j++) {
						graphViewData[j] = new GraphViewData(counter++, records.get(j).getTemperature());
					}
					
					
					GraphViewSeries exampleSeries = new GraphViewSeries("description",null, graphViewData);
					GraphView graphView = new BarGraphView (context, "Temperature");
					//data
					graphView.addSeries(exampleSeries);

					graphView.getGraphViewStyle().setGridColor(Color.BLACK);
					graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
					graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
					graphView.getGraphViewStyle().setVerticalLabelsWidth(80);

					container.addView(graphView);
					
					break;
				case TYPE_SPINNER:
					
					Spinner spinner = new Spinner(context);
					spinner.setId(i);
					ArrayList<String> spinnerArray = new ArrayList<String>();
					ArrayAdapter<String> dataAdapter = null;
					
					if (elem.getText().equals(TEXT_DEVICE)) {

						spinnerArray = new ArrayList<String>();
						for (Iterator<Device> iterator = pageMessage.getDevicesIter(); iterator.hasNext();) {
							Device d = (Device) iterator.next();
							spinnerArray.add(d.getName());
						}
						
						dataAdapter = new ArrayAdapter<String>(context,
								android.R.layout.simple_list_item_1, spinnerArray);

						spinner.setAdapter(dataAdapter);
						spinners.add(spinner);
						container.addView(spinner);
						
					} else if (elem.getText().equals(TEXT_FILTER)) {

						spinnerArray = new ArrayList<String>();
						for (Iterator<String> iterator = pageMessage.getFiltersIter(); iterator.hasNext();) {
							String filter = (String) iterator.next();
							spinnerArray.add(filter);
						}
						
						dataAdapter = new ArrayAdapter<String>(context,
								android.R.layout.simple_list_item_1, spinnerArray);
						
						spinner.setAdapter(dataAdapter);
						spinners.add(spinner);
						container.addView(spinner);
					}else{
						Toast.makeText(context, "unexpected spinner type", Toast.LENGTH_LONG).show();
					}
					
					break;
					
				default:
					break;
				}
			}
			layout.addView(container);
		}else{
			Toast.makeText(context, "no links to follow", Toast.LENGTH_LONG).show();
		}
			
	
	}
	
	public ArrayList<String> getParams(String token){
		/* all params from textfields, spinners and token are put to arraylist
		 * gets them in order they are going to be added to link for now */
		ArrayList<String> params= new ArrayList<String>();
		params.add(token);
		
		if (textFields.size() > 0) {
			for (EditText edf : textFields)
				params.add(edf.getText().toString());
			
		} else if (spinners.size() > 0) {
			for (Spinner s : spinners)
				params.add(s.getSelectedItem().toString());
		}
		
		return params;
	}
	
	public boolean isLinkAccessible(Link lnk, String token){
		
		if ( (lnk.getAccess().equals(ACCESS_REGISTRED) && !token.equals(ACCESS_DENIED)) || // logged on user
			 (lnk.getAccess().equals(ACCESS_GUEST) && token.equals(ACCESS_DENIED)) || // not logged on
			 (lnk.getAccess().equals(ACCESS_ALL)) ) // for everybody
			return true;
		return false;
	}
}
