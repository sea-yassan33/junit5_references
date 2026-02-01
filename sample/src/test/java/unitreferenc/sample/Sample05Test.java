package unitreferenc.sample;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Sample05Test {
	
	@Mock
	private Sample05Info mockInfo;
	private AutoCloseable closeable; //メモリーリーク防止やテストに影響が生じない様に管理
	
	@InjectMocks
	private Sample05Display target;
	
	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void close() throws Exception{
		closeable.close();
	}

	@Test
	@DisplayName("Mockの基本的な使い方")
	void test001() {
		when(mockInfo.getName(anyString())).thenReturn("義堂合六");
		when(mockInfo.getGender(anyString())).thenReturn("女");
		when(mockInfo.getOld(anyString())).thenReturn("22");
		String result = target.getUserInfoString("test");

		assertEquals("義堂合六(女) 22歳", result);
	}

}
