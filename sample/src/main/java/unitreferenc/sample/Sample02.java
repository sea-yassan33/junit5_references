package unitreferenc.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sample02 {
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
		
		//　本日の日付を取得
		
		
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
