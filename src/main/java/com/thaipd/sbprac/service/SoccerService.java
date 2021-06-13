package com.thaipd.sbprac.service;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.entity.Team;

import java.util.List;
import java.util.Optional;

public interface SoccerService {
    public Optional<Team> findTeamById(Long teamId);
    public List<Player> getAllTeamPlayers(Long teamId);
    public Player addPlayer(Long teamId, String name, String position, int number);
    public int deletePlayerByNumber(Integer playerNum);
    public int deletePlayerByNumberCustom(Integer playerNum);
}
