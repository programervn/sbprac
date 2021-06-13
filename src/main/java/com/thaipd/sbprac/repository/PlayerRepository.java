package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long teamId);
    void deletePlayerById(Long playerID);

    @Modifying
    @Query(value = "delete from Player p where p.num=:num")
    void testDeletePlayerByNumber(@Param("num") Integer id);
}
