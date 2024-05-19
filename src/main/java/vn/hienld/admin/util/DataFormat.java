package vn.hienld.admin.util;

import java.util.Arrays;
import java.util.List;

public class DataFormat {

    public static String lower(String data) {
        return data == null ? null : data.toLowerCase();
    }

    public static String trim(String data) {
        return data == null ? null : data.trim();
    }

    public static List<Integer> toList(Integer[] data) {
        return data.length == 0 ? null : Arrays.asList(data);
    }
}
