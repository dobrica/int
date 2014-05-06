package com.example.restclient;

import Util.UrlDrawableResource;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import beans.PageMessage;

public class MainActivity extends Activity {

	public PageMessage pageMessage;
	public ServiceHandler sh;
	public String result;
	public Drawable drw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		new Connection().execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class Connection extends AsyncTask<String, Integer, PageMessage> {
		protected PageMessage doInBackground(String... urls) {
			sh = new ServiceHandler();
			// BASE_URL
			String url = "http://10.0.3.2:8080/rest/home";
			result = sh.makeServiceCall(url, 1);
			
			// parsing
			Parser p = new Parser();
			pageMessage = p.parse(result);

			drw = UrlDrawableResource.ImageOperations(MainActivity.this, pageMessage.getBackgroundUrl(),"background");
			
			return pageMessage;
		}

		
		@SuppressWarnings("deprecation")
		protected void onPostExecute(PageMessage result) {
			
			Interpreter i = new Interpreter();
			Intent intent = new Intent(MainActivity.this, NextActivity.class);
			LinearLayout layout = (LinearLayout) findViewById(R.id.llayout);
			layout.setBackgroundDrawable(drw);
			i.init(layout, MainActivity.this, pageMessage, intent);
			
		}
	}

}