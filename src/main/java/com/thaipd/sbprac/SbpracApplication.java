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

    @Override
    public void run(String... args) {
        Long teamId = 2L;
        Optional<Team> teamOptional = soccerService.findTeamById(teamId);
        if (teamOptional.isEmpty()) {
            logger.error("Not found team");
        } else {
            Team team = teamOptional.get();
            logger.info("Team info: {}", team.getName());
            List<Player> teamPlayers = team.getPlayers();
            for (Player p : teamPlayers) {
                logger.info("Player: {}", p);
            }
        }
        //soccerService.addPlayer(2L,"Xavi Hernandez", "Midfielder", 6);

//        List<Player> players = soccerService.getAllTeamPlayers(teamId);
//        for(Player player : players)
//        {
//            System.out.println("Introducing Barca player => " + player);
//        }
    }
}
