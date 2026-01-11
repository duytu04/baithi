package com.example.baithi.service;

import com.example.baithi.dto.CreatePlayerIndexRequest;
import com.example.baithi.dto.PlayerIndexView;
import com.example.baithi.dto.UpdatePlayerIndexRequest;
import com.example.baithi.exception.BadRequestException;
import com.example.baithi.exception.ConflictException;
import com.example.baithi.exception.NotFoundException;
import com.example.baithi.model.PerformanceIndex;
import com.example.baithi.model.Player;
import com.example.baithi.model.PlayerIndex;
import com.example.baithi.repository.PerformanceIndexRepository;
import com.example.baithi.repository.PlayerIndexRepository;
import com.example.baithi.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerIndexService {
    private final PlayerRepository playerRepository;
    private final PerformanceIndexRepository indexRepository;
    private final PlayerIndexRepository playerIndexRepository;

    public PlayerIndexService(PlayerRepository playerRepository,
                              PerformanceIndexRepository indexRepository,
                              PlayerIndexRepository playerIndexRepository) {
        this.playerRepository = playerRepository;
        this.indexRepository = indexRepository;
        this.playerIndexRepository = playerIndexRepository;
    }

    @Transactional
    public PlayerIndexView createPlayerIndex(CreatePlayerIndexRequest request) {
        String playerName = requireText(request.getPlayerName(), "playerName");
        Integer age = request.getAge();
        if (age == null || age <= 0) {
            throw new BadRequestException("age must be greater than 0");
        }
        PerformanceIndex index = findIndex(request.getIndexName());
        double value = requireValue(request.getValue(), "value");
        validateValueInRange(value, index);

        Player player = playerRepository.findByNameIgnoreCase(playerName)
                .orElseGet(() -> playerRepository.save(new Player(playerName, playerName, age)));

        Optional<PlayerIndex> existing = playerIndexRepository.findByPlayerIdAndIndexId(player.getId(), index.getId());
        if (existing.isPresent()) {
            throw new ConflictException("player already has this index, please update instead");
        }

        PlayerIndex playerIndex = playerIndexRepository.save(new PlayerIndex(player, index, value));
        return toView(player, index, playerIndex);
    }

    @Transactional(readOnly = true)
    public List<PlayerIndexView> listAll() {
        return playerIndexRepository.findAll().stream()
                .map(this::toView)
                .sorted(Comparator.comparing(PlayerIndexView::getPlayerName)
                        .thenComparing(PlayerIndexView::getIndexName))
                .collect(Collectors.toList());
    }

    @Transactional
    public PlayerIndexView updateValue(long id, UpdatePlayerIndexRequest request) {
        double value = requireValue(request.getValue(), "value");
        PlayerIndex playerIndex = playerIndexRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("player index not found"));

        Player player = playerIndex.getPlayer();
        PerformanceIndex index = playerIndex.getIndex();

        validateValueInRange(value, index);
        playerIndex.setValue(value);
        return toView(player, index, playerIndex);
    }

    @Transactional
    public void delete(long id) {
        PlayerIndex playerIndex = playerIndexRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("player index not found"));
        playerIndexRepository.deleteById(playerIndex.getId());
    }

    @Transactional(readOnly = true)
    public List<PerformanceIndex> listIndexes() {
        return indexRepository.findAll().stream()
                .sorted(Comparator.comparing(PerformanceIndex::getName))
                .collect(Collectors.toList());
    }

    private PlayerIndexView toView(PlayerIndex playerIndex) {
        Player player = playerIndex.getPlayer();
        PerformanceIndex index = playerIndex.getIndex();
        return toView(player, index, playerIndex);
    }

    private PlayerIndexView toView(Player player, PerformanceIndex index, PlayerIndex playerIndex) {
        return new PlayerIndexView(
                playerIndex.getId(),
                player.getName(),
                player.getAge(),
                index.getName(),
                playerIndex.getValue(),
                index.getValueMin(),
                index.getValueMax()
        );
    }

    private PerformanceIndex findIndex(String indexName) {
        String normalized = requireText(indexName, "indexName");
        return indexRepository.findByNameIgnoreCase(normalized)
                .orElseThrow(() -> new BadRequestException("indexName is not supported"));
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException(fieldName + " is required");
        }
        return value.trim();
    }

    private double requireValue(Double value, String fieldName) {
        if (value == null) {
            throw new BadRequestException(fieldName + " is required");
        }
        return value;
    }

    private void validateValueInRange(double value, PerformanceIndex index) {
        if (value < index.getValueMin() || value > index.getValueMax()) {
            throw new BadRequestException("value must be between " + index.getValueMin() + " and " + index.getValueMax());
        }
    }
}
