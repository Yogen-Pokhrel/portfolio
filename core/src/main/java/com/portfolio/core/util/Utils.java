package com.portfolio.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
    public static String slugify(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    public static String slugify(String str, String delimiter) {
        return str.replaceAll("[^a-zA-Z0-9]", delimiter).toLowerCase();
    }

    public static String slugify(String str, boolean upper) {
        return str.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    public static String slugify(String str, boolean upper, String delimiter) {
        return str.replaceAll("[^a-zA-Z0-9]", delimiter).toUpperCase();
    }

    public static String extractSimpleClassName(String input) {
        // Regex pattern to match the fully qualified class name
        String regex = "`([a-zA-Z0-9_\\.]+)`";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String fullClassName = matcher.group(1);
            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            input = input.replace(fullClassName, simpleClassName);
        }
        return input;
    }
}
