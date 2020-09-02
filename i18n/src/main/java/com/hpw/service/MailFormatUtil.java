package com.hpw.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailFormatUtil {
    private static final String SEPARATOR = ";";

    private static final Pattern pattern;

    static {
        String regex = "(#<[a-zA-Z]+>)";
        pattern = Pattern.compile(regex);
    }

    /**
     * 将格式字符串进行格式化
     *
     * @param waitFormatStr 待格式化的字符串
     * @param argsStr 待填充的格式化参数
     * @return 格式化完成的字符串
     */
    public static String format(String waitFormatStr, String argsStr) {
        String[] split = argsStr.split(SEPARATOR);
        return format(waitFormatStr, split);
    }

    /**
     * 将格式字符串进行格式化
     *
     * @param waitFormatStr 待格式化的字符串
     * @param args 待填充的格式化参数
     * @return 格式化完成的字符串
     */
    public static String format(String waitFormatStr, Object[] args) {
        Map<Integer, FormatControlStrInfo> formatControlStrMap = new HashMap<>(3);
        Matcher matcher = pattern.matcher(waitFormatStr);
        // 待匹配的字符串起始位置
        int match_start_index = 0;
        // 第几个转义控制字符串
        int formatControlStrCount = 0;
        if (matcher.find()) {
            while (matcher.find(match_start_index)) {
                formatControlStrMap.put(matcher.start(), new FormatControlStrInfo(formatControlStrCount, matcher.group(1)));
                match_start_index = matcher.end();
                formatControlStrCount++;
            }
        }

        if (formatControlStrMap.size() != args.length) {
            throw new RuntimeException("formatControlStr is no equals to args length");
        }

        // 已经处理结束的位置
        int handleStrIndex = 0;
        // 占位符开始位置
        int formatControlStrIndex = 0;

        StringBuilder builder = new StringBuilder(waitFormatStr.length() + 50);

        int argsIndex = 0;
        for (int index = 0; index < waitFormatStr.length(); index++) {
            // 从 handleStrIndex 位置开始匹配
            formatControlStrIndex = waitFormatStr.indexOf("#<", handleStrIndex);
            if (formatControlStrIndex == -1) {
                // 之前没有匹配到任何占位符，直接返回
                if (handleStrIndex == 0) {
                    return waitFormatStr;
                }
                // 连接 content 剩余内容
                builder.append(waitFormatStr, handleStrIndex, waitFormatStr.length());
                return builder.toString();
            }

            // todo {@author lyl} 暂不处理转义

            // 填充占位符前面的内容
            builder.append(waitFormatStr, handleStrIndex, formatControlStrIndex);
            // 填充占位符内的内容
            FormatControlStrInfo formatControlStrInfo = formatControlStrMap.get(formatControlStrIndex);
            if (formatControlStrInfo == null) {
                throw new RuntimeException("formatControlStr can not fount");
            }
            builder.append(args[argsIndex]);
            argsIndex++;
            // 忽略占位符
            handleStrIndex =  formatControlStrIndex + formatControlStrInfo.text.length();
        }
        return waitFormatStr;
    }

    private static class FormatControlStrInfo {
        /**
         * 当前转义控制字符串属于文本的第几个
         */
        public int count;
        /**
         * 转义控制字符串的内容
         */
        public String text;

        public FormatControlStrInfo(int count, String text) {
            this.count = count;
            this.text = text;
        }

        @Override
        public String toString() {
            return "FormatControlStrInfo{" +
                    "count=" + count +
                    ", content='" + text + '\'' +
                    '}';
        }
    }
}
