package pl.java.corona.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.java.corona.coronavirustracker.model.LocalStats;
import pl.java.corona.coronavirustracker.service.LocalStatsService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LocalStatsController {

    private final LocalStatsService localStatsService;

    @Autowired
    public LocalStatsController(LocalStatsService localStatsService) {
        this.localStatsService = localStatsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("locationStats", localStatsService.getAllStats());

        return "home";
    }

    @GetMapping("/sorted")
    public String homeSorted(Model model) {
        List<LocalStats> allStats = localStatsService.getAllStats();

        List<LocalStats> collect = allStats.stream()
                .sorted(Comparator.comparing(LocalStats::getTotalCases).reversed())
                .collect(Collectors.toList());

        model.addAttribute("locationStats", collect);

        int todayTotalCases = allStats.stream()
                .map(LocalStats::getDailyConfirmedCases)
                .reduce(0, Integer::sum);

        model.addAttribute("todayTotalCases", todayTotalCases);

        return "home";
    }

//    @GetMapping("/cases")
//    public List<LocalStats> getAllStats() {
//        return localStatsService.getAllStats();
//    }
//
//    @GetMapping("/cases/top")
//    public List<LocalStats> getAllOrderByTotalCases() {
//        return localStatsService.getAllOrderByTotalCases();
//    }
}
