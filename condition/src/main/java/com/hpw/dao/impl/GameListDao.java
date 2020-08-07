package com.hpw.dao.impl;

import com.hpw.LocalMapperFactory;
import com.hpw.bean.GameList;
import com.hpw.dao.IGameListDao;
import com.hpw.dao.mapper.GameListMapper;
import com.hpw.utils.ComValidatorUtil;
import com.hpw.utils.validatorgroup.InsertCheckGroup;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameListDao implements IGameListDao {
    private static class Holder {
        public static GameListDao instance = new GameListDao();
    }

    private GameListMapper mapper = LocalMapperFactory.getInstance().getMapper(GameListMapper.class);

    public static GameListDao getInstance() {
        return Holder.instance;
    }

    private Map<Integer, GameList> cacheGameList = new ConcurrentHashMap<>();

    @Override
    public List<GameList> getGameList() {
        if (CollectionUtils.isEmpty(cacheGameList)) {
            List<GameList> gameLists = mapper.getAll();
            for (GameList gameList : gameLists) {
                cacheGameList.put(gameList.getId(), gameList);
            }
            return gameLists;
        }

        Collection<GameList> cacheValue = cacheGameList.values();
        List<GameList> ret = new ArrayList<>(cacheValue.size());
        ret.addAll(cacheValue);

        return ret;
    }

    public GameList getGameListById(int id) {
        if (CollectionUtils.isEmpty(cacheGameList)) {
            getGameList();
        }
        return cacheGameList.get(id);
    }


    @Override
    public boolean addGameList(GameList gameList) {
        // todo validator
        String errCode = ComValidatorUtil.validateEntityFailFast(gameList, InsertCheckGroup.class);
        if (Objects.nonNull(errCode)) {
            // todo logger
            System.out.println(errCode);
            return false;
        }

        int ret = mapper.addGameList(gameList);
        if (checkRet(ret)) {
            cacheGameList.put(gameList.getId(), gameList);
        }
        return checkRet(ret);
    }

    private boolean checkRet(int ret) {
        return ret > 0;
    }
}
