package com.quickdev.baseframework.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理字符串工具类
 */
public class StringUtils {
    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串数组texts中是否有一个字符串为空
     *
     * @param texts
     * @return 如果字符串数组texts中有一个为空或texts为空，返回true;otherwise return false;
     */
    public static boolean isEmpty(String... texts) {
        if (texts == null || texts.length == 0) {
            return true;
        }
        for (String text : texts) {
            if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                    || "null".equals(text.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得MD5加密字符串
     *
     * @param str 字符串
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * 得到字符串长度
     *
     * @param text
     * @return
     */
    public static int getCharCount(String text) {
        String Reg = "^[\u4e00-\u9fa5]{1}$";
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg))
                result += 2;
            else
                result++;
        }
        return result;
    }

    /**
     * 获取截取后的字符串
     *
     * @param text   原字符串
     * @param length 截取长度
     * @return
     */
    public static String getSubString(String text, int length) {
        return getSubString(text, length, true);
    }

    /**
     * 获取截取后的字符串
     *
     * @param text   原字符串
     * @param length 截取长度
     * @param isOmit 是否加上省略号
     * @return
     */
    public static String getSubString(String text, int length, boolean isOmit) {
        if (isNullOrEmpty(text)) {
            return "";
        }
        if (getCharCount(text) <= length + 1) {
            return text;
        }

        StringBuffer sb = new StringBuffer();
        String Reg = "^[\u4e00-\u9fa5]{1}$";
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg)) {
                result += 2;
            } else {
                result++;
            }

            if (result <= length + 1) {
                sb.append(b);
            } else {
                if (isOmit) {
                    sb.append("...");
                }
                break;
            }
        }
        return sb.toString();
    }


    /**
     * 邮箱验证
     *
     * @param mail 邮箱
     * @return
     */
    public static boolean validateEmail(String mail) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = pattern.matcher(mail);
        return m.matches();
    }

    /**
     * 验证输入的身份证号是否符合格式要求
     *
     * @param IDNum 身份证号
     * @return 符合国家的格式要求为 true;otherwise,false;
     */
    public static boolean validateIDcard(String IDNum) {
        String id_regEx1 = "^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3}[0-9Xx])$";
        Pattern pattern = Pattern.compile(id_regEx1);
        Matcher m = pattern.matcher(IDNum);
        return m.matches();
    }

    /**
     * 验证字符串内容是否合法
     *
     * @param content 字符串内容
     * @return
     */
    public static boolean validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
        boolean legal = true;
        L1:
        for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < illegal.length(); j++) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    legal = false;
                    break L1;
                }
            }
        }
        return legal;
    }

    /**
     * 验证字符串内容是否合法(进一步验证字符串内容是否仅仅包含汉字或者0-9、a-z、A-Z)
     *
     * @param content 被校验的字符串内容
     * @return 合法：返回true;otherwise,false.
     */
    public static boolean validataLegalString2(String content) {
        if (isEmpty(content)) return false;
        if (validateLegalString(content)) {
            // 进一步验证字符串是否是汉字或者0-9、a-z、A-Z
            for (int i = 0; i < content.length(); i++) {
                if (!isRightChar(content.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    public static boolean validataLegalString3(String content) {
        if (validateLegalString(content)) {
            // 进一步验证字符串是否是汉字
            for (int i = 0; i < content.length(); i++) {
                if (!isChinese(content.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean validataLegalString4(String content) {
        if (validateLegalString(content)) {
            // 进一步验证字符串是否是0-9、a-z、A-Z，下划线
            for (int i = 0; i < content.length(); i++) {
                if (!isWord(content.charAt(i))) {
                    return false;
                }
                if (isChinese(content.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否是汉字或者0-9、a-z、A-Z
     *
     * @param c 被验证的char
     * @return true代表符合条件
     */
    public static boolean isRightChar(char c) {
        return isChinese(c) || isWord(c);
    }

    /**
     * 校验某个字符是否是a-z、A-Z、_、0-9
     *
     * @param c 被校验的字符
     * @return true代表符合条件
     */
    public static boolean isWord(char c) {
        return Pattern.compile("[\\w]").matcher(String.valueOf(c)).matches();
    }

    /**
     * 判定输入的是否是汉字
     *
     * @param c 被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 如果str为空，tv的值为"";否则，tv的值为str.
     *
     * @param tv
     * @param str 判断该参数是否为空
     */
    public static void setTextViewString(TextView tv, String str) {
        if (tv == null)
            return;
        tv.setText(isNullOrEmpty(str) ? "" : str);
    }


    /**
     * 如果str为空，tv的值为default1;否则tv的值为default2.
     *
     * @param tv
     * @param str         判断该参数是否为空
     * @param defaultStr1 str为空的话，tv的值
     * @param defaultStr2 str不为空的话，tv的值
     */
    public static void setTextViewString(TextView tv, String str, String defaultStr1,
                                         String defaultStr2) {
        if (tv == null)
            return;
        tv.setText(isNullOrEmpty(str) ? defaultStr1 : defaultStr2);
    }

    /**
     * 设置参数中的views显示状态为View.VISIBLE
     */
    public static void setViewVisible(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置参数中的views显示状态为View.GONE
     */
    public static void setViewGone(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置参数中的views显示状态为View.INVISIBLE
     */
    public static void setViewInvisible(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 对流转化成字符串
     *
     * @param is
     * @return
     */
    public static String getContentByString(InputStream is) {
        try {
            if (is == null)
                return null;
            byte[] b = new byte[1024];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对流转化成字符串
     *
     * @param is
     * @return
     */
    public static String getStringByStream(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString().replaceAll("\n\n", "\n");
        } catch (OutOfMemoryError o) {
            System.gc();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 截取字符串，去掉sign后边的
     *
     * @param source 原始字符串
     * @param sign
     * @return
     */
    public static String splitByIndex(String source, String sign) {
        String temp = "";
        if (isNullOrEmpty(source)) {
            return temp;
        }
        int length = source.indexOf(sign);
        if (length > -1) {
            temp = source.substring(0, length);
        } else {
            return source;
        }
        return temp;
    }

    /**
     * 截取字符串，返回sign分隔的字符串
     */
    public static String splitNumAndStr(String res, String sign) {
        StringBuffer buffer;
        String reg = "\\d+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(res);
        if (m.find()) {
            buffer = new StringBuffer();
            String s = m.group();
            buffer.append(s);
            buffer.append(sign);
            buffer.append(res.replace(s, ""));
            return buffer.toString();
        }
        return null;
    }

    /**
     * 字符串 转换 double
     *
     * @return 转化后的double数据
     */
    public static double parseDouble(String s) {
        String ss = "";
        if (!isNullOrEmpty(s) && s.contains(",")) {
            int len = s.split(",").length;
            for (int i = 0; i < len; i++) {
                ss = ss + s.split(",")[i];
            }
            return canParseDouble(ss) ? Double.parseDouble(ss) : 0;
        }
        return canParseDouble(s) ? Double.parseDouble(s) : 0;
    }

    /**
     * @param d   double数据
     * @param len 小数点位数
     * @return
     */
    public static String formatNumber(double d, int len) {
        try {
            DecimalFormat df = null;
            if (len == 0) {
                df = new DecimalFormat("###0");
            } else {
                String s = "#,##0.";
                String ss = "";
                for (int i = 0; i < len; i++) {
                    s = s + "0";
                    ss = ss + "0";
                }
                df = new DecimalFormat(s);
                if (df.format(d).split("\\.")[1].equals(ss)) {
                    return df.format(d).split("\\.")[0];
                }
            }
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 保留小数点后一位
     */
    public static String formatNumber(double d) {
        try {
            DecimalFormat df = new DecimalFormat("#,##0.0");
            return df.format(d);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 保留小数点后两位
     */
    public static String formatNumber2(double d) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 保留小数点后两位,三位分割()
     */
    public static String formatNumber2(double d, boolean bl) {
        if (bl) {
            try {
                DecimalFormat df = new DecimalFormat("#,##0.00");
                return df.format(d);
            } catch (Exception e) {
            }
        }
        return "";
    }

    /**
     * 保留小数点后三位
     */
    public static String formatNumber3(double d) {
        DecimalFormat df = new DecimalFormat("#,##0.000");
        return df.format(d);
    }

    /**
     * 保留小数点后四位
     */
    public static String formatNumber4(double d) {
        try {
            DecimalFormat df = new DecimalFormat("0.0000");
            return df.format(d);
        } catch (Exception e) {
        }
        return "";
    }

    public static String formatNumber(String d) {
        return formatNumber(Double.parseDouble(d));
    }

    public static String formatNumber(String d, int len) {
        return formatNumber(Double.parseDouble(d), len);
    }

    /**
     * 把对象放进map里
     *
     * @param o 实体
     */
    public static Map<String, String> getMapForEntry(Object o) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fields = o.getClass().getFields();
            for (Field f : fields) {
                String key = f.getName();
                try {
                    String value = (String) f.get(o);
                    if (StringUtils.isNullOrEmpty(value) || value.indexOf("不限") > -1) {
                        continue;
                    }
                    map.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * map 转化为实体
     *
     * @param <T>
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T setMapForEntry(Map<String, String> map, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            for (Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                Field field = t.getClass().getField(key);
                field.set(t, entry.getValue());
            }
        } catch (Exception e) {
        }
        return t;
    }


    /**
     * 判断价格是否为0或空
     */
    public static boolean isPriceZero(String price) {
        if (isNullOrEmpty(price)) {
            return true;
        }
        price = splitByIndex(price, ".");
        if ("0".equals(price)) {
            return true;
        }
        return false;
    }

    /**
     * 取价格的整数，去掉单位
     *
     * @param price
     * @return
     */
    public static String getPrice(String price) {
        if (price == null) {
            return "";
        }
        Pattern p = Pattern.compile("^\\d+");
        Matcher m = p.matcher(price);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    /**
     * 去掉单位
     *
     * @param price
     * @return
     */
    public static String getPriceAll(String price) {
        if (price == null) {
            return "";
        }
        if (price.contains(".")) {
            Pattern p = Pattern.compile("^\\d+.\\d+");
            Matcher m = p.matcher(price);
            if (m.find()) {
                return m.group();
            }
        } else {
            Pattern p = Pattern.compile("^\\d+");
            Matcher m = p.matcher(price);
            if (m.find()) {
                return m.group();
            }
        }
        return "";
    }

    /**
     * 判断是否全为数字
     *
     * @param content
     * @return
     */
    public static boolean isAllNumber(String content) {
        boolean isAllNumber = true;
        if (isNullOrEmpty(content)) {
            return false;
        }
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) < '0' || content.charAt(i) > '9') {
                isAllNumber = false;
            }
        }
        return isAllNumber;
    }

    /**
     * 整数转字节数组
     *
     * @param i
     * @return
     */
    public static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * 字节数组转整数
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }


    /**
     * 获取字符串中的数字
     *
     * @return
     */
    public static String getPriceNum(String price) {
        String regEx = "[^0-9.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(price);
        String pricetype = m.replaceAll("").trim();

        return pricetype;
    }

    /**
     * 按字节截取字符串
     *
     * @param orignal 原始字符串
     * @param count   截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static String substring(String orignal, int count) throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (null != orignal && !"".equals(orignal)) {
            // 将原始字符串转换为GBK编码格式
            String orignal_byte = new String(orignal.getBytes("UTF-8"), "UTF-8");
            if (count > 0 && count < orignal.getBytes("UTF-8").length) {
                StringBuffer buff = new StringBuffer();
                char c;
                String s = "";
                int num = 0;
                for (int i = 0; i < count; i++) {
                    // charAt(int index)也是按照字符来分解字符串的
                    if (orignal_byte.length() > i) {
                        c = orignal_byte.charAt(i);
                        buff.append(c);
                        if (isChineseChar(c)) {// 遇到中文汉字，字节总数+2
                            num += 2; // 一般汉字在utf-8中为3个字节长度
                        } else {
                            num += 1;
                        }
                        if (num == count) {
                            s = buff.toString() + "...";
                            continue;
                        } else if (num > count) {
                            if (num == 15) {
                                return buff.toString() + "...";
                            } else {
                                return s;
                            }
                        }
                    }
                }
                return buff.toString();
            }
            // 要截取的字节数大于0，且小于原始字符串的字节数
        }
        return orignal;
    }

    public static boolean isChineseChar(char c) {
        // 如果字节数大于1，是汉字
        try {
            return String.valueOf(c).getBytes("UTF-8").length > 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回汉字个数
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static int getChineseCount(String s) {// 获得汉字的长度
        char c;
        int chineseCount = 0;
        if (!"".equals("")) {// 判断是否为空
            try {
                s = new String(s.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } // 进行统一编码
        }
        for (int i = 0; i < s.length(); i++) {// for循环
            c = s.charAt(i); // 获得字符串中的每个字符
            if (isChineseChar(c)) {// 调用方法进行判断是否是汉字
                chineseCount++; // 等同于chineseCount=chineseCount+1
            }
        }
        return chineseCount; // 返回汉字个数
    }


    /**
     * 返回间隔空格的title
     *
     * @param args
     * @return
     */
    public static String getStringName(String... args) {
        String name = "";
        for (int i = 0; i < args.length; i++) {
            if (!isNullOrEmpty(args[i])) {
                name += args[i] + " ";
            }
        }
        return name;
    }

    /**
     * 检验字符串是否包含不合法字符
     *
     * @param Str
     * @return
     */
    public static boolean validateStr(String Str) {
        Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9\u4e00-\u9fa5]+$");
        Matcher m = pattern.matcher(Str);
        return m.matches();
    }

    /**
     * 验证一个字符串是否能解析成整数
     *
     * @param numberStr
     * @return
     */
    public static boolean canParseInt(String numberStr) {
        try {
            Integer.parseInt(numberStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 验证一个字符串是否能解析成双精度浮点数
     *
     * @param numberStr
     * @return
     */
    public static boolean canParseDouble(String numberStr) {
        try {
            Double.parseDouble(numberStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 验证一个字符串是否能解析成浮点数
     *
     * @param numberStr
     * @return
     */
    public static boolean canParseFloat(String numberStr) {
        try {
            Float.parseFloat(numberStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 验证一个字符串是否能解析成长整型数
     *
     * @param numberStr
     * @return
     */
    public static boolean canParseLong(String numberStr) {
        try {
            Long.parseLong(numberStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean validateString(String Str) {
        // 按照顺序汉字，英文字母数字和英文符号，标点，中日韩标点，特殊标点,希腊文，几何形状各种运算符之类
        if (StringUtils.isNullOrEmpty(Str)) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("^[\u4e00-\u9fbf\u0020-\u007F\u2000-\u206F\u3000-\u303F\uFF00-\uFFEF\u0370-\u03FF\u2100-\u25FF]+$");
        Matcher m = pattern.matcher(Str);
        return m.matches();
    }

    /**
     * 判断字符串中是否含有连续的数字
     *
     * @param content 字符串
     * @param num     几个连续数字
     * @return
     */
    public static boolean isHavePhoneNum(String content, int num) {
        int count = 0;
        if (StringUtils.isNullOrEmpty(content)) {
            return false;
        }
        Pattern p = Pattern.compile("\\d{" + num + "}");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @param name 注册的名字
     * @return 是否合法
     */
    public static boolean isRegisterName(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("^[\\u2E80-\\u9FFF\\[a-zA-Z]{2,10}$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    /**
     * @param password 注册的密码 0-9 |英文 |符号
     * @return 是否合法
     */
    public static boolean isOkPassword(String password) {
        if (StringUtils.isNullOrEmpty(password)) {
            return false;
        }
//        Pattern pattern = Pattern
//                .compile("^(?!^[0-9]+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{8,16}$");
        Pattern pattern = Pattern
                .compile("^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）――+|{}【】‘；：”“'。，、？]){8,16}$");
        Matcher m = pattern.matcher(password);
        return m.matches();
    }

    public static boolean isLetterNumber(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-z0-9A-Z_]*$");
        Matcher m = pattern.matcher(text);
        return m.matches();
    }

    public static boolean isInteger(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[1-9]\\d{0,5}$");
        Matcher m = pattern.matcher(text);
        return m.matches();
    }

    /**
     * 电话号码验证
     *
     * @param phoneNumber 手机号码
     * @return true：手机号
     */
    public static boolean validatePhoneNumber(String... phoneNumber) {
        if (phoneNumber == null || phoneNumber.length == 0) {
            return false;
        }
        for (String text : phoneNumber) {
            Pattern pattern = Pattern
                    .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = pattern.matcher(text);
            return m.matches();
        }
        return false;
    }


    /**
     * 截取字符串，多余部分使用省略号
     *
     * @param s
     * @param index 14字的"houseName"  10字的标题
     * @return
     */
    public static String getTextZi(String s, int index) {
        if (s != null) {
            if (s.length() == 0) {
                return "";
            } else if (s.length() >= index) {
                s = s.substring(0, index) + "...";
                return s;
            }
        }
        return s;
    }


    public static String getDataTime(long l) {
        Date d = new Date(l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    /**
     * 获取Editext的输入内容
     *
     * @param et
     * @return
     */
    public static String getText(EditText et) {
        if (et != null) {
            return et.getText().toString().trim();
        }
        return "";
    }

    public static String getResString(int id) {
        return AppContextUtil.getContext().getResources().getString(id);
    }
    @SuppressWarnings("unchecked")
    public static String url(Map<String, String> params) {
        int i = 0;
        String url = "";
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entity = (Entry<String, Object>) iterator.next();
            if (entity != null) {
                if (i == 0) {
                    url += entity.getKey() + "=" + entity.getValue();
                } else {
                    url += "&" + entity.getKey() + "=" + entity.getValue();
                }
            }
            i++;
        }
        return url;
    }


}
