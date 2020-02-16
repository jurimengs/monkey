package com.monkey.util;

import org.apache.commons.lang3.CharUtils;

import com.monkey.constants.CommonConstants;

/**
 * 压缩文件工具类
 */
public class StringUtil {

    /** * 对象属性转换为字段 例如:userName to user_name * @param property 字段名 * @return */
    public static String fieldToDbField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + (char) (c + 32));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toFieldName(String fieldName) {
        return toBaseName(fieldName, false);
    }
    
    public static String getBeanNameOfClass(String className) {
        String tmp = className.substring(className.lastIndexOf(CommonConstants.Symbol.POINT) + 1);
        return toCammerName(tmp);
    }
    
    public static String toCammerName(String fieldName) {
        char[] ch = fieldName.toCharArray();
        ch[0] = (char) (ch[0] + 32);
        return new String(ch);
    }

    private static String toBaseName(String fieldName, boolean firstCharToUpper) {
        fieldName = fieldName.toLowerCase();
        char[] ch = fieldName.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '_') {
                i++;
                ch[i] = (char) (ch[i] - 32);
            }
        }
        if (firstCharToUpper) {
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
        }

        String res = new String(ch);

        return res.replaceAll("_", "");
    }

}