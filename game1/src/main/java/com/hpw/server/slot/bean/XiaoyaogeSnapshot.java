package com.hpw.server.slot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.util.Random;
import com.hpw.server.slot.util.WeightManager;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 逍遥阁快照记录
 *
 * @author lyl
 * @date 2020/9/2
 */
@TableName("g_xiaoyaoge_snapshot")
public class XiaoyaogeSnapshot {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属机器
     */
    @TableField("machineId")
    private Integer machineId;

    /**
     * 种子
     */
    @TableField("seed")
    private Integer seed;

    /**
     * 得到的倍率
     */
    @TableField("multiplyingPower")
    private long multiplyingPower;

    /**
     * 记录是否触发一次 jackpot
     */
    @TableField("triggerJackpot")
    private boolean triggerJackpot;

    /**
     * 记录是否触发一次 bonus
     */
    @TableField("triggerBonus")
    private boolean triggerBonus;

    /**
     * 当 {@link XiaoyaogeSnapshot#triggerBonus} 为 true 时，表示免费押注次数
     */
    @TableField("freeSpinsTimes")
    private int freeSpinsTimes;

    /**
     * 普通图案的单线匹配信息 <br>
     * 格式按照: 规则Id,单线首轴图案类型,单线所属图案id，匹配个数，是否需要翻倍，单线匹配初始倍率;
     */
    @TableField("normalPatternSingleLineMatchingInfo")
    private String normalPatternSingleLineMatchingInfo;


    /**
     * 存储普通图案单线匹配信息
     *
     * @param list 普通图案单线匹配信息
     */
    public void setNormalPatternSingleLineInfo(List<SingleLineInfo> list) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ret.append(list.get(i).toStorageStr());
            if (i != list.size() - 1) {
                ret.append(";");
            }
        }
        this.normalPatternSingleLineMatchingInfo = ret.toString();
    }

    /**
     * 将普通图案单线匹配信息存储字符串转化为实体类 list
     *
     * @return 实体类 list
     */
    public List<SingleLineInfo> getNormalPatternSingleLineList() {
        List<SingleLineInfo> infoList = new ArrayList<>(50);
        if (normalPatternSingleLineMatchingInfo != null && !"".equals(normalPatternSingleLineMatchingInfo)) {
            String[] items = normalPatternSingleLineMatchingInfo.split(";");
            if (items.length != 0) {
                for (String storageStr : items) {
                    SingleLineInfo info = SingleLineInfo.toSingleLineInfo(storageStr);
                    infoList.add(info);
                }
            }
        }
        return infoList;
    }


    /**
     * 整理结果
     * 普通图案+WILD类: 单线得分
     * JACKPOT: 1,3,5 集成三个 jackpot 图案，获得奖池 10% 奖励 <br>
     * BONUS: 1,3,5 集成三个 bonus，去 free spins 游戏 <br>
     * 如果是普通图案/WILD/WILD，则按照规则计算得分: <br>
     * 单线押注金额: 玩家总押注 / 总中奖数(固定50) <br>
     * 单线中奖金额: 单线押注金额 * 中奖图案倍率 <br>
     * 总得分: 所有单线中奖金额之和
     *
     * @param machineInfo 携带 machine 轴信息
     * @param seed        种子
     * @return 生成结果
     */
    public static XiaoyaogeSnapshot buildSnapshotRet(XiaoyaogeMachine machineInfo, int seed) {
        XiaoyaogeSnapshot ret = new XiaoyaogeSnapshot();

        // 0. 设置所属机器
        ret.setMachineId(machineInfo.getMachineId());

        // 1. 设置随机种子
        Random random = new Random(seed);
        ret.setSeed(seed);

        // 2. 获得当前快照的底盘
        int[][] chassis = SlotChassis.buildChassis(machineInfo.toRollerList(), random);
        // 3. 获得当前快照的底盘结果
        List<SingleLineInfo> singleLineInfoList = SlotChassis.calculateNormalPattern(chassis);

        // 4. 开始进行匹配统计
        carryOutBasicStatistics(chassis, singleLineInfoList, ret);

        // 5.如果当前快照完成 bonus，说明后续需根据随机出来的 freeSpins 次数，再执行若干次快照
        if (ret.isTriggerBonus()) {
            ret.setFreeSpinsTimes(WeightManager.linearRand(WeightManager.BONUS_WEIGHTS).getWeight());
        }
        // 6. 记录普通图案单线匹配记录
        ret.setNormalPatternSingleLineInfo(singleLineInfoList);
        return ret;
    }

    /**
     * 计算当前快照单线得分、jackpot、bonus 完成状况
     *
     * @param cleanChassis       整理后的底盘
     * @param singleLineInfoList 单线规则信息
     * @param snapshotRet        最终结果
     */
    private static void carryOutBasicStatistics(int[][] cleanChassis, List<SingleLineInfo> singleLineInfoList,
                                                XiaoyaogeSnapshot snapshotRet) {
        long finalScore = 0;

        for (SingleLineInfo singleLineInfo : singleLineInfoList) {
            switch (singleLineInfo.getFirstPatternType()) {
                case NORMAL:
                case WILD:
                case WILD_X2:
                    int rewardRate;
                    // 连线超过三次即可
                    if (singleLineInfo.getConsecutiveAim() != null && singleLineInfo.getConsecutiveAim() >= 3) {
                        // 如果是单线所属为 WILD/WILD_X2，则按照 WILD 面值
                        if (singleLineInfo.getBelongsToPattern().equals(XiaoyaogeConstant.WILD_ID)
                                || singleLineInfo.getBelongsToPattern().equals(XiaoyaogeConstant.WILD_X2_ID)) {
                            rewardRate = XiaoyaogeConstant.WILD_REWARD_TIME_MAP.get(singleLineInfo.getConsecutiveAim());
                        } else {
                            rewardRate = XiaoyaogeConstant.NORMAL_PATTERN_REWARD_TIME_MAP
                                    .get(singleLineInfo.getBelongsToPattern())
                                    .get(singleLineInfo.getConsecutiveAim());
                        }
                        // 设置初始倍率
                        singleLineInfo.setSourceMultiplyingPower(rewardRate);

                        if (singleLineInfo.getNeedDouble()) {
                            finalScore += rewardRate << 1;
                        } else {
                            finalScore += rewardRate;
                        }

                        System.out.println("当前规则 " + singleLineInfo + "倍率:" + rewardRate);
                    }
                    break;
                case JACKPOT:
                case BONUS:
                    int jackpotTimes = 0;
                    int bonusTimes = 0;
                    for (int i = 0; i < cleanChassis.length; i++) {
                        // 只要1,3,5列出现一次即可
                        if (i == 0 || i == 2 || i == 4) {
                            for (int j = 0; j < cleanChassis[i].length; j++) {
                                if (cleanChassis[i][j] == XiaoyaogeConstant.JACKPOT_ID) {
                                    jackpotTimes++;
                                    // 单列只要有出现一次即可
                                    break;
                                }
                            }

                            for (int j = 0; j < cleanChassis[i].length; j++) {
                                if (cleanChassis[i][j] == XiaoyaogeConstant.BONUS_ID) {
                                    bonusTimes++;
                                    // 单列只要有出现一次即可
                                    break;
                                }
                            }
                        }
                    }
                    if (jackpotTimes == 3) {
                        snapshotRet.setTriggerJackpot(true);
                    }
                    if (bonusTimes == 3) {
                        snapshotRet.setTriggerBonus(true);
                    }
                    break;
                default:
                    break;
            }
        }
        snapshotRet.setMultiplyingPower(finalScore);
    }

    /**
     * 复原底盘
     *
     * @param machineInfo 轴所在机器信息
     */
    public int[][] restoreChassis(XiaoyaogeMachine machineInfo) {
        if (machineInfo == null) {
            return null;
        }
        int[][] ret = SlotChassis.buildChassis(machineInfo.toRollerList(), new Random(seed));
        SlotChassis.printChassis(ret);
        return ret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public long getMultiplyingPower() {
        return multiplyingPower;
    }

    public void setMultiplyingPower(long multiplyingPower) {
        this.multiplyingPower = multiplyingPower;
    }

    public boolean isTriggerJackpot() {
        return triggerJackpot;
    }

    public void setTriggerJackpot(boolean triggerJackpot) {
        this.triggerJackpot = triggerJackpot;
    }

    public boolean isTriggerBonus() {
        return triggerBonus;
    }

    public void setTriggerBonus(boolean triggerBonus) {
        this.triggerBonus = triggerBonus;
    }

    public int getFreeSpinsTimes() {
        return freeSpinsTimes;
    }

    public void setFreeSpinsTimes(int freeSpinsTimes) {
        this.freeSpinsTimes = freeSpinsTimes;
    }

    public String getNormalPatternSingleLineMatchingInfo() {
        return normalPatternSingleLineMatchingInfo;
    }

    public void setNormalPatternSingleLineMatchingInfo(String normalPatternSingleLineMatchingInfo) {
        this.normalPatternSingleLineMatchingInfo = normalPatternSingleLineMatchingInfo;
    }

    @Override
    public String toString() {
        return "XiaoyaogeSnapshot{" +
                "id=" + id +
                ", seed=" + seed +
                ", multiplyingPower=" + multiplyingPower +
                ", triggerJackpot=" + triggerJackpot +
                ", triggerBonus=" + triggerBonus +
                ", freeSpinsTimes=" + freeSpinsTimes +
                ", pathInfo='" + normalPatternSingleLineMatchingInfo + '\'' +
                '}';
    }
}
