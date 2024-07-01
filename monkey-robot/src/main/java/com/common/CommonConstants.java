package com.common;


/**
 * 公用常量类
 *
 */
public class CommonConstants {

    // 特殊符号
    public interface Symbol {
        /*中杠*/
        public static final String DOWN_SLASH = "_";
        /*分号*/
        public static final String MULTIPLY = "*";
        /* 垂线 */
        public static final String VERTICAL = "|";
        /* 反斜线 */
        public static final String SLASH_OPPOSITE = "\\";
        /* 双反斜线 */
        public static final String SLASH_OPPOSITE_DOUBLE = "\\\\";
        /* 双斜线 */
        public static final String SLASH = "/";
        /* 中杠 */
        public static final String DASH = "-";
        /* 中杠 */
        public static final String UNDERLINE = "_";
        /* 逗号 */
        public static final String COMMA = ",";
        /* and 符 */
        public static final String STR_AND = "&";
        /* 双引号 */
        public static final String DOUBLE_QUOTATION = "\"";
        /* 句号 */
        public static final String POINT = ".";
        /* 分号 */
        public static final String SEMI = ";";
        /* 分号 */
        public static final String PLUS = "+";

        public static final char CHAR_WHITE_SPACE = ' ';

        public static final String STRING_WHITE_SPACE = " ";

        public static final String STRING_QUESTION = "?";

        public static final String STRING_DENG = "=";

        public static final String COLON = ":";

    }


    public interface CharSet {
        public static final String UTF8 = "UTF-8";
    }

    public static final String BEAN_NAME_SERVER_CONNECTION = "connection";
    public static final String NODE_SYNCHRONIZER = "nodeSynchronizer";

}
