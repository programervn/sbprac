package com.thaipd.sbprac;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.entity.Team;
import com.thaipd.sbprac.service.impl.PersonServiceImpl;
import com.thaipd.sbprac.service.SoccerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

/*
reference:
    https://dzone.com/articles/spring-boot-jpa-hibernate-oracle
    https://billykorando.com/2019/05/06/jpa-or-sql-in-a-spring-boot-application-why-not-both/
    https://mkyong.com/spring/spring-jdbctemplate-querying-examples/
 */
@SpringBootApplication
public class SbpracApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(SbpracApplication.class);
    @Autowired
    PersonServiceImpl personService;
    @Autowired
    SoccerService soccerService;

    public static void main(String[] args) {
        SpringApplication.run(SbpracApplication.class, args);
    }

    public void testJpa1() {
        Long teamId = 1L;
        Optional<Team> teamOptional = soccerService.findTeamById(teamId);
        if (teamOptional.isEmpty()) {
            logger.error("Not found team");
        } else {
            Team team = teamOptional.get();
            logger.info("Team info: {}", team);
            List<Player> teamPlayers = team.getPlayers();
            for (Player p : teamPlayers) {
                logger.info("Player: {}", p);
            }
        }
        soccerService.addPlayer(teamId,"Xavi Hernandez", "Midfielder", 6);

        List<Player> players = soccerService.getAllTeamPlayers(teamId);
        for(Player player : players)
        {
            logger.info("Introducing Barca player => {}" + player);
        }
    }

    public void testDeletePlayer() {
        Integer playerNumber = 6;
        soccerService.deletePlayerByNumber(playerNumber);
    }

    public void testInsertDuplicateNumber() {
        Long teamId = 2L;
        soccerService.addPlayer(teamId,"Xavi Hernandez", "Midfielder", 3);
    }

    public void testDeletePlayerCustom() {
        Integer playerNumber = 6;
        int n = soccerService.deletePlayerByNumberCustom(playerNumber);
        logger.info("Number player deleted: {}", n);
    }

    public void testGetPlayerByName() {
        String name = "mes";
        List<Player> playerList = soccerService.findPlayersByName(name);
        logger.debug("Total player find: {}", playerList.size());
        for (Player p : playerList) {
            logger.info("{}", p);
        }
    }

    public void testGeneral() {
        Player player = soccerService.getPlayerById(1L);
        logger.info("{}", player);
    }
    @Override
    public void run(String... args) {
        //soccerService.addPlayer(1L,"Xavi Hernandez", "Midfielder", 6);
        testGeneral();
    }
}
