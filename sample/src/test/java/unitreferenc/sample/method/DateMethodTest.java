package unitreferenc.sample.method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateMethodTest {

	@Test
	@DisplayName("DateMethod内のmock")
	void test001() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.JANUARY,2,14,30,00);
		
		// DateMethodをmock
		try(MockedStatic<DateMethod> mockDateMethod = mockStatic(DateMethod.class, Mockito.CALLS_REAL_METHODS)){
			mockDateMethod.when(() -> DateMethod.convDataStr(any(Date.class), anyString())).thenReturn("17:00");
			
			String result = DateMethod.convDataStr(DateMethod.getSystemDate(), "HH:mm");
			
			assertEquals("17:00", result);
		}
		
	}
}
