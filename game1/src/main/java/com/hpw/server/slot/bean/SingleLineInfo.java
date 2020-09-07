package com.hpw.server.slot.bean;

import com.hpw.server.slot.constant.PatternTypeEnum;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author lyl
 * @date 2020/8/31
 */
public class SingleLineInfo {
    /**
     * 匹配哪个规则
     */
    private Integer pathRuleId;
    /**
     * 首轴上的图案类型
     */
    private PatternTypeEnum firstPatternType;

    /**
     * 该连线属于那个图案,注意 WILD 可能被取代
     */
    private Integer belongsToPattern;

    /**
     * 连续命中次数
     */
    private Integer consecutiveAim;

    /**
     * 是否进行翻倍
     */
    private Boolean isNeedDouble;

    /**
     * 默认获得倍率(未进行翻倍的倍率
     */
    private Integer sourceMultiplyingPower;


    /**
     * 绑定的路径规则, 目前仅用来查看
     */
    private Map<Integer, Integer> ruleMap;

    /**
     * 检查并且增加一次连续命中次数 <br>
     * 为啥检查? 因为默认首轴为一次了
     */
    public void checkAndIncreaseConsecutiveAim() {
        if (this.consecutiveAim == null) {
            this.consecutiveAim = 1;
        }
        this.consecutiveAim++;
    }

    /**
     * 转化为用于存储字符串
     *
     * @return 存储字符串
     */
    public String toStorageStr() {
        return this.pathRuleId +
                "," +
                (firstPatternType == null ? PatternTypeEnum.UNKNOWN.getType() : firstPatternType.getType()) +
                "," +
                (belongsToPattern == null ? -1 : belongsToPattern) +
                "," +
                (consecutiveAim == null ? 0 : consecutiveAim) +
                "," +
                (isNeedDouble == null ? "false" : isNeedDouble) +
                "," +
                (sourceMultiplyingPower == null ? 0 : sourceMultiplyingPower);
    }

    /**
     * 根据存储字符串，转化为当前类实例
     *
     * @param storageStr 存储字符串，格式为 {@link SingleLineInfo#toStorageStr()}
     * @return 当前类实例
     */
    public static SingleLineInfo toSingleLineInfo(String storageStr) {
        SingleLineInfo info = new SingleLineInfo();
        String[] infoArr = storageStr.split(",");
        if (infoArr.length != 0) {
            info.setPathRuleId(Integer.parseInt(infoArr[0]));
            info.setFirstPatternType(PatternTypeEnum.getById(Integer.parseInt(infoArr[1])));
            info.setBelongsToPattern(Integer.parseInt(infoArr[2]));
            info.setConsecutiveAim(Integer.parseInt(infoArr[3]));
            info.setNeedDouble(Boolean.parseBoolean(infoArr[4]));
            info.setSourceMultiplyingPower(Integer.parseInt(infoArr[5]));
        }
        return info;
    }

    public Integer getPathRuleId() {
        return pathRuleId;
    }

    public void setPathRuleId(Integer pathRuleId) {
        this.pathRuleId = pathRuleId;
    }

    public PatternTypeEnum getFirstPatternType() {
        return firstPatternType;
    }

    public void setFirstPatternType(PatternTypeEnum firstPatternType) {
        this.firstPatternType = firstPatternType;
    }

    public Integer getBelongsToPattern() {
        return belongsToPattern;
    }

    public void setBelongsToPattern(Integer belongsToPattern) {
        this.belongsToPattern = belongsToPattern;
    }

    public Integer getConsecutiveAim() {
        return consecutiveAim;
    }

    public void setConsecutiveAim(Integer consecutiveAim) {
        this.consecutiveAim = consecutiveAim;
    }

    public Boolean getNeedDouble() {
        if (isNeedDouble == null) {
            return false;
        }
        return isNeedDouble;
    }

    public void setNeedDouble(Boolean needDouble) {
        isNeedDouble = needDouble;
    }

    public Map<Integer, Integer> getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(Map<Integer, Integer> ruleMap) {
        this.ruleMap = ruleMap;
    }

    public Integer getSourceMultiplyingPower() {
        return sourceMultiplyingPower;
    }

    public void setSourceMultiplyingPower(Integer sourceMultiplyingPower) {
        this.sourceMultiplyingPower = sourceMultiplyingPower;
    }

    @Override
    public String toString() {
        return "SingleLineInfo{" +
                "pathRuleId=" + pathRuleId +
                ", firstPatternType=" + firstPatternType +
                ", belongsToPattern=" + belongsToPattern +
                ", consecutiveAim=" + consecutiveAim +
                ", isNeedDouble=" + isNeedDouble +
                ", sourceMultiplyingPower=" + sourceMultiplyingPower +
                ", ruleMap=" + ruleMap +
                '}';
    }
}