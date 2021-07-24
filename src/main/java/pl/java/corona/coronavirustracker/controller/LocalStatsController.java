package pl.java.corona.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.java.corona.coronavirustracker.model.LocalStats;
import pl.java.corona.coronavirustracker.service.LocalStatsService;

import java.util.List;

@RestController
public class LocalStatsController {

    private LocalStatsService localStatsService;

    @Autowired
    public LocalStatsController(LocalStatsService localStatsService) {
        this.localStatsService = localStatsService;
    }

    @GetMapping("/cases")
    public List<LocalStats> getAllStats() {
        return localStatsService.getAllStats();
    }

    @GetMapping("/cases/top")
    public List<LocalStats> getAllOrderByTotalCases() {
        return localStatsService.getAllOrderByTotalCases();
    }
}
