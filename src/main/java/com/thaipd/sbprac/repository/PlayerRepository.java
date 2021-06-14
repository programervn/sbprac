package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, PlayerRepositoryCustom {

    @Query(value = "{call lista_procedure()}", nativeQuery = true)
    List<Player> listAllPlayer();

    List<Player> findByTeamId(Long teamId);
    void deletePlayerById(Long playerID);
    List<Player> findByNameContainingIgnoreCase(String name);
    List<Player> findByNameLike(String name);

    @Modifying
    @Query(value = "delete from Player p where p.num=:num")
    void testDeletePlayerByNumber(@Param("num") Integer id);

    @Query(nativeQuery=true, value = "select * from player a where lower(a.name) like %:name%")
    List<Player> findPlayersByNameQuery(@Param("name") String name);
}
