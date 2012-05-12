package com.jmolly.stacktraceparser;

final class Utils {

    private Utils() {}

    public static String strip(String value, String chars) {
        if (value == null) return null;
        char[] charsArr = chars.toCharArray();
        int i = 0, j = value.length() - 1;
        while (i < j && contains(charsArr, value.charAt(i))) {
            ++i;
        }
        while (i <= j && contains(charsArr, value.charAt(j))) {
            --j;
        }
        return value.substring(i, j + 1);
    }

    private static boolean contains(char[] a, char c) {
        for (char i : a) {
            if (i == c)
                return true;
        }
        return false;
    }

}
