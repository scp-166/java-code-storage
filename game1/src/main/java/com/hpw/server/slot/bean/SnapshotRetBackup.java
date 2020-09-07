package com.hpw.server.slot.bean;

import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.util.Random;
import com.hpw.server.slot.util.WeightManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 单次快照结果
 *
 * @author lyl
 * @date 2020/8/31
 */
public class SnapshotRetBackup {
    /**
     * 图案得分
     */
    private long patternScore;

    /**
     * 记录是否得到一次 jackpot
     */
    private boolean onceJackpot;

    /**
     * 记录是否得到一次 bonus
     */
    private boolean onceBonus;

    /**
     * 当前是否属于子快照
     */
    private boolean isSubSnapshot;

    /**
     * 当前快照触发了几次 bonus(包含子 bonus 的次数
     */
    @Deprecated
    private int bonusTriggerTimes;

    /**
     * 当前快照的 jackpot 总次数(包含子快照
     */
    @Deprecated
    private int jackpotTimes;

    /**
     * 当前子快照
     */
    @Deprecated
    private List<SnapshotRetBackup> subSnapshots;

    /**
     * 当 {@link SnapshotRetBackup#onceBonus} 为 true 时，表示免费押注次数
     */
    private int freeSpinsTimes;

    public long getPatternScore() {
        return patternScore;
    }

    public void setPatternScore(long patternScore) {
        this.patternScore = patternScore;
    }

    public boolean isOnceJackpot() {
        return onceJackpot;
    }

    public void setOnceJackpot(boolean onceJackpot) {
        this.onceJackpot = onceJackpot;
    }

    public boolean isOnceBonus() {
        return onceBonus;
    }

    public void setOnceBonus(boolean onceBonus) {
        this.onceBonus = onceBonus;
    }

    public boolean isSubSnapshot() {
        return isSubSnapshot;
    }

    public void setSubSnapshot(boolean subSnapshot) {
        this.isSubSnapshot = subSnapshot;
    }

    public int getFreeSpinsTimes() {
        return freeSpinsTimes;
    }

    public void setFreeSpinsTimes(int freeSpinsTimes) {
        this.freeSpinsTimes = freeSpinsTimes;
    }

    public int getBonusTriggerTimes() {
        return bonusTriggerTimes;
    }

    public void setBonusTriggerTimes(int bonusTriggerTimes) {
        this.bonusTriggerTimes = bonusTriggerTimes;
    }

    public List<SnapshotRetBackup> getSubSnapshots() {
        return subSnapshots;
    }

    public void setSubSnapshots(List<SnapshotRetBackup> subSnapshots) {
        this.subSnapshots = subSnapshots;
    }

    public int getJackpotTimes() {
        return jackpotTimes;
    }

    public void setJackpotTimes(int jackpotTimes) {
        this.jackpotTimes = jackpotTimes;
    }

    @Override
    public String toString() {
        return "SnapshotRet{" +
                "patternScore=" + patternScore +
                ", onceJackpot=" + onceJackpot +
                ", onceBonus=" + onceBonus +
                ", isSubSnapshot=" + isSubSnapshot +
                ", bonusTriggerTimes=" + bonusTriggerTimes +
                ", jackpotTimes=" + jackpotTimes +
                ", subSnapshots=" + subSnapshots +
                ", freeSpinsTimes=" + freeSpinsTimes +
                '}';
    }

    /**
     * 添加指定数量的 bonus 免费次数
     *
     * @param timesIncrease bonus免费次数增加量
     */
    public void addFreeBonusTimes(int timesIncrease) {
        this.freeSpinsTimes += timesIncrease;
    }

    /**
     * 增加指定数量的图案得分
     *
     * @param patternScoreIncrease 图案得分增加量
     */
    public void addPatternScore(long patternScoreIncrease) {
        this.patternScore += patternScoreIncrease;
    }

    /**
     * 增加一次 bonus 触发次数
     */
    public void increaseBonusTriggerTimes() {
        this.bonusTriggerTimes++;
    }

    /**
     * 增加指定数量的 bonus 触发次数
     *
     * @param subBonusTimesIncrease bonus 次数增加量
     */
    public void addSubBonusTriggerTimes(int subBonusTimesIncrease) {
        this.bonusTriggerTimes += subBonusTimesIncrease;
    }

    /**
     * 在 {@code onceBonus} 为 true 时，添加一个子快照
     *
     * @param subSnapshot 子快照
     */
    public void addSubSnapshot(SnapshotRetBackup subSnapshot) {
        if (this.subSnapshots == null) {
            subSnapshots = new ArrayList<>();
        }
        if (this.onceBonus) {
            this.subSnapshots.add(subSnapshot);
        }
    }

    public void increaseJackpotTimes() {
        this.jackpotTimes++;
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
     * @param machineInfo    携带 machine 轴信息
     * @param random         随机函数+种子
     * @param singleBet      单次押注数量
     * @param isSubSnapshot  当前是否为子快照
     * @param recursionCount 递归次数
     * @return 每一层的快照结果递归次数过多则返回 {@code null}, 需要抛弃此次快照
     */
    public static SnapshotRetBackup buildSnapshotRet(XiaoyaogeMachine machineInfo, Random random, long singleBet, boolean isSubSnapshot, int recursionCount) {
        // 递归次数过多，直接抛弃
        if (recursionCount > 5) {
            return null;
        }

        // 1. 获得当前快照的底盘
        int[][] chassis = SlotChassis.buildChassis(machineInfo.toRollerList(), random);
        // 2. 获得当前快照的底盘结果
        List<SingleLineInfo> singleLineInfoList = SlotChassis.calculateNormalPattern(chassis);

        // 3. 开始计算结果
        SnapshotRetBackup ret = new SnapshotRetBackup();
        ret.setSubSnapshot(isSubSnapshot);

        basicallyCalculate(chassis, singleBet, singleLineInfoList, ret);

        // 如果当前快照完成 bonus，说明要根据随机出来的 freeSpins 次数，再执行若干次快照
        if (ret.isOnceBonus()) {
            // fixme 前后端随机无法正常随，当做快照吧
            ret.setFreeSpinsTimes(WeightManager.linearRand(WeightManager.BONUS_WEIGHTS).getWeight());

            int times = WeightManager.linearRand(WeightManager.BONUS_WEIGHTS).getWeight();
            ret.setFreeSpinsTimes(times);
            ret.increaseBonusTriggerTimes();

            for (int i = 0; i < times; i++) {
                SnapshotRetBackup subSnapshotRet = buildSnapshotRet(machineInfo, random, singleBet, true, recursionCount++);
                if (subSnapshotRet == null) {
                    return null;
                }
                // 如果当前快照属于子快照, 那么它下面的子快照都要吃掉
                if (ret.isSubSnapshot()) {
                    ret.addPatternScore(subSnapshotRet.getPatternScore());
                    ret.addFreeBonusTimes(subSnapshotRet.getFreeSpinsTimes());
                }

                // 如果当前快照不属于子快照，则直接存储
                else {
                    ret.addSubSnapshot(subSnapshotRet);
                }

                // 触发 bonus 次数
                ret.addSubBonusTriggerTimes(subSnapshotRet.getBonusTriggerTimes());
            }
        }
        return ret;
    }

    /**
     * 计算当前快照单线得分、jackpot、bonus 完成状况
     *
     * @param cleanChassis       整理后的底盘
     * @param singleBet          单次押注
     * @param singleLineInfoList 单线规则信息
     * @param snapshotRet        最终结果
     */
    private static void basicallyCalculate(int[][] cleanChassis, long singleBet,
                                           List<SingleLineInfo> singleLineInfoList, SnapshotRetBackup snapshotRet) {
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

                        if (singleLineInfo.getNeedDouble()) {
                            finalScore += (rewardRate * singleBet) << 1;
                        } else {
                            finalScore += (rewardRate * singleBet);
                        }

                        System.out.println("当前规则 " + singleLineInfo + ", 得分: " + rewardRate * singleBet);
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
                        snapshotRet.setOnceJackpot(true);
                    }
                    if (bonusTimes == 3) {
                        snapshotRet.setOnceBonus(true);
                    }
                    break;
                default:
                    break;
            }
        }
        snapshotRet.setPatternScore(finalScore);
    }
}
