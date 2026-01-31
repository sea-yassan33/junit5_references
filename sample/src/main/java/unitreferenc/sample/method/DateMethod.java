package unitreferenc.sample.method;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMethod {
	
	private DateMethod() {
		// インスタンス生成なし
	}

	public static Date getSystemDate() {
		return new Date();
	}
	
	public static String convDataStr(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date convStrDate(String text, String format) {
		
		if (text == null || text.equals("")) {
			return null;
		}
		
		Date date =null;
		
		text= text.trim();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(text);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		return date;
	}
}
