package unitreferenc.sample;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Sample04_2 {

	@ParameterizedTest(name = "【testNo-{index}】 {arguments}")
	@ValueSource(strings = { "B001,30000,0", "B001,29999,100", "B999,40000,200", "B999,39999,500" })
	@DisplayName("リテラル配列からパラメータ取得")
	void test001(String paramStr) {
		String[] param = paramStr.split(",");
		Sample04 sample04 = new Sample04();

		//
		int result = sample04.calcFee(param[0], Integer.parseInt(param[1]));
		// 検証フェーズ
		assertEquals(Integer.parseInt(param[2]), result);
	}
	
	@ParameterizedTest(name = "【testNo-{index}】 {arguments}")
	@MethodSource("testData")
	@DisplayName("リストからパラメータ取得")
	void test002(List<String> params) {
		String bankCode = params.get(0);
		int amount = Integer.parseInt(params.get(1));
		int expected = Integer.parseInt(params.get(2));

		Sample04 target = new Sample04();
		int result = target.calcFee(bankCode, amount);

		assertEquals(expected, result);
	}

	static Stream<Arguments> testData() {
		return Stream.of(
				Arguments.of(List.of("B001", "30000", "0")),
				Arguments.of(List.of("B001", "29999", "100")),
				Arguments.of(List.of("B999", "40000", "200")),
				Arguments.of(List.of("B999", "39999", "500")));
	}
	
	@ParameterizedTest(name = "【testNo-{index}】 {arguments}")
	@MethodSource("testData02")
	@DisplayName("リスト（テスト値・戻り値を分離）からパラメータ取得")
	void test003(List<String> input, int expected) {
		String bankCode = input.get(0);
		int amount = Integer.parseInt(input.get(1));

		Sample04 target = new Sample04();
		int result = target.calcFee(bankCode, amount);

		assertEquals(expected, result);
	}

	static Stream<Arguments> testData02() {
		return Stream.of(
				Arguments.of(List.of("B001", "30000"), 0),
				Arguments.of(List.of("B001", "29999"), 100),
				Arguments.of(List.of("B999", "40000"), 200),
				Arguments.of(List.of("B999", "39999"), 500));
	}

}
