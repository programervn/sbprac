package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;

import java.util.List;

public interface PlayerRepositoryCustom {
    public int deletePlayerCustom(Integer playerNumber);
    public Player getPlayerById(Long id);
    public int getTotalPlayerCount();

    public List<Player> procFindAll(int orderByDate);
    public Integer funcGetCount(Integer i, String s);

    public String funcGetCount2(Integer i, String s);
}
