package com.hpw.myenum.convert;

/**
 * 游戏类型枚举
 */
public enum GameIdEnum implements BaseCodeTypeEnum{
    /**
     * 类似游戏合集，可以直接查看次级界面
     */
    CONTAINER(0, "容器"),

    /**
     * 表示需要下载才能进行查看
     */
    GAME(1, "游戏")
    ;
    private Integer id;
    private String desc;

    GameIdEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "GameIdEnum{" +
                "gameId=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public Integer getCode() {
        return getId();
    }
}
