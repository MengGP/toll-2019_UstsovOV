package menggp.tracker.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 *  Описание таблицы GPSDATA для ДБ
 */
@Entity
@Table(name="gpsdata")
public class GpsData {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "ID")
    int id;

    @Column(name = "LAT")
    Double lat;

    @Column(name = "LON")
    Double lon;

    @Column(name = "AZIMUTH")
    Double azimuth;

    @Column(name = "INSTANTSPEED")
    int instantSpeed;

    @Column(name = "TIME")
    long time;

    @Column(name = "AUTOID")
    String autoID;

    public String toString() {
        return "GPS-data{ id="+id+", lat ="+lat+", lon ="+lon+", azimuth ="+azimuth+", instant speed ="+instantSpeed+", time ="+time+", auto ID ="+autoID+" }";
    } // end_method


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public int getInstantSpeed() {
        return instantSpeed;
    }

    public void setInstantSpeed(int instantSpeed) {
        this.instantSpeed = instantSpeed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAutoID() {
        return autoID;
    }

    public void setAutoID(String autoID) {
        this.autoID = autoID;
    }

} // end_class
