package com.example.estoque3.util;

import java.math.BigDecimal;

public class ConvertsUtil {

    public static BigDecimal stringToBigDecimal(String str){
        return BigDecimal.valueOf(Long.parseLong(str));
    }
    public static Integer stringToInteger(String str){
        return Integer.parseInt(str);
    }
}
