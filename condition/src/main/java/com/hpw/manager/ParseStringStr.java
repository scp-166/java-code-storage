package com.hpw.manager;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public interface ParseStringStr {
    /**
     * 转换为条件组，最小单位不做处理，仅存储
     *
     * @param conditionStr 条件组字符串
     * @return 双层 list，最内层时且关系，最外层时或关系，最小单位为 {@link String}
     */
    default List<List<String>> parse2StringConditionGroup(String conditionStr) {
        if (StringUtils.isEmpty(conditionStr)) {
            return Collections.emptyList();
        }

        char ch;
        int operationStartIndex = 0;

        List<List<String>> orGroups = new ArrayList<>(3);
        List<String> andGroups = new ArrayList<>(3);

        for (int i = 0, size = conditionStr.length(); i < size; i++) {
            ch = conditionStr.charAt(i);

            if (ch == ';' || ch == '|') {
                String conditionItem = conditionStr.substring(operationStartIndex, i);
                andGroups.add(conditionItem);
                operationStartIndex = i + 1;
            }

            if (i == size - 1) {
                // 最后一行需要全部填充, 不跳过当前字符
                String conditionItem = conditionStr.substring(operationStartIndex, i + 1);
                andGroups.add(conditionItem);
                operationStartIndex = i + 1;
            }

            // 遇到 | 或者 末尾时，把之前的内层and组添加到or组
            if (ch == '|' || i == size - 1) {
                orGroups.add(andGroups);
                andGroups = new ArrayList<>(3);
            }
        }

        return orGroups;
    }


    /**
     * 功能同上 <br>
     * 基准测试(JHM)中，规模在数量级 1 亿以上，花费时间依然比 {@link ParseStringStr#parse2StringConditionGroup(String)} 慢
     */
    @Deprecated
    default List<List<String>> other4parse2StringConditionGroup(String conditionStr) {
        if (StringUtils.isEmpty(conditionStr)) {
            return Collections.emptyList();
        }
        List<List<String>> orGroups = new ArrayList<>(3);
        List<String> andGroups ;

        StringTokenizer orTokenizer = new StringTokenizer(conditionStr, "|");
        StringTokenizer andTokenizer;
        String andGroup;

        while (orTokenizer.hasMoreElements()) {
            andGroup = orTokenizer.nextToken();
            andGroups = new ArrayList<>(3);
            andTokenizer = new StringTokenizer(andGroup, ";");
            while (andTokenizer.hasMoreElements()) {
                andGroups.add(andTokenizer.nextToken());
            }
            orGroups.add(andGroups);
        }
        return orGroups;
    }
}
