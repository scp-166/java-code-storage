package com.hpw.dao;

import com.hpw.bean.GameList;

import java.util.List;

public interface IGameListDao {
    List<GameList> getGameList();

    GameList getGameListById(int id);

    boolean addGameList(GameList gameList);
}
