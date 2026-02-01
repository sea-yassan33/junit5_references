package unitreferenc.sample;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Sample06Test {
	
	@Mock
	Sample06 sample06;
	
	@InjectMocks
	Sample06Service sample06Service;

	@Test
	@DisplayName("voidメソッドのMockテスト")
	void test001() {
		// voidメソッドは doNothing を使う
		doNothing().when(sample06).send(anyString());

		sample06Service.sampleOrder("A001");

		// 呼ばれたことだけを検証
		verify(sample06).send("order placed: A001");
	}

}
