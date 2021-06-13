package com.thaipd.sbprac.repository;

import com.thaipd.sbprac.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByPlayers(Long playerId);
}
