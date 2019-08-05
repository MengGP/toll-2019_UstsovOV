package menggp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

// класс-оболочка для класса Point
public class Location {

    // делегируемый класс
    //--------------------------------------------------------------------------------------
    private Point point = new Point();


    // делегирование методов класса Point
    //--------------------------------------------------------------------------------------
    public void setLat(double lat) {
        point.setLat(lat);
    }

    public double getLat() {
        return point.getLat();
    }

    public double getLon() {
        return point.getLon();
    }

    public void setLon(double lon) {
        point.setLon(lon);
    }

    public String getAutoId() {
        return point.getAutoId();
    }

    public void setAutoId(String autoId) {
        point.setAutoId(autoId);
    }

    public String toJson() throws JsonProcessingException {
        return point.toJson();
    }

    public void setTime(long time) {
        point.setTime(time);
    }

    public long getTime() {
        return point.getTime();
    }

    public double getAzimuth() {
        return point.getAzimuth();
    }

    public void setAzimuth(double azimuth) {
        point.setAzimuth(azimuth);
    }

    public int getInstantSpeed() {
        return point.getInstantSpeed();
    }

    public void setInstantSpeed(int instantSpeed) {
        point.setInstantSpeed(instantSpeed);
    }

    // методы
    //--------------------------------------------------------------------------------------
//    public static void meth() {}



} // end_class
