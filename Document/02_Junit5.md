# Junit5について
- JUnit 5は、Javaプログラムの単体テスト（ユニットテスト）を自動化するための最新の標準フレームワークできます。
- ラムダ式やStream、デフォルトメソッドなどのモダンなJava機能をフル活用して、より簡潔にテストを記述できまる。

## 主な違いの比較表

|項目 |JUnit 4 (2006年〜)|JUnit 5 (2017年〜)|
|:----|:----|:----|
|Java要求バージョン|Java 5 以上|Java 8 以上|
|アーキテクチャ|単一のJAR（モノリス型）|モジュール構造（必要な機能のみ導入可）|
|パッケージ名|org.junit|org.junit.jupiter.api|
|拡張機能|@RunWith / @Rule|@ExtendWith (拡張性が向上)|
|後方互換性|なし|あり（VintageエンジンでJUnit 4も動く）|

## アノテーションについて

|JUnit 4 (org.junit.*)|JUnit 5 (org.junit.jupiter.api.*)|内容|
|:----|:----|:----|
|Ignore|Disabled|テスト対象外|
|Before|BeforeEach|テスト実行前の処理|
|After|AfterEach|テスト実行後の処理|
|BeforeClass|BeforeAll|テストクラスのテスト実行前に一度だけ行う処理|
|AfterClass|AfterAll|テストクラスのテスト実行後に一度だけ行う処理|

## アサーションについて

|メソッド |説明|
|:----|:----|
|assertEquals(expected, actual)|期待値と実測値が等しいか検証|
|assertNotEquals(unexpected, actual)|値が等しくないことを検証|
|assertTrue(condition)|条件が**true**であるか検証|
|assertFalse(condition)|条件が**false**であるか検証|
|assertNull(actual)|オブジェクトが**null**であるか検証|
|assertNotNull(actual)|オブジェクトが**null**でないか検証|
|assertSame(expected, actual)|同一のオブジェクト（参照が同じ）か検証|
|assertNotSame(unexpected, actual)|異なるオブジェクト（参照が別）か検証|
|assertThrows(Class<T>,Executable)|処理の実行により、指定された例外が送出されることを検証|
|assertTimeout(Duration,Executable)|処理の実行が指定された時間内に終了することを検証|
|fail()|テストを明示的に失敗させる|

## @DisplayNameアノテーション
- テストクラスやテストメソッドに対してわかりやすい名前を付ける事が可能

```java
public class SampleClassTest{
  @Test
  @DisplayName("条件１を通過するかのテスト")
  void test001(){
    // ．．．
  }
  @Test
  @DisplayName("条件2を通過するかのテスト")
  void test002(){
    // ．．．
  }
}
```

## パラメータ化テスト

- 対象コード

```java
public class Sample04 {
	private static String OUR_BANK_CODE = "B001"; // 銀行コード（自分の銀行）

	// 振込手数料の計算を行う
	public int calcFee(String bankCode, int amount) {
		// 振込先が自分の銀行か判定する
		if (OUR_BANK_CODE.equals(bankCode)) {
			// 自分の銀行の場合、30000円以上は無料、それ以外は100円
			if (30000 <= amount) {
				return 0;
			} else {
				return 100;
			}

		} else {
			// 他の銀行の場合、40000円以上は200円、それ以外は500円
			if (40000 <= amount) {
				return 200;
			} else {
				return 500;
			}
		}
	}
}
```

- リテラル配列からパラメータ取得

```java
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
```

- リストからのパラメータ取得

```java
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
```

- リスト（テスト値・戻り値を分離）からパラメータ取得

```java
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
```


## 参考
- [JUnit4とJUnit5の違い](https://qiita.com/torayamadajp/items/e0c84296105edb472942)
- [learn_java_testing](https://github.com/KenyaSaitoh/learn_java_testing)