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
