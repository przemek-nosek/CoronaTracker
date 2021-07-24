package pl.java.corona.coronavirustracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.java.corona.coronavirustracker.model.LocalStats;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LocalStatsRepository extends JpaRepository<LocalStats, Long> {

    @Modifying
    @Transactional
    @Query("update LocalStats l set l.dailyConfirmedCases=:newCases, l.totalCases=:newTotalCases where l.id=:id")
    void updateById(@Param("newCases") int newCases, @Param("newTotalCases") int newTotalCases, @Param("id") Long id);

    List<LocalStats> OrderByTotalCasesDesc();
}
