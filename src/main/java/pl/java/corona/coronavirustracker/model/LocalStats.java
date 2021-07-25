package pl.java.corona.coronavirustracker.model;


import javax.persistence.*;

@Entity
public class LocalStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;
    private String country;

    @Column(name = "daily_cases")
    private int dailyConfirmedCases;
    private int totalCases;

    @Embedded
    private GeoPosition geoPosition;

    public LocalStats() {
    }


    public LocalStats(String state, String country, int dailyConfirmedCases, int totalCases, GeoPosition geoPosition) {
        this.state = state;
        this.country = country;
        this.dailyConfirmedCases = dailyConfirmedCases;
        this.totalCases = totalCases;
        this.geoPosition = geoPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDailyConfirmedCases() {
        return dailyConfirmedCases;
    }

    public void setDailyConfirmedCases(int dailyConfirmedCases) {
        this.dailyConfirmedCases = dailyConfirmedCases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }
}
