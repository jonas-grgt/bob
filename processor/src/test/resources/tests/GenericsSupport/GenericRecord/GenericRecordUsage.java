package io.jonasg.bob.test;

public class GenericRecordUsage {
	public static String run() {
		GenericRecord<String> built = new GenericRecordBuilder<String>().value("abc").build();
		String value = built.value();
		return value + "/" + value.compareTo("abd");
	}
}
