package com.example.baithi.repository;

import com.example.baithi.model.PlayerIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerIndexRepository extends JpaRepository<PlayerIndex, Long> {
    Optional<PlayerIndex> findByPlayerIdAndIndexId(Long playerId, Long indexId);
}
