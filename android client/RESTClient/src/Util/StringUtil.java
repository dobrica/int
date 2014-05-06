package Util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringUtil {
	
	// returns full url with parameters
	public static ArrayList<String> splitUrl(String url){
		
		ArrayList<String> parts = new ArrayList<String>();
		// splits url by special character *
		StringTokenizer tokenizer = new StringTokenizer(url, "*");
		while (tokenizer.hasMoreElements()) {
			parts.add(tokenizer.nextElement().toString());
		}
		
		return parts;
	}
	
	public static String addParamsToUrl(ArrayList<String> parts, ArrayList<String> params){
		String result = "";
		/* add value of parameter instead of * , url params order is relevant*/
		for (int i = 0; i < parts.size(); i++) {
			result+=parts.get(i)+params.get(i);
		}
		
		return result;
	}
	
}
