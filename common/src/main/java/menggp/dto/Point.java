package menggp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 *  Copy from toll-2017 on 13.06.2019
 */
public class Point {

    // аттрибуты
    //--------------------------------------------------------------------------------------
    private double lat;
    private double lon;
    private String autoId;
    private long time;
    private double azimuth;
    private int instantSpeed;

    // сеттеры и геттеры
    //--------------------------------------------------------------------------------------
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public int getInstantSpeed() {
        return instantSpeed;
    }

    public void setInstantSpeed(int instantSpeed) {
        this.instantSpeed = instantSpeed;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) throws IOException {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }


    // методы
    //--------------------------------------------------------------------------------------

    // метод преобразования экземпляра объекта в формат JSON
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    } // end_method

    // преобразование в строку - замена стандартного метода
    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", autoId='" + autoId + '\'' +
                ", time="+ time +
                '}';
    } // end_method


} // end_class
