package com.hpw.dao.mapper;

import com.hpw.bean.BroadcastMailInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface BroadcastMailInfoPOMapper {
    public abstract int insertFullInfo(@Param("info") BroadcastMailInfoPO info);
    public abstract int insertFullInfoAndReturnId(@Param("info") BroadcastMailInfoPO info);
    public abstract List<BroadcastMailInfoPO> selectAllInfo();
    public abstract List<BroadcastMailInfoPO> selectAllOrderBySendingTime();
}