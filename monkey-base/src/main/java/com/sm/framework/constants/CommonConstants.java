package com.sm.framework.constants;


/**
 * 公用常量类
 *
 * @author zhangen
 */
public class CommonConstants {

    public interface Roles {
        /**
         * 超级管理员
         */
        public static final String SUPER = "SUPER";
        /**
         * 企业管理员，需要绑定一个用户的， 要初始化所有的功能权限（menu_id， btn_id） // TODO 企业管理员放到角色管理里面添加， 只是这个添加的动作由系统自动完成
         * 
         */
        public static final String COMPANY_SYS_MANAGER = "COMPANY_SYS_MANAGER"; // 基本上没用了
        /**
         * 注册管理员，无绑定用户， 要初始化人员相关权限、角色管理、角色授权权限
         */
        public static final String REGISTER_MANAGER = "0047297f43f4429892f31a3af63feae3"; // 该id 必需与初始化脚本中的 role_id 保持一致
    }

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

    public interface Cache {
        /**
         * 缓存1分钟, 单位秒
         */
        public static final int ONE_MINUTES_SECOND = 60;
        /**
         * 缓存5分钟, 单位秒
         */
        public static final int FIVE_MINUTES_SECOND = 300;
        /**
         * 缓存10分钟, 单位秒
         */
        public static final int TEN_MINUTES_SECOND = 600;
        /**
         * 缓存30分钟, 单位秒
         */
        public static final int HALF_ONE_HOUR_SECOND = 1800;
        /**
         * 缓存1小时, 单位秒
         */
        public static final int ONE_HOUR_SECOND = 3600;
        /**
         * 缓存2小时, 单位秒
         */
        public static final int TWO_HOUR_SECOND = 7200;

        /**
         * 缓存24小时, 单位秒
         */
        public static final int ONE_DAY_SECOND = 24 * 3600;
    }

    public interface CharSet {
        public static final String UTF8 = "UTF-8";
    }

    public interface Export {
        public final static String EXCEL_SUFFIX = ".xls";
        public final static String EXCEL_SUFFIX_XLSX = ".xlsx";
        public final static String EXCEL_SUFFIX_CSV = ".csv";
    }

    public interface Number {
        public static final int NEGATIVEONE = -1;
        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
        public static final int FIVE = 5;
        public static final int SIX = 6;
        public static final int SEVEN = 7;
        public static final int EIGHT = 8;
        public static final int NINE = 9;
        public static final int TEN = 10;
        public static final int ELEVEN = 11;
        public static final int TWELVE = 12;
        public static final int TWENTY = 20;
        public static final int THIRTY = 30;
        public static final int NUMBER_3600 = 3600;
        public static final int CONTRACTCODENUMSTART = 100001;
        public static final int FORTY = 40;
        public static final int FIFTY = 50;
        public static final int SIXTY = 60;
        public static final int NUMBER_200 = 200;
        public static final int NUMBER_400 = 400;
        public static final int NUMBER_999 = 999;
    }

    public interface NumberString {
        public static final String NOPARENT = "-1";
        public static final String ZERO = "0";
        public static final String ONE = "1";
        public static final String TWO = "2";
        public static final String THREE = "3";
        public static final String FOUR = "4";
        public static final String FIVE = "5";
        public static final String SIX = "6";
        public static final String SEVEN = "7";
        public static final String EIGHT = "8";
        public static final String NINE = "9";
        public static final String TEN = "10";
        public static final String ELEVEN = "11";
        public static final String TWELVE = "12";
        public static final String THIRTEEN = "13";
        public static final String FOURTEEN = "14";
        public static final String FIFTEEN = "15";
        public static final String SIXTEEN = "16";
        public static final String SEVENTEEN = "17";
        public static final String EIGHTEEN = "18";
        public static final String NINETEEN = "19";
        public static final String TWENTY = "20";
    }

    public interface NumberByte {
        public static final byte ZERO = 0;
        public static final byte ONE = 1;
        public static final byte TWO = 2;
        public static final byte THREE = 3;
        public static final byte FOUR = 4;
    }
    
    public interface Gender {
    	 public static final int MALE = 1;
         public static final int FEMALE = 2;
    }

    public interface File {
        public static final String XLSX = ".xlsx";
        public static final String XLS = ".xls";
    }
    

    /************************** Punctuation 各类分隔符或标点 ***********************/
    public static final String PUNC_SPLITTER = "어"; // 独一无二分隔符
    public static final String PUNC_DOT = "."; // 英文点号
    public static final String PUNC_SINGLE_QUOTE = "'"; // 英文单引号
    public static final String PUNC_DOUBLE_QUOTE = "\""; // 英文双引号
    public static final String PUNC_BLANKSPACE = " "; // 一个空格
    public static final String PUNC_SEMICOLON = ";"; // 英文分号
    public static final String PUNC_COLON = ":"; // 英文冒号
    public static final String PUNC_STRIKE_LINE = "-"; // 英文中划线
    public static final String PUNC_UNDER_LINE = "_"; // 英文下划线
    public static final String PUNC_COMMA = ","; // 英文逗号
    public static final String PUNC_HASH = "#"; // 井号
    public static final String PUNC_STAR = "*"; // 星号
    public static final String PUNC_AND = "&"; // and号
    public static final String PUNC_SLASH = "/"; // 斜杠
    public static final String PUNC_BACKSLASH = "\\"; // 反斜杠
    public static final String PUNC_DOLLAR = "$"; // 美元
    public static final String PUNC_EXCITE = "!"; // 英文感叹号
    public static final String PUNC_QUESTION = "?"; // 英文问号
    public static final String PUNC_AT = "@"; // AT符号
    public static final String PUNC_PERCENT = "%"; // 百分号
    public static final String PUNC_VERTICAL_LINE = "|"; // 英文竖线
    public static final String PUNC_PLUS = "+";// 英文加号
    public static final String PUNC_EQUAL = "="; // 英文等号
    public static final String PUNC_XKH_LEFT = "("; // 英文小括号左
    public static final String PUNC_XKH_RIGHT = ")"; // 英文小括号邮
    public static final String PUNC_ZKH_LEFT = "["; // 英文中括号左
    public static final String PUNC_ZKH_RIGHT = "]"; // 英文中括号右
    public static final String PUNC_DKH_LEFT = "{";// 英文大括号左
    public static final String PUNC_DKH_RIGHT = "}";// 英文大括号右
    public static final String PUNC_GREATER = ">";// 英文大于号
    public static final String PUNC_LESS = "<";// 英文小于号
    /***************************************************************/

    /************************** Boolean ***********************/
    // public static final Boolean BOOL_TRUE = true;
    // public static final Boolean BOOL_FALSE = false;
    public static final String BOOL_Y = "y";
    public static final String BOOL_N = "n";
    public static final String BOOL_YES = "yes";
    public static final String BOOL_NO = "no";
    public static final String BOOL_TEXT_TRUE = "true";
    public static final String BOOL_TEXT_FALSE = "false";
    /***************************************************************/

    /********************** Time Format ***********************/
    // public static final String TIME_FORMAT_HOUR_MINUTE = "hh:mm";
    // public static final String TIME_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    // public static final String TIME_FORMAT_YMD_HOUR_MINUTE = "yyyy-MM-dd hh:mm";
    /***********************************************************/

    /********************** Picture Format ***********************/
    public static final String PIC_FORMAT_JPG = "jpg";
    public static final String PIC_FORMAT_JPEG = "jpeg";
    public static final String PIC_FORMAT_PNG = "png";
    public static final String PIC_FORMAT_GIF = "gif";

    /********************** All status types ***********************/
    public static final int ALL_STATUS_TYPE = 9999;
    public static final String QUANBU_STATUS_TYPE = "全部";
    public static final String SUOYOU_STATUS_TYPE = "所有";
    public static final String BUXIAN_STATUS_TYPE = "不限";

    // START 常用数字,一般情况下不需要使用,可以hardcode,看具体情况
    /******************** Digit *****************/
    public static final int DIGIT_MINUS_ONE = -1;
    public static final int DIGIT_ZERO = 0;
    public static final int DIGIT_ONE = 1;
    public static final int DIGIT_TWO = 2;
    public static final int DIGIT_THREE = 3;
    public static final int DIGIT_FOUR = 4;
    public static final int DIGIT_FIVE = 5;
    public static final int DIGIT_SIX = 6;
    public static final int DIGIT_SEVEN = 7;
    public static final int DIGIT_EIGHT = 8;
    public static final int DIGIT_NINE = 9;
    public static final int DIGIT_TEN = 10;
    public static final int DIGIT_THIRTEEN = 13;
    public static final int DIGIT_FIFTEEN = 15;
    public static final int DIGIT_TWENTY = 20;
    public static final int DIGIT_THIRTY = 30;
    public static final int DIGIT_FIFTY = 50;
    public static final int DIGIT_SIXTY = 60;
    public static final int DIGIT_HUNDRED = 100;
    public static final int DIGIT_THOUNDRED = 1000;
    public static final int DIGIT_NINE_THOUSAND_NINE_HUNDRED_AND_NINETY_NINE = 9999;
    /********************************************/

    /************************** LONG ***********************/
    public static final Long LONG_ZERO = 0L;

    /**********************************************************/

    /************************** String ***********************/
    public static final String STRING_ZERO = "0";
    public static final String STRING_ONE = "1";
    public static final String STRING_TWO = "2";
    public static final String STRING_THREE = "3";
    public static final String STRING_FOUR = "4";
    public static final String STRING_FIVE = "5";
    public static final String STRING_SIX = "6";
    public static final String STRING_SEVEN = "7";
    public static final String STRING_EIGHT = "8";
    public static final String STRING_NINE = "9";
    public static final String STRING_TEN = "10";
    public static final String STRING_FIFTY = "50";
    public static final String STRING_SIXTY = "60";
    /**********************************************************/
    // END 常用数字,一般情况下不需要使用,可以hardcode,看具体情况


    public static final String CHARSET = "UTF-8";
    public static final String DATA_SCHEMAL = "json";
    public static final String SIGN_SHA_1 = "SHA-1";
    public static final String SEPARATOR_CHAR = ":";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SPACE = " ";
    public static final String SYS = "sys";
    public static final String SYMBAL_ATTA = "@";
    public static final String BLANK = "";
    public static final String HYPHEN = "-";
    public static final String SLASH = "/";
    public static final String POSITION_FIRST_CODE = "000001";
    public static final String POST_FIRST_CODE = "001";
    public static final String ORGAN_FIRST_CODE = "01";
    public static final String KONGGANGWEI = "空岗位";
    public static final String COMMONPWD = "joyhr@2019";
    
    public static final String EXTFIELDREX = "[c]([1-9]|[1-4][0-9]|50)$";
}
