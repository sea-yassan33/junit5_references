package unitreferenc.sample;

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
