package com.hpw.server.slot.bean;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyl
 * @date 2020/9/3
 */
public class SlotItem {
    private Integer id;
    private Integer type;
    private String name;
    private String uiPath;
    private String rewardTime;
    private Map<Integer, Integer> rewardTimeMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiPath() {
        return uiPath;
    }

    public void setUiPath(String uiPath) {
        this.uiPath = uiPath;
    }

    public void setRewardTime(String rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getRewardTime() {
        return rewardTime;
    }

    public Map<Integer, Integer> getRewardTimeMap() {
        return rewardTimeMap;
    }

    public void setRewardTimeMap(Map<Integer, Integer> rewardTimeMap) {
        this.rewardTimeMap = rewardTimeMap;
    }



    @Override
    public String toString() {
        return "SlotItem{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", uiPath='" + uiPath + '\'' +
                ", rewardTime='" + rewardTime + '\'' +
                ", rewardTimeMap=" + rewardTimeMap +
                '}';
    }
}
