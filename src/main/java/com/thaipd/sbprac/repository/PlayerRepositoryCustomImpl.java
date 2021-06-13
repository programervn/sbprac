package com.thaipd.sbprac.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(PlayerRepositoryCustomImpl.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int deletePlayerCustom(Integer playerNumber) {
        String sql = "delete from player a where a.num = :player_number";
        return jdbcTemplate.update(sql, playerNumber);
    }
}
