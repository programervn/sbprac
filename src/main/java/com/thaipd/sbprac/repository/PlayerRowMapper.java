package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;
import com.thaipd.sbprac.entity.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRowMapper implements RowMapper<Player> {
    @Override
    public Player mapRow(ResultSet rs, int i) throws SQLException {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setName(rs.getString("name"));
        player.setNum(rs.getInt("num"));
        player.setPosition(rs.getString("position"));
        player.setTeamId(rs.getLong("team_id"));
        return player;
    }
}
