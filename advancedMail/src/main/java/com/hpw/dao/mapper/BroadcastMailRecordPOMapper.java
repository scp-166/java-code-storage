package com.hpw.dao.mapper;

import com.hpw.bean.BroadcastMailRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BroadcastMailRecordPOMapper {
    int addRecord(@Param("record") BroadcastMailRecordPO record);

    List<BroadcastMailRecordPO> getRecordByReceiverId(@Param("receiverId") long receiverId);

    int updateRecordList(@Param("poList") List<BroadcastMailRecordPO> poList);

    int logicDeleteByIdList(@Param("poList") List<BroadcastMailRecordPO> deleteMailList);
}