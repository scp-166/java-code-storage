package com.hpw.dao.mapper;

import com.hpw.bean.GameList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameListMapper {
    List<GameList> getAll();

    int addGameList(@Param("gameList") GameList gameList);
}