package io.jonasg.bob;

public final class Formatter {

	public static String format(String source, Object... args) {
		return String.format(source.replaceAll("\\$\\w+", "%s"), args);
	}
}
