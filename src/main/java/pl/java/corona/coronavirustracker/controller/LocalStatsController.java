package pl.java.corona.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.java.corona.coronavirustracker.model.LocalStats;
import pl.java.corona.coronavirustracker.service.LocalStatsService;


import java.util.List;


@Controller
public class LocalStatsController {

    private final LocalStatsService localStatsService;

    @Autowired
    public LocalStatsController(LocalStatsService localStatsService) {
        this.localStatsService = localStatsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<LocalStats> allStats = localStatsService.getAllOrderByTotalCases();

        model.addAttribute("locationStats", allStats);

        int todayTotalCases = allStats.stream()
                .map(LocalStats::getDailyConfirmedCases)
                .reduce(0, Integer::sum);

        model.addAttribute("todayTotalCases", todayTotalCases);

        int totalCases = allStats.stream()
                .map(LocalStats::getTotalCases)
                .reduce(0, Integer::sum);

        model.addAttribute("totalCases", totalCases);

        return "home";
    }

    @GetMapping("/map")
    public String map(Model model) {
        List<LocalStats> stats = localStatsService.getAllStats();

        model.addAttribute("points", stats);

        return "map";
    }

}
