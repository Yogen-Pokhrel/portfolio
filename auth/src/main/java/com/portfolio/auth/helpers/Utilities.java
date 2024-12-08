package com.portfolio.auth.helpers;

public class Utilities {
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


}
