package pl.java.corona.coronavirustracker.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.java.corona.coronavirustracker.model.GeoPosition;
import pl.java.corona.coronavirustracker.model.LocalStats;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {

    public static List<LocalStats> parseCsvFile(HttpResponse<String> httpResponse) throws IOException {
        StringReader body = new StringReader(httpResponse.body());
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(body);
        List<LocalStats> localStats = new ArrayList<>();

        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            int totalCases = Integer.parseInt(record.get(record.size() - 1));
            int dailyCases = totalCases - Integer.parseInt(record.get(record.size() - 2));

            String lat = record.get("Lat");
            String longitude = record.get("Long");
            if (!lat.isBlank() && !longitude.isBlank()) {
                GeoPosition geoPosition = new GeoPosition();
                geoPosition.setLatitude(Double.parseDouble(lat));
                geoPosition.setLongitude(Double.parseDouble(longitude));
                localStats.add(new LocalStats(state, country, dailyCases, totalCases, geoPosition));
            }
        }
        return localStats;
    }
}
