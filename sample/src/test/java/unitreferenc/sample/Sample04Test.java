package unitreferenc.sample;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("legacy-test")
class Sample04Test extends Sample04 {

	private String originalCode;

	@BeforeEach
	void setUp() throws Exception {
		// privatを可視化
		Field field = Sample04.class.getDeclaredField("OUR_BANK_CODE");
		field.setAccessible(true);
		// 元の値を退避
		originalCode = (String) field.get(null);
		// テスト用に差し替え
		field.set(null, "TEST");
	}

	@AfterEach
	void tearDown() throws Exception {
		// 元に戻す
		Field field = Sample04.class.getDeclaredField("OUR_BANK_CODE");
		field.setAccessible(true);
		field.set(null, originalCode);
	}

	@Test
	@DisplayName("privatの変数を変えてテスト")
	void test001() {
		Sample04 target = new Sample04();

		assertEquals(0, target.calcFee("TEST", 30000));
		assertEquals(100, target.calcFee("TEST", 10000));
	}

}
