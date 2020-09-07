package com.hpw.server.slot.bean;

import com.hpw.server.slot.constant.PatternTypeEnum;
import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.util.Random;
import com.hpw.server.slot.util.SlotConfigCache;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 逍遥阁底盘
 *
 * @author lyl
 * @date 2020/8/31
 */
public class SlotChassis {

    /**
     * 获得初始底盘
     *
     * @param rollerList 轴信息
     * @param random     随机函数+随机种子
     * @return 初始底盘
     */
    private static int[][] buildSourceChassis(List<List<Integer>> rollerList, Random random) {
        if (rollerList == null || rollerList.size() == 0) {
            return null;
        }
        int num;
        int startIndex;

        int[][] chassis = new int[XiaoyaogeConstant.COLUMN_SIZE][XiaoyaogeConstant.ROW_SIZE];

        for (int i = 0; i < rollerList.size(); i++) {
            // 每一个轴都要进行一次 rand
            num = random.getInt();
            startIndex = num % rollerList.get(i).size();
            List<Integer> columns = getRingValue(rollerList.get(i), startIndex, XiaoyaogeConstant.ROW_SIZE);
            for (int j = 0; j < XiaoyaogeConstant.ROW_SIZE; j++) {
                chassis[i][j] = columns.get(j);
            }
        }
        buildTable("source", chassis);
        System.out.println("sourceChassis: " + Arrays.deepToString(chassis));
        System.out.println("sourceChassis");
        printChassis(chassis);
        return chassis;
    }


    /**
     * 整理底盘，主要针对 wildX2 在第三栏出现 WILD 时，整个列切换为 WILD X2，且 所有 WILD 转变为 WILD，其他普通图案随机替换出两个 WILD
     *
     * @param chassis 初始底盘
     * @param random  随机函数+随机种子
     * @return 按照要求进行整理后的底盘
     */
    private static int[][] cleanChassis(int[][] chassis, Random random) {
        if (chassis == null) {
            return new int[][]{};
        }
        // 奇数除以2，获得的就是中间位置的索引了
        int mid = chassis.length / 2;
        if (mid == 0) {
            return new int[][]{};
        }

        // 中间栏是否出现 WILD
        boolean needClean = false;
        for (int patternId : chassis[mid]) {
            if (XiaoyaogeConstant.WILD_ID == patternId) {
                needClean = true;
                break;
            }
        }
        if (needClean) {
            // 中间一行全部设置为 WILD_X2
            Arrays.fill(chassis[mid], XiaoyaogeConstant.WILD_X2_ID);

            LinkedList<Integer> checkQueue = new LinkedList<>();
            List<String> checkQueueIndexList = new ArrayList<>();
            for (int i = 0; i < chassis.length; i++) {
                // 剔除中间一行
                if (i == mid) {
                    continue;
                }
                for (int j = 0; j < chassis[i].length; j++) {
                    // 填充剩下的普通图标进检查队列中
                    for (Integer pic : XiaoyaogeConstant.BASIC_PATTERN_MAP.keySet()) {
                        if (pic.equals(chassis[i][j])) {
                            checkQueueIndexList.add(i + XiaoyaogeConstant.SEPARATOR + j);
                            checkQueue.offerLast(chassis[i][j]);
                        }
                    }

                    // wild 全部替换为 wild_x2
                    if (isWild(chassis[i][j])) {
                        chassis[i][j] = XiaoyaogeConstant.WILD_X2_ID;
                    }
                }
            }
            // 检查队列中找到两个图标进行替换
            int num;
            num = random.getInt();
            String firstIndex = searchReplaceIndex(checkQueue, checkQueueIndexList, num);
            num = random.getInt();
            String secondIndex = searchReplaceIndex(checkQueue, checkQueueIndexList, num);

            String[] indexArr;
            indexArr = firstIndex.split(XiaoyaogeConstant.SEPARATOR);
            chassis[Integer.parseInt(indexArr[0])][Integer.parseInt(indexArr[1])] = XiaoyaogeConstant.WILD_X2_ID;

            indexArr = secondIndex.split(XiaoyaogeConstant.SEPARATOR);
            chassis[Integer.parseInt(indexArr[0])][Integer.parseInt(indexArr[1])] = XiaoyaogeConstant.WILD_X2_ID;
        }

        buildTable("clean", chassis);
        System.out.println("cleanChassis: " + Arrays.deepToString(chassis));
        System.out.println("cleanChassis: ");
        printChassis(chassis);
        return chassis;
    }

    /**
     * 创建底盘
     */
    public static int[][] buildChassis(List<List<Integer>> rollerList, Random random) {
        return cleanChassis(buildSourceChassis(rollerList, random), random);
    }

    public static void printChassis(int[][] chassis) {
        String[][] ret = new String[chassis.length][chassis[0].length];
        for (int i = 0; i < chassis.length; i++) {
            for (int j = 0; j < chassis[i].length; j++) {
                ret[i][j] = XiaoyaogeConstant.ALL_PATTERN_MAP.get(chassis[i][j]);
            }
        }
        System.out.println(Arrays.deepToString(ret));
    }

    private static boolean isNormal(Integer patternId) {
        for (Integer p : XiaoyaogeConstant.BASIC_PATTERN_MAP.keySet()) {
            if (p.equals(patternId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWild(Integer patternId) {
        return XiaoyaogeConstant.WILDS_MAP.get(patternId) != null;
    }

    private static boolean isWildX2(Integer patternId) {
        return XiaoyaogeConstant.WILD_X2_ID.equals(patternId);
    }

    private static boolean isBonus(Integer patternId) {
        return XiaoyaogeConstant.BONUS_ID.equals(patternId);
    }

    private static boolean isJackpot(Integer patternId) {
        return XiaoyaogeConstant.JACKPOT_ID.equals(patternId);
    }

    /**
     * 处理 普通图案和 WILD 的基础玩法
     *
     * @param chassis 底盘
     */
    public static List<SingleLineInfo> calculateNormalPattern(int[][] chassis) {

        // 记录 chassis 作用完一条规则后的结果
        List<SingleLineInfo> singleLineInfoList = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            singleLineInfoList.add(new SingleLineInfo());
        }
        int pathIndex = 0;


        // 循环判断 50 个规则
        for (Map.Entry<Integer, Map<Integer, Integer>> ruleItem : SlotConfigCache.RULE_POINT_MAP.entrySet()) {
            singleLineInfoList.get(pathIndex).setPathRuleId(ruleItem.getKey());
            singleLineInfoList.get(pathIndex).setRuleMap(ruleItem.getValue());
            Integer currentPatternId;
            boolean checkFail = false;

            // 存储一条规则下五个轴上的元素
            List<Integer> rollerItemCheckList = new ArrayList<>(5);
            int normalPatternCheckIndex = 0;

            // 循环五条轴
            for (Map.Entry<Integer, Integer> rule : ruleItem.getValue().entrySet()) {
                if (checkFail) {
                    break;
                }

                int column = rule.getKey();
                int row = rule.getValue();
                currentPatternId = chassis[column][row];

                // 检查首轴图案是什么类型
                if (column == 0) {
                    if (isNormal(currentPatternId)) {
                        singleLineInfoList.get(pathIndex).setFirstPatternType(PatternTypeEnum.NORMAL);
                    } else if (isWild(currentPatternId)) {
                        singleLineInfoList.get(pathIndex).setFirstPatternType(PatternTypeEnum.WILD);
                    } else if (isWildX2(currentPatternId)) {
                        singleLineInfoList.get(pathIndex).setFirstPatternType(PatternTypeEnum.WILD_X2);
                    } else if (isJackpot(currentPatternId)) {
                        singleLineInfoList.get(pathIndex).setFirstPatternType(PatternTypeEnum.JACKPOT);
                    } else if (isBonus(currentPatternId)) {
                        singleLineInfoList.get(pathIndex).setFirstPatternType(PatternTypeEnum.BONUS);
                    }
                    // 连线所属图案，注意 WILD 和 WILD_X2 后续可能会变动
                    singleLineInfoList.get(pathIndex).setBelongsToPattern(currentPatternId);
                }

                rollerItemCheckList.add(currentPatternId);

                // 当前图案是 WILD_X2，则设置翻倍
                if (isWildX2(currentPatternId)) {
                    singleLineInfoList.get(pathIndex).setNeedDouble(true);
                }

                // 普通图案从第二个图案开始检查
                if (normalPatternCheckIndex > 0) {
                    if (!checkNormalContinue(rollerItemCheckList.get(0), rollerItemCheckList.get(normalPatternCheckIndex - 1),
                            rollerItemCheckList.get(normalPatternCheckIndex), singleLineInfoList.get(pathIndex))) {
                        checkFail = true;
                    }
                }
                normalPatternCheckIndex++;
            }
            pathIndex++;
        }

        // int i = 0;
        // for (SingleLineInfo singleLineInfo : singleLineInfoList) {
        //     System.out.println(++i + " ->" + singleLineInfo);
        // }

        return singleLineInfoList;
    }

    /**
     * 根据当前图案，判断是否需要继续进行规则判断
     *
     * @param firstPatternId   第一个图案的id
     * @param frontPatternId   前一个图案的id
     * @param currentPatternId 当前图案的id
     * @param singleLineInfo   当前路径规则记录
     * @return 是否需要继续判断
     */
    private static boolean checkNormalContinue(Integer firstPatternId, Integer frontPatternId, Integer currentPatternId, SingleLineInfo singleLineInfo) {
        switch (singleLineInfo.getFirstPatternType()) {
            case NORMAL:
                if ((currentPatternId.equals(firstPatternId)) || isWild(currentPatternId) || isWildX2(currentPatternId)) {
                    singleLineInfo.checkAndIncreaseConsecutiveAim();
                    return true;
                } else {
                    return false;
                }
            case WILD:
            case WILD_X2:
                // 遇到普通图案，且连线信息所属还是 WILD，则更新所属
                if (isNormal(currentPatternId) && (isWild(singleLineInfo.getBelongsToPattern()) || isWildX2(singleLineInfo.getBelongsToPattern()))) {
                    singleLineInfo.setBelongsToPattern(currentPatternId);
                } else {
                    // 如果当前图案非 WILD，且和连线信息所属不一致，则绝对不匹配
                    if (!(isWild(currentPatternId) || isWildX2(currentPatternId))) {
                        if (!currentPatternId.equals(singleLineInfo.getBelongsToPattern())) {
                            return false;
                        }
                    }
                }
                if ((currentPatternId.equals(frontPatternId))
                        || isWild(frontPatternId) || isWildX2(frontPatternId)
                        || isWild(currentPatternId) || isWildX2(currentPatternId)) {
                    singleLineInfo.checkAndIncreaseConsecutiveAim();
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }


    /**
     * 获得检查队列中原图标对应的坐标
     *
     * @param checkQueue          检查队列, 仅包含普通图案
     * @param checkQueueIndexList 检查队列 {@code checkQueue} 中对应的各个原坐标队列
     * @param num                 用于计算替换位置索引的值
     * @return 检查队列中被删除元素的原坐标
     */
    private static String searchReplaceIndex(List<Integer> checkQueue, List<String> checkQueueIndexList, int num) {
        int removeIndex = num % (checkQueue.size());
        checkQueue.remove(removeIndex);
        return checkQueueIndexList.remove(removeIndex);
    }

    /**
     * 环形取值
     *
     * @param sourceList 待取值的list
     * @param index      从哪里开始取值
     * @param targetNum  希望取到几个值
     * @return 环形取值后的 list
     */
    private static List<Integer> getRingValue(List<Integer> sourceList, int index, int targetNum) {
        int unReachIndex = sourceList.size();
        List<Integer> retList = new ArrayList<>(targetNum);
        for (int i = 0; i < targetNum; i++) {
            if (index == unReachIndex) {
                index = 0;
            }
            retList.add(sourceList.get(index));
            index++;
        }
        return retList;
    }

    /**
     * 创建 table
     * springboot 需要设置     ide 的run--->VM Options里加上一句 -Djava.awt.headless=false
     *
     * @param title   标题
     * @param rowData 数据
     */
    public static void buildTable(String title, int[][] rowData) {
        Object[][] newData = new Object[XiaoyaogeConstant.ROW_SIZE][XiaoyaogeConstant.COLUMN_SIZE];
        //矩阵行列进行交换, 且转换为可视化字符串
        int temp;
        for (int i = 0; i < XiaoyaogeConstant.COLUMN_SIZE; i++) {
            for (int j = 0; j < XiaoyaogeConstant.ROW_SIZE; j++) {
                temp = rowData[i][j];
                if (XiaoyaogeConstant.ALL_PATTERN_MAP.get(temp) != null) {
                    newData[j][i] = XiaoyaogeConstant.ALL_PATTERN_MAP.get(temp);
                }
            }
        }

        JFrame jf = new JFrame(title);

        // 创建内容面板，使用边界布局
        JPanel panel = new JPanel(new BorderLayout());

        Object[] columnNames = {"第一行", "第二行", "第三列", "第四列", "第五列"};
        // 创建一个表格，指定 所有行数据 和 表头
        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
        // 创建一个表格，指定 所有行数据 和 表头
        JTable table = new JTable(model);

        // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        // 把 表格内容 添加到容器中心
        panel.add(table, BorderLayout.CENTER);

        jf.setContentPane(panel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
