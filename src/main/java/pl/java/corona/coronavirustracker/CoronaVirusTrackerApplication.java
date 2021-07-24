package pl.java.corona.coronavirustracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.java.corona.coronavirustracker.model.LocalStats;
import pl.java.corona.coronavirustracker.repository.LocalStatsRepository;

@SpringBootApplication
@EnableScheduling
public class CoronaVirusTrackerApplication {


    public static void main(String[] args) {
        SpringApplication.run(CoronaVirusTrackerApplication.class, args);
    }


}
