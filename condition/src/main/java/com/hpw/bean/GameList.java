package com.hpw.bean;

import com.hpw.myenum.mappingValue.EntranceStateEnum;
import com.hpw.myenum.mappingValue.GameIdEnum;
import com.hpw.myenum.mappingValue.SizeTypeEnum;
import com.hpw.myenum.mappingValue.TagTypeEnum;
import com.hpw.utils.validatorgroup.InsertCheckGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Table: g_game_list
 */
public class GameList implements Serializable {
    /**
     * id, 可以看成游戏入口id
     *
     * Table:     g_game_list
     * Column:    id
     * Nullable:  false
     */
    @NotNull(message = "id 不能为空")
    private Integer id;

    /**
     * icon 类型 (0容器 1游戏)
     *
     * Table:     g_game_list
     * Column:    game_id
     * Nullable:  false
     */
    @NotNull(message = "gameId 不能为空", groups = {InsertCheckGroup.class})
    private GameIdEnum gameId;

    /**
     * 容器id, 表明所处的容器位置，0表示处于大厅中
     *
     * Table:     g_game_list
     * Column:    container_id
     * Nullable:  false
     */
    @NotNull(message = "containerId 不能为空", groups = {InsertCheckGroup.class})
    private Integer containerId;

    /**
     * 解锁条件, 采用 conditionId,conditionTargetValue 形式, 分号表示且, 管道符表示或; 比如 1,2000;2,19|3,299 表示要满足1条件达到2000且2条件达到19或者3条件达到299
     *
     * Table:     g_game_list
     * Column:    unlock_condition
     * Nullable:  true
     */
    private String unlockCondition;

    /**
     * icon 尺寸，(0小 1中 2大)
     *
     * Table:     g_game_list
     * Column:    size_type
     * Nullable:  false
     */
    @NotNull(message = "sizeType 不能为空", groups = {InsertCheckGroup.class})
    private SizeTypeEnum sizeType;

    /**
     * icon位置
     *
     * Table:     g_game_list
     * Column:    index
     * Nullable:  false
     */
    @NotNull(message = "index 不能为空", groups = {InsertCheckGroup.class})
    private Integer index;

    /**
     * 玩法，key, value 形式，比如 1,200 可以表示jackpot达到库存200，需要内部计算
     *
     * Table:     g_game_list
     * Column:    play
     * Nullable:  true
     */
    private String play;

    /**
     * 标签类型(0上新，1热门，2vip)
     *
     * Table:     g_game_list
     * Column:    tag_type
     * Nullable:  false
     */
    @NotNull(message = "tagType 不能为空", groups = {InsertCheckGroup.class})
    private TagTypeEnum tagType;

    /**
     * 当前入口状态 0下架 1敬请期待 2激活
     *
     * Table:     g_game_list
     * Column:    state
     * Nullable:  false
     */
    @NotNull(message = "state 不能为空", groups = {InsertCheckGroup.class})
    private EntranceStateEnum state;

    /**
     * 额外解锁条件，目前指消耗物品，用分号区分；当用户完成时，可忽视 unlock_condition
     *
     * Table:     g_game_list
     * Column:    extra_unlock_cost
     * Nullable:  true
     */
    private String extraUnlockCost;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameIdEnum getGameId() {
        return gameId;
    }

    public void setGameId(GameIdEnum gameId) {
        this.gameId = gameId;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public String getUnlockCondition() {
        return unlockCondition;
    }

    public void setUnlockCondition(String unlockCondition) {
        this.unlockCondition = unlockCondition;
    }

    public SizeTypeEnum getSizeType() {
        return sizeType;
    }

    public void setSizeType(SizeTypeEnum sizeType) {
        this.sizeType = sizeType;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public TagTypeEnum getTagType() {
        return tagType;
    }

    public void setTagType(TagTypeEnum tagType) {
        this.tagType = tagType;
    }

    public EntranceStateEnum getState() {
        return state;
    }

    public void setState(EntranceStateEnum state) {
        this.state = state;
    }

    public String getExtraUnlockCost() {
        return extraUnlockCost;
    }

    public void setExtraUnlockCost(String extraUnlockCost) {
        this.extraUnlockCost = extraUnlockCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gameId=").append(gameId);
        sb.append(", containerId=").append(containerId);
        sb.append(", unlockCondition=").append(unlockCondition);
        sb.append(", sizeType=").append(sizeType);
        sb.append(", index=").append(index);
        sb.append(", play=").append(play);
        sb.append(", tagType=").append(tagType);
        sb.append(", state=").append(state);
        sb.append(", extraUnlockCost=").append(extraUnlockCost);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}