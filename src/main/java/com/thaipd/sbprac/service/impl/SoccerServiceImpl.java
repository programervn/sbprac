package com.thaipd.sbprac.service.impl;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.entity.Team;
import com.thaipd.sbprac.repository.PlayerRepository;
import com.thaipd.sbprac.repository.PlayerRowMapper;
import com.thaipd.sbprac.repository.TeamRepository;
import com.thaipd.sbprac.service.SoccerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SoccerServiceImpl implements SoccerService {
    private static Logger logger = LoggerFactory.getLogger(SoccerServiceImpl.class);
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    public Optional<Team> findTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public List<Player> getAllTeamPlayers(Long teamId) {
        List<Player> result = playerRepository.findByTeamId(teamId);
        return result;
    }

    public Player addPlayer(Long teamId, String name, String position, int number) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            logger.error("Team not found");
            return null;
        }
        Team team = teamOptional.get();
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setPosition(position);
        newPlayer.setNum(number);
        newPlayer.setTeam(team);
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    @Transactional
    public int deletePlayerByNumber(Integer playerNum) {
        logger.debug("Delete player number: {}", playerNum);
        playerRepository.testDeletePlayerByNumber(playerNum);
        return 0;
    }

    //@Transactional
    public int deletePlayerByNumberCustom(Integer playerNum) {
        logger.debug("deletePlayerByNumberCustom  playerNum={}", playerNum);
        return playerRepository.deletePlayerCustom(playerNum);
    }

    public List<Player> findPlayersByName(String name) {
        logger.debug("Query player with name like: {}", name);
        return playerRepository.findByNameLike(name.toUpperCase());
    }

    public Player getPlayerById(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent())
            return playerOptional.get();
        else
            return null;
    }
}
