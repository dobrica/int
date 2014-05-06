package Util;

import android.graphics.Color;

public class ColorUtil {
	
	public static int getColor(String color){
		switch (color) {
		case "WHITE": 
			return Color.WHITE;
		case "BLACK": 
			return Color.BLACK;
		case "BLUE": 
			return Color.BLUE;
		case "GREEN": 
			return Color.GREEN;
		case "GRAY": 
			return Color.GRAY;
		case "RED": 
			return Color.RED;
		case "CYAN": 
			return Color.CYAN;
		case "DKGRAY": 
			return Color.DKGRAY;
		case "LTGRAY": 
			return Color.LTGRAY;
		case "MAGENTA": 
			return Color.MAGENTA;
		case "TRANSPARENT": 
			return Color.TRANSPARENT;
		case "YELLOW": 
			return Color.YELLOW;
		default:
			return Color.BLACK;

		}
	}
}
