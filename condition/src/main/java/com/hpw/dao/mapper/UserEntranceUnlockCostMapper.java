package com.hpw.dao.mapper;

import com.hpw.bean.UserEntranceUnlockCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserEntranceUnlockCostMapper {
    List<UserEntranceUnlockCost> getAll();

    int addUserEntranceUnlockCost(@Param("cost") UserEntranceUnlockCost cost);
}