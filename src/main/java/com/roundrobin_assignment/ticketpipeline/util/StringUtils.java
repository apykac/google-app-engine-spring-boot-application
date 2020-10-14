package com.roundrobin_assignment.ticketpipeline.util;

public class StringUtils {
    private StringUtils() {
    }

    public static String left(String s, int limit) {
        if (org.springframework.util.StringUtils.isEmpty(s) || s.length() <= limit) {
            return s;
        }
        return s.substring(0, limit);
    }
}
