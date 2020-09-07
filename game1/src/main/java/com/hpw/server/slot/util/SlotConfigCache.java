package com.hpw.server.slot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hpw.server.slot.bean.SlotItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author lyl
 * @date 2020/9/3
 */
public class SlotConfigCache {
    public static String RULE_INDEX;

    public static final List<SlotItem> SLOT_ITEMS = new ArrayList<>();

    /**
     * {id:{columnIndex: rowIndex}}
     */
    public static final Map<Integer, Map<Integer, Integer>> RULE_POINT_MAP = new TreeMap<>();

    public static void init() {
        SLOT_ITEMS.clear();
        RULE_POINT_MAP.clear();
        RULE_INDEX = null;

        // 读取路径规则
        StringBuilder builder = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("slot.json")));) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map parse = JSON.parseObject(builder.toString(), Map.class);
        RULE_INDEX = (String) parse.get("SlotPathData");

        Map<Integer, Integer> rule = null;
        String[] ruleArr = RULE_INDEX.split(";");

        int column = 0;
        int ruleId = 1;
        for (String str : ruleArr) {
            rule = new TreeMap<>();
            String[] rowIndexArr = str.split(",");
            for (String rowIndex : rowIndexArr) {
                rule.put(column, Integer.parseInt(rowIndex) - 1);
                column++;
            }
            column = 0;
            RULE_POINT_MAP.put(ruleId++, rule);
        }

        // 读取 图标信息
        Map slotItemData = (Map) parse.get("SlotItemData");
        JSONArray items = (JSONArray) slotItemData.get("item");
        for (Object o : items) {
            SlotItem item = JSON.parseObject(o.toString(), SlotItem.class);
            SLOT_ITEMS.add(item);
        }
    }

}
