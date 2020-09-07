package com.hpw.server.slot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hpw.server.slot.constant.XiaoyaogeConstant;
import com.hpw.server.slot.util.Random;
import com.hpw.server.slot.util.WeightManager;

import java.util.List;

/**
 * 逍遥阁快照导致的结果
 *
 * @author lyl
 * @date 2020/9/2
 */
@TableName("g_xiaoyaoge_snapshot_result")
public class XiaoyaogeSnapshotResultLog {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 对应的快照id
     */
    @TableField("snapshotId")
    private Long snapshotId;

    /**
     * 图案得分
     */
    @TableField("patternScore")
    private long patternScore;


    /**
     * jackpot得分
     */
    @TableField("jackpotScore")
    private long jackpotScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public long getPatternScore() {
        return patternScore;
    }

    public void setPatternScore(long patternScore) {
        this.patternScore = patternScore;
    }

    public long getJackpotScore() {
        return jackpotScore;
    }

    public void setJackpotScore(long jackpotScore) {
        this.jackpotScore = jackpotScore;
    }

    @Override
    public String toString() {
        return "XiaoyaogeSnapshotResult{" +
                "id=" + id +
                ", snapshotId=" + snapshotId +
                ", patternScore=" + patternScore +
                ", jackpotScore=" + jackpotScore +
                '}';
    }
}
