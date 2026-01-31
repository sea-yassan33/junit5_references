package unitreferenc.sample;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Sample01Test extends Sample01 {
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		// 標準出力をキャプチャ用のストリームに切り替える
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	public void tearDown() {
		// テスト終了後に元に戻す
		System.setOut(standardOut);
	}

	@Test
	@DisplayName("【正常系】通常実行")
	void test000() {
		// mainメソッドを実行
		Sample01.main(new String[] {});

		// 出力された内容を文字列として取得（トリムして改行などの差異を吸収）
		String output = outputStreamCaptor.toString().trim();

		// 期待される出力: [A,B,C,D,E,F] を逆順にして 3つ取得 = [F, E, D]
		assertTrue(output.contains("【当選者】：[F, E, D]"));
	}

	@Test
	@DisplayName("【正常系】指定した人数分だけ当選者が選択できること")
	void test001() {
		List<String> items = List.of("A", "B", "C", "D", "E");
		int n = 3;

		List<String> results = Sample01.drawWinners(items, n);

		assertEquals(n, results.size(), "当選人数が一致すること");
		assertTrue(items.containsAll(results), "当選者は元のリストに含まれていること");
	}

	@Test
	@DisplayName("【異常系】nが0以下の場合、IllegalArgumentExceptionを投げること")
	void test002() {
		List<String> items = List.of("A", "B");

		assertThrows(IllegalArgumentException.class, () -> Sample01.drawWinners(items, 0),
				"抽選人数は1人以上にして下さい。");
		assertThrows(IllegalArgumentException.class, () -> Sample01.drawWinners(items, -1));
	}

	@Test
	@DisplayName("【異常系】nが参加者数より多い場合、IllegalArgumentExceptionを投げること")
	void test003() {
		List<String> items = List.of("A", "B");
		int n = 3;

		assertThrows(IllegalArgumentException.class, () -> Sample01.drawWinners(items, n),
				"抽選人数は参加者数を超えています。");
	}

	@Test
	@DisplayName("【privateメソッド】 内部で正常に動くか検証")
	void test04() throws Exception {
		// メソッドを取得
		Method method = Sample01.class.getDeclaredMethod("subListMethod", List.class, int.class);
		// privateメソッドへのアクセスを許可
		method.setAccessible(true);
		List<String> input = List.of("X", "Y", "Z");
		// staticメソッドなので第一引数は null
		List<String> result = (List<String>) method.invoke(null, input, 2);
		assertEquals(List.of("X", "Y"), result);
	}

	@Test
	@DisplayName("【staticメソッド】Mockした検証")
	void test05() {
		// テストデータ
		List<String> items = List.of("A", "B", "C");
		int num = 1;

		// ダミーデータ
		List<String> expectedMockResult = List.of("A", "C");

		try (MockedStatic<Sample01> mockedSample02 = mockStatic(Sample01.class)) {
			// drawWinnersは「本物」を動かし、reverseListWithStreamだけを「Mock」にする設定
			mockedSample02.when(() -> Sample01.drawWinners(anyList(), anyInt())).thenCallRealMethod();
			mockedSample02.when(() -> Sample01.reverseListWithStream(anyList(), anyInt()))
					.thenReturn(expectedMockResult);

			// 実行
			List<String> actual = Sample01.drawWinners(items, num);

			// 検証
			assertEquals(expectedMockResult, actual);
			// reverseListWithStreamが正しく呼ばれたか検証
			mockedSample02.verify(() -> Sample01.reverseListWithStream(items, num));
		}
		
	}
}
