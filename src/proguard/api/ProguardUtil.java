package proguard.api;

import java.io.File;
import java.util.ArrayList;

import proguard.trace.ReTrace;

/**
 * Copyright © 2013-2019 Worktile. All Rights Reserved.
 *
 * @author : liboxin
 * @date : 2019-07-01
 * Email: liboxin@worktile.com
 * Time: 13:55
 * Desc:
 */
class ProguardUtil {

    private static final String STACK_TRACE_EXPRESSION = "(?:.*?\\bat\\s+%c\\.%m\\s*\\(.*?(?::%l)?\\)\\s*)|(?:(?:.*?[:\"]\\s+)?%c(?::.*)?)";


    public static void main(String[] args) {
        String json = decode(
                "/Users/liboxin/0_mine/temp/proguard/mapping.txt",
                new String[]{
                        "Caused by: java.lang.NullPointerException: lalala",
                        "at com.worktile.err.ExceptionUtil.a(Unknown Source:29)",
                        "at com.worktile.MainActivity.click(Unknown Source:44)"}
        );
        System.out.println("json -> " + json);
    }

    public static String decode(String mappingFilePth, String[] proguardStackLines) {
        ArrayList<String> list;
        try {
            list = new ArrayList<String>(new ReTrace(STACK_TRACE_EXPRESSION, true, new File(mappingFilePth), proguardStackLines).execute());
        } catch (Exception ex) {
            String err = ex.getMessage() == null ? "unknown" : ex.getMessage();
            return String.format("{\"code\":201,\"message\":\"%s\",\"data\":[]}", err);
        }
        return String.format("{\"code\":200,\"message\":\"success\",\"data\":[%s]}", makeJsonArray(list));
    }

    private static String makeJsonArray(ArrayList<String> list) {
        if (list != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append("{");
                sb.append(String.format("\"line\":\"%s\"", list.get(i)));
                sb.append("}");
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            if (sb.toString().endsWith(",")) {
                sb.substring(0, sb.length() - 1);
            }
            return sb.toString();
        }
        return "";
    }
}
