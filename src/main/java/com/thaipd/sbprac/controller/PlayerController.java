package com.thaipd.sbprac.controller;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.service.SoccerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    @Autowired
    SoccerService soccerService;

    @GetMapping("/")
    public List<Player> findAllPlayer() {
        return soccerService.procFindAll(1);
    }
}
