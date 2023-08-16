package com.masterj.base.utils;

import java.util.List;

public class StringUtils {

    public static final String SPACE = " ";

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is
     * not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * calculate the length of str. A un-ASCII char's length is 2.
     */
    public static int charLength(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        int len = 0;
        for (char c : str.toCharArray()) {
            if (c >= 0 && c <= 127) {
                len++;
            } else {
                len += 2;
            }
        }
        return len;
    }

    public static String forceEllipsizeEnd(String str, int size) {
        if (str.length() > size) {
            return str.substring(0, size) + "...";
        }
        return str;
    }

    public static String forceEllipsizeEnd(String str, float maxWidth, float singleWith) {
        String result = str.trim();
        int maxCount = (int) Math.floor(maxWidth / singleWith);
        if (result.length() > maxCount) {
            return forceEllipsizeEnd(result, maxCount - 1);
        } else {
            return result;
        }
    }

    public static String addDividerToPhoneNumber(String phoneNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(phoneNumber.substring(0, 3));
        sb.append("-");
        sb.append(phoneNumber.substring(3, 7));
        sb.append("-");
        sb.append(phoneNumber.substring(7));
        return sb.toString();
    }

    public static String join(List<Integer> list, String seperator) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            sb.append(seperator);
        }
        sb.delete(sb.length() - seperator.length(), sb.length());
        return sb.toString();
    }

    public static String join(List<String> list, String seperator, boolean needQuote) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (needQuote) {
                sb.append("\"");
                sb.append(list.get(i));
                sb.append("\"");
            } else {
                sb.append(list.get(i));
            }
            sb.append(seperator);
        }
        sb.delete(sb.length() - seperator.length(), sb.length());
        return sb.toString();
    }
}
