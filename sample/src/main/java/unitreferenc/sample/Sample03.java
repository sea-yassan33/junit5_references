package unitreferenc.sample;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Sample03 {
	
	public static void main(String[] args) {
		List<String> dateList = new ArrayList<>();
		
		dateList = sampleCalDate();
		
		System.out.println(dateList);
	}
	
	private static final List<String> sampleCalDate() {
		List<String> dateList = new ArrayList<>();
		
		// calendarから文字列へ
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		String calDateStr = sdf.format(calendar.getTime());
		
		// LocalDateから文字列へ
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("HH:mm");
		
		// 格納
		dateList.add(calDateStr);
		dateList.add(now.format(formatDate));
		
		return dateList;
		
	}

}
