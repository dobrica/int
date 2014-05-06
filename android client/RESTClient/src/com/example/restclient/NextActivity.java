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

public class NextActivity extends Activity {


	public PageMessage pageMessage;
	public ServiceHandler sh;
	public String result;
	public Drawable drw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_next);
		//Get the bundle
	    Bundle bundle = getIntent().getExtras();
	    //Extract the data…
	    String url = bundle.getString("url");
	    String methodStr = bundle.getString("method");
	    // String[] args params array from bundle needed for service call  
	    String[] args = {url, methodStr};
		new Connection().execute(args);
	}
	
	@Override
	public void onBackPressed() { // android back button(hardware)
		Bundle bundle = getIntent().getExtras();
		String lastVisited = bundle.getString("rel");
		// hack: to preserve token after certain events, for now
		// better, new solution (REST): backTo link for every request  
		if (lastVisited.equals("Submit")) {
			String[] args = {"http://10.0.3.2:8080/rest/home?token="+pageMessage.getToken(), "1"};
			new Connection().execute(args);
		}else if (lastVisited.equals("Logoff")) {
			String[] args = {"http://10.0.3.2:8080/rest/home?token=1", "1"};
			new Connection().execute(args);
		}else{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.next, menu);
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
	
	private class Connection extends AsyncTask<String, Object, PageMessage> {

		protected PageMessage doInBackground(String... args) {
			/* args from bundle for (NextActivity intent)
			 * serviceCall as asyncTask (new Thread)
			 * return value pageMessage*/
			sh = new ServiceHandler();
			String url = args[0];
			String methodStr = args[1];
		    Integer method = Integer.parseInt(methodStr);
		    
		    result = sh.makeServiceCall(url, method);
		    
			Parser p = new Parser();
			pageMessage = p.parse(result);
			drw = UrlDrawableResource.ImageOperations(NextActivity.this, pageMessage.getBackgroundUrl(),"background");
			
			return pageMessage;
		}

		
		@SuppressWarnings("deprecation")
		protected void onPostExecute(PageMessage result) {
			
            Interpreter i = new Interpreter();
    		Intent intent = new Intent(NextActivity.this, NextActivity.class);
    		LinearLayout layout = (LinearLayout) findViewById(R.id.llayout);
    		i.init(layout, NextActivity.this, pageMessage, intent);
    		layout.setBackgroundDrawable(drw);
		}
	}

}
