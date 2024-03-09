package io.jonasg.bob.utils;

public class Formatter {

    public static String format(String source, Object ... args) {
        return String.format(source.replaceAll("\\$\\w+", "%s"), args);
    }
}
