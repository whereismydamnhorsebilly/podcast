package com.example.controller;

import com.example.model.SimpleStat;
import com.example.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatService statService;

    @GetMapping("/getAll")
    public List<SimpleStat> getAll() {
        return statService.getAllStats();
    }
}
