package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;

public interface PlayerRepositoryCustom {
    public int deletePlayerCustom(Integer playerNumber);
    public Player getPlayerById(Long id);
    public int getTotalPlayerCount();
}
