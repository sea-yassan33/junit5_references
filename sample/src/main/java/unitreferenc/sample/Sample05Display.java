package unitreferenc.sample;

public class Sample05Display {

	private Sample05Info info = new Sample05Info();

	public String getUserInfoString(String userId) {
		String name = info.getName(userId);
		String gender = info.getGender(userId);
		String old = info.getOld(userId);

		return name + "(" + gender + ") " + old + "歳";
	}
}
