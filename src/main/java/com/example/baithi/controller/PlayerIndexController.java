package com.example.baithi.controller;

import com.example.baithi.dto.CreatePlayerIndexRequest;
import com.example.baithi.dto.PlayerIndexView;
import com.example.baithi.dto.UpdatePlayerIndexRequest;
import com.example.baithi.service.PlayerIndexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-index")
public class PlayerIndexController {
    private final PlayerIndexService playerIndexService;

    public PlayerIndexController(PlayerIndexService playerIndexService) {
        this.playerIndexService = playerIndexService;
    }

    @PostMapping
    public ResponseEntity<PlayerIndexView> create(@RequestBody CreatePlayerIndexRequest request) {
        PlayerIndexView created = playerIndexService.createPlayerIndex(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<PlayerIndexView> list() {
        return playerIndexService.listAll();
    }

    @PutMapping("/{id}")
    public PlayerIndexView update(@PathVariable long id, @RequestBody UpdatePlayerIndexRequest request) {
        return playerIndexService.updateValue(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        playerIndexService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
