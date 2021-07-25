package pl.java.corona.coronavirustracker.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GeoPosition {
    private double latitude;
    private double longitude;

    public GeoPosition() {
    }

    public GeoPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
