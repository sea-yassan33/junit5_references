package unitreferenc.sample;

public class Sample06 {

	public void send(String message) {
		// 本来は外部APIやメール送信など副作用がある処理
		System.out.println("send message: " + message);
	}

}
