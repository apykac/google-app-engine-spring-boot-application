package com.roundrobin_assignment.ticketpipeline.dao;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class Converter {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BigInteger stringToBigInteger(String s) {
        return StringUtils.isEmpty(s) ? null : new BigInteger(s);
    }

    public Integer stringToInteger(String s) {
        return StringUtils.isEmpty(s) ? null : Integer.valueOf(s);
    }

    public Boolean stringToBoolean(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }

        switch (s) {
            case "1":
            case "true":
                return Boolean.TRUE;
            case "0":
            case "false":
                return Boolean.FALSE;
            default:
                return null;
        }
    }

    public LocalDateTime stringToLocalDateTime(String s) {
        return StringUtils.isEmpty(s) ? null : LocalDateTime.parse(s, formatter);
    }

    public String numberToString(Number bi) {
        return bi == null ? null : String.valueOf(bi);
    }

    public String localDateTimeToString(LocalDateTime ldt) {
        return ldt == null ? null : ldt.format(formatter);
    }

    public String booleanToString(Boolean b) {
        if (b == null) {
            return null;
        }
        return Objects.equals(b, Boolean.TRUE) ? "1" : "0";
    }
}
