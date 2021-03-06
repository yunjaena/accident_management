package com.yunjaena.accident_management.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static String stringListToString(List<String> stringList) {
        return TextUtils.join(",", stringList);
    }

    public static List<String> stringToStringList(String string) {
        if (string.length() >= 1) {
            String[] s = string.split(",");
            return new ArrayList<>(Arrays.asList(s));
        } else{
            return new ArrayList<>();
        }
    }
}
