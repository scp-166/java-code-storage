package com.hpw.dao.mapper;

import com.hpw.bean.NormalMailRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NormalMailRecordPOMapper {
    List<NormalMailRecordPO> getRecordListByUserId(@Param("userId") long userId, @Param("isSender") boolean isSender);

    int addRecord(@Param("record") NormalMailRecordPO record);

    int updateRecordList(@Param("poList") List<NormalMailRecordPO> poList);

    int logicDeleteByIdList(@Param("poList") List<NormalMailRecordPO> deleteMailList);
}