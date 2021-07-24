package pl.java.corona.coronavirustracker.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.java.corona.coronavirustracker.model.LocalStats;
import pl.java.corona.coronavirustracker.repository.LocalStatsRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalStatsService {

    private final Logger logger = LoggerFactory.getLogger(LocalStatsService.class);

    private static final String CSV_CORONA_VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private final LocalStatsRepository repository;

    @Autowired
    public LocalStatsService(LocalStatsRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "* 1 1 * * *")
    @PostConstruct
    private void getCoronaVirusData() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CSV_CORONA_VIRUS_DATA))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<LocalStats> localStatsDetails = readCsvFile(httpResponse);

        updateDb(localStatsDetails);
        localStatsDetails.clear();
    }


    private void updateDb(List<LocalStats> stats) {
        if (repository.count() == 0) {
            repository.saveAll(stats);
        } else {
            for (int i = 0; i < stats.size(); i++) {
                LocalStats localStatsDetail = stats.get(i);
                int newCases = localStatsDetail.getDailyConfirmedCases();
                int newTotalCases = localStatsDetail.getTotalCases();
                repository.updateStats(newCases, newTotalCases, (long) i + 1);
            }
        }
    }

    private List<LocalStats> readCsvFile(HttpResponse<String> httpResponse) throws IOException {
        StringReader body = new StringReader(httpResponse.body());
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(body);
        List<LocalStats> localStats = new ArrayList<>();

        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            int totalCases = Integer.parseInt(record.get(record.size() - 1));
            int dailyCases = totalCases - Integer.parseInt(record.get(record.size() - 2));
            localStats.add(new LocalStats(state, country, dailyCases, totalCases));
        }
        return localStats;
    }

    public List<LocalStats> getAllStats() {
        return repository.findAll();
    }

    public List<LocalStats> getAllOrderByTotalCases() {
        return repository.OrderByTotalCasesDesc();
    }

}
