package com.hpw.dao.impl;

import com.hpw.LocalMapperFactory;
import com.hpw.bean.UserEntranceUnlockCost;
import com.hpw.dao.IUserEntranceUnlockCostDao;
import com.hpw.dao.mapper.UserEntranceUnlockCostMapper;
import com.hpw.utils.ComValidatorUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserEntranceUnlockCostDao implements IUserEntranceUnlockCostDao {
    private static class Holder {
        public static UserEntranceUnlockCostDao instance = new UserEntranceUnlockCostDao();
    }

    private UserEntranceUnlockCostMapper mapper =
            LocalMapperFactory.getInstance().getMapper(UserEntranceUnlockCostMapper.class);

    public static UserEntranceUnlockCostDao getInstance() {
        return Holder.instance;
    }

    private Map<String, UserEntranceUnlockCost> cacheCostRecord = new ConcurrentHashMap<>();
    private static final String SEPARATOR = ";";

    @Override
    public List<UserEntranceUnlockCost> getUserEntranceUnlockCost() {
        if (CollectionUtils.isEmpty(cacheCostRecord)) {
            List<UserEntranceUnlockCost> costs = mapper.getAll();
            for (UserEntranceUnlockCost cost : costs) {
                cacheCostRecord.put(cost.getUserId() + SEPARATOR + cost.getEntranceId(), cost);
            }
            return costs;
        }

        Collection<UserEntranceUnlockCost> cacheValue = cacheCostRecord.values();
        List<UserEntranceUnlockCost> ret = new ArrayList<>(cacheValue.size());
        ret.addAll(cacheValue);

        return ret;

    }

    @Override
    public UserEntranceUnlockCost getUserEntranceUnlockCostById(long userId, int entranceId) {
        if (CollectionUtils.isEmpty(cacheCostRecord)) {
            getUserEntranceUnlockCost();
        }
        return cacheCostRecord.get(userId + SEPARATOR + entranceId);
    }

    @Override
    public boolean addUserEntranceUnlockCost(UserEntranceUnlockCost cost) {
        String errCode = ComValidatorUtil.validateEntityFailFast(cost);
        if (Objects.nonNull(errCode)) {
            // todo logger
            System.out.println(errCode);
            return false;
        }

        if (Objects.isNull(cost.getCreateTime())) {
            cost.setCreateTime(System.currentTimeMillis());
        }

        int ret = mapper.addUserEntranceUnlockCost(cost);
        if (checkRet(ret)) {
            cacheCostRecord.put(cost.getUserId() + SEPARATOR + cost.getEntranceId(), cost);
        }

        return checkRet(ret);
    }

    private boolean checkRet(int ret) {
        return ret > 0;
    }
}
