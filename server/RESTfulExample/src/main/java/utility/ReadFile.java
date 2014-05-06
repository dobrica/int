package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadFile {
	
	private static Reader r;
	private static FileInputStream fis;
	
	public static String getFileContent(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		fis  = new FileInputStream(file);
		r = new InputStreamReader(fis, "UTF-8");
		char[] buf = new char[1024];
		int amt = r.read(buf);
		while (amt > 0) {
			sb.append(buf, 0, amt);
			amt = r.read(buf);
		}
		return sb.toString();
	}

}
