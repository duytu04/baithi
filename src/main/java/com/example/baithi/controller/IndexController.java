package com.example.baithi.controller;

import com.example.baithi.model.PerformanceIndex;
import com.example.baithi.service.PlayerIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/indexes")
public class IndexController {
    private final PlayerIndexService playerIndexService;

    public IndexController(PlayerIndexService playerIndexService) {
        this.playerIndexService = playerIndexService;
    }

    @GetMapping
    public List<PerformanceIndex> list() {
        return playerIndexService.listIndexes();
    }
}
