package pl.java.corona.coronavirustracker.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

    private static final String CSV_CORONA_VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocalStats> localStatsDetails = new ArrayList<>();

    private LocalStatsRepository repository;

    @Autowired
    public LocalStatsService(LocalStatsRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "5 * * * * *")
    @PostConstruct
    public void getCoronaVirusData() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CSV_CORONA_VIRUS_DATA))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        this.localStatsDetails = readCsvFile(httpResponse);
        repository.saveAll(localStatsDetails);
    }

    private List<LocalStats> readCsvFile(HttpResponse<String> httpResponse) throws IOException {
        StringReader body = new StringReader(httpResponse.body());
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(body);
        List<LocalStats> localStats = new ArrayList<>();

        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            int cases = Integer.parseInt(record.get(record.size()-1));
            localStats.add(new LocalStats(state, country, cases));
        }
        return localStats;
    }
}
