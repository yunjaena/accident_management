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
        String[] s = string.split(",");
        List<String> stringList = new ArrayList<>(Arrays.asList(s));
        return stringList;
    }
}
