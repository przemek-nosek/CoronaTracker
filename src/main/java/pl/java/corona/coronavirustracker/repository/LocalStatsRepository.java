package pl.java.corona.coronavirustracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.java.corona.coronavirustracker.model.LocalStats;

@Repository
public interface LocalStatsRepository extends JpaRepository<LocalStats, Long> {
}
