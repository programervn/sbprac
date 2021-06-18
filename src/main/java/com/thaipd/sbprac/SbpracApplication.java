package com.thaipd.sbprac;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.entity.Team;
import com.thaipd.sbprac.entity.Book;
import com.thaipd.sbprac.service.BookService;
import com.thaipd.sbprac.service.OraclePackageService;
import com.thaipd.sbprac.service.PersonService;
import com.thaipd.sbprac.service.SoccerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
reference:
    https://dzone.com/articles/spring-boot-jpa-hibernate-oracle
    https://billykorando.com/2019/05/06/jpa-or-sql-in-a-spring-boot-application-why-not-both/
    https://mkyong.com/spring/spring-jdbctemplate-querying-examples/
 */
@SpringBootApplication
@EnableScheduling
public class SbpracApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SbpracApplication.class);
    @Autowired
    PersonService personService;
    @Autowired
    SoccerService soccerService;
    @Autowired
    OraclePackageService oraclePackageService;

    @Autowired
    BookService bookService;

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
        String REGEX = "\\D[0-9]{4,8}\\D";
        String INPUT = "01234";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);   // get a matcher object
        logger.info("Match? ", m.matches());
    }
    @Override
    public void run(String... args) {
        //soccerService.addPlayer(1L,"Xavi Hernandez", "Midfielder", 6);
        testGeneral();
    }
}
