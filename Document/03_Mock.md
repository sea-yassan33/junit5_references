# Mockitoについて

- Mockitoはテスト対象プログラムが外に向けてコミュニケーションを行い、何らかの副作用を発生させる疑似プログラム

## PublicメソッドのMock 

- 対象コード

```java
// 【テスト対象】
public class Sample05Display {

	private Sample05Info info = new Sample05Info();

	public String getUserInfoString(String userId) {
		String name = info.getName(userId);
		String gender = info.getGender(userId);
		String old = info.getOld(userId);
		return name + "(" + gender + ") " + old + "歳";
	}
}

public class Sample05Info {
	public String getName(String id) {
		String name = "デフォルト";
		return name;
	}
	public String getGender(String sexId) {
		String gender = "デフォルト";
		return gender;
	}
	public String getOld(String sexId) {
		String old = "デフォルト";
		return old;
	}
}

```

- テストコード

```java
@ExtendWith(MockitoExtension.class) //Mockの際は必須
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
```

## voidメソッドのMock

- 対象コード

```java
// 【対象コード】

public class Sample06 {

	public void send(String message) {
		// 本来は外部APIやメール送信など副作用がある処理
		System.out.println("send message: " + message);
	}
}

public class Sample06Service {

	private final Sample06 sample06;

	public Sample06Service(Sample06 sample06) {
		this.sample06 = sample06;
	}

	public void sampleOrder(String orderId) {
		// 何らかの業務処理
		if (orderId == null) {
			throw new IllegalArgumentException("orderId is null");
		}
		// voidメソッド呼び出し
		sample06.send("order placed: " + orderId);

	}
}
```

- テストコード

```java
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
```

## staticメソッドのMock

- 対象コード

```java
public class Sample01 {
	public static void main(String[] args) {
		/**
		 * 複数当選とバリデーション付き
		 */
		List<String> items = List.of("A", "B", "C", "D", "E", "F");
		int n = 3;
		List<String> winners = drawWinners(items, n);
		System.out.println("【当選者】：" + winners);
	}

	public static List<String> drawWinners(List<String> items, int n) {
		// バリデーション
		if (n <= 0) {
			throw new IllegalArgumentException("抽選人数は1人以上にして下さい。");
		}
		if (n > items.size()) {
			throw new IllegalArgumentException("抽選人数は参加者数を超えています。");
		}
		// 逆順した物を返す
		List<String> resultList = reverseListWithStream(items, n);
		return resultList;
	}

	public static List<String> reverseListWithStream(List<String> sampleList, int n) {
		List<String> newList = sampleList.stream().collect(Collectors.toList());
		Collections.reverse(newList);

		return subListMethod(newList, n);
	}

	private static List<String> subListMethod(List<String> sampleList, int n) {
		List<String> tempList = new ArrayList<>(sampleList);
		return tempList.subList(0, n);
	}
}
```

- テストコード

```java
	@Test
	@DisplayName("【staticメソッド】Mockした検証")
	void test05() {
		// テストデータ
		List<String> items = List.of("A", "B", "C");
		int num = 1;

		// ダミーデータ
		List<String> expectedMockResult = List.of("A", "C");

		try (MockedStatic<Sample01> mockedSample01 = mockStatic(Sample01.class, Mockito.CALLS_REAL_METHODS)) {
			mockedSample01.when(() -> Sample01.reverseListWithStream(anyList(), anyInt()))
					.thenReturn(expectedMockResult);

			// 実行
			List<String> actual = Sample01.drawWinners(items, num);

			// 検証
			assertEquals(expectedMockResult, actual);
			// reverseListWithStreamが正しく呼ばれたか検証
			mockedSample01.verify(() -> Sample01.reverseListWithStream(items, num));
		}	
	}
```

## staticメソッドの応答設定

- 応答設定はmockStaticの第2引数などで指定出来ます。

|設定名（org.mockito.Answers） |動作の内容|
|:----|:----|
|RETURNS_DEFAULTS|デフォルト設定。スタブ化していないメソッドは、参照型なら null、基本データ型なら 0 や false を返します。|
|CALLS_REAL_METHODS|スタブ化していないメソッドは、実際の実装（本物のメソッド）を呼び出します。スパイ（Spy）に近い挙動になります。|
|RETURNS_SMART_NULLS|null を返す代わりに、未定義のメソッドが呼ばれたことを示す例外を投げやすい「スマートなNull」を返し、デバッグを容易にします。|
|RETURNS_MOCKS|スタブ化していないメソッドがオブジェクトを返す場合、その返り値を自動的にモック化して返します（連鎖的なモック作成）。|
|RETURNS_DEEP_STUBS|メソッドチェーン（例：A.getB().getC()）のテストを容易にするため、深い階層まで自動的にスタブ化します。|
|RETURNS_SELF|ビルダーパターンなどで自分自身（this）を返すメソッドに対し、自動的にそのモック自身を返します。|

## privateメソッドのテスト方法

- privateメソッドではMockが出来ない為、FieldクラスでPrivateメソッドを可視化してテストを実施します。

```java
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
```

## Calender・LocalDateTimeをMockしてテスト

- 対象コード

```java
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
```

- テーストコード

```java
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
```

## 他のパッケージのenumを取り出す方法

```java
package sample.utils;

public class SampleUtils {
	// sample定数
	enum SampleName{
		// 運動
		EXERCISE,
		// 食事
		MEAL,
		// 睡眠
		SLEEP
	}
}
```

- テストコード

```java
package unitreferenc.sample.utils;

import org.junit.jupiter.api.Test;

class SampleUtilsTest extends SampleUtils {

	@Test
	void test() throws Exception {
		Class<?> enumClass = Class.forName("unitreferenc.sample.utils.SampleUtils$SampleName");
		Object exercise = Enum.valueOf((Class<Enum>) enumClass, "EXERCISE");
		
		System.out.println(exercise);
	}

}
```