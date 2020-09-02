package com.hpw.dao.mapper;

import com.hpw.bean.SystemMailRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMailRecordPOMapper {
    public abstract int insertFullRecord(@Param("record") SystemMailRecordPO po);

    public abstract SystemMailRecordPO selectByUserId(@Param("userId") long userId, @Param("isSender") boolean isSender);

    public abstract int getAllRecordCountById(@Param("userId") long userId, @Param("isSender") boolean isSender);

    List<SystemMailRecordPO> getRecordLimit(@Param("userId") long userId, @Param("isSender") boolean isSender, @Param("limit0") int limitStart, @Param("limit1") int limitEnd);
}