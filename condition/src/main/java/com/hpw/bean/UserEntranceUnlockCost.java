package com.hpw.bean;

import com.hpw.utils.validatorgroup.FinalCheckGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Table: g_user_entrance_unlock_cost
 */
public class UserEntranceUnlockCost implements Serializable {
    /**
     * 用户id
     *
     * Table:     g_user_entrance_unlock_cost
     * Column:    user_id
     * Nullable:  false
     */
    @NotNull(message = "userId 不能为空")
    private Long userId;

    /**
     * 游戏入口id, 对应 g_game_list 的 id
     *
     * Table:     g_user_entrance_unlock_cost
     * Column:    entrance_id
     * Nullable:  false
     */
    @NotNull(message = "entranceId 不能为空")
    private Integer entranceId;

    /**
     * 当时解锁花费，格式为 item 的格式
     *
     * Table:     g_user_entrance_unlock_cost
     * Column:    unlock_cost
     * Nullable:  false
     */
    @NotBlank(message = "unlockCost 不能为空")
    private String unlockCost;

    /**
     * 创建时间
     *
     * Table:     g_user_entrance_unlock_cost
     * Column:    create_time
     * Nullable:  false
     */
    @NotNull(message = "createTime 不能为空", groups = {FinalCheckGroup.class})
    private Long createTime;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(Integer entranceId) {
        this.entranceId = entranceId;
    }

    public String getUnlockCost() {
        return unlockCost;
    }

    public void setUnlockCost(String unlockCost) {
        this.unlockCost = unlockCost;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", entranceId=").append(entranceId);
        sb.append(", unlockCost=").append(unlockCost);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}