package unitreferenc.sample;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Sample03Test {

	@Test
	@DisplayName("Calender・LocalDateTimeをMock")
	void test001() throws Exception {
		
		//Mock：Calender
		try(MockedStatic<Calendar> mockCal = mockStatic(Calendar.class,Mockito.CALLS_REAL_METHODS)){
			mockCal.when(Calendar::getInstance).thenAnswer(Invocation ->{
				Calendar newCal = new GregorianCalendar();
				newCal.set(2025, Calendar.JANUARY,2,14,30,00);
				newCal.set(Calendar.MILLISECOND, 0);
				return newCal;
			});
			//Mock：LocalDateTime
			try(MockedStatic<LocalDateTime> mockLocalDate = mockStatic(LocalDateTime.class,Mockito.CALLS_REAL_METHODS)){
				LocalDateTime fakeNow = LocalDateTime.of(2025,1,2,14,30,0);
				mockLocalDate.when(LocalDateTime::now).thenReturn(fakeNow);
				
				Sample03 sample03 = new Sample03();
				
				// privatメソッドを可視化
				Method method = sample03.getClass().getDeclaredMethod("sampleCalDate", null);
				method.setAccessible(true);
				
				List<String> res = (List<String>) method.invoke(sample03, null);
				
				// 検証
				List<String> act = List.of("2025-01-02","14:30");
				assertEquals(res, act);
				
			}	
		}
		
	}
}
