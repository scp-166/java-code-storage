package com.hpw.dao;

import com.hpw.bean.UserEntranceUnlockCost;

import java.util.List;

public interface IUserEntranceUnlockCostDao {
    List<UserEntranceUnlockCost> getUserEntranceUnlockCost();

    public UserEntranceUnlockCost getUserEntranceUnlockCostById(long userId, int entranceId);

    boolean addUserEntranceUnlockCost(UserEntranceUnlockCost cost);
}
