package menggp.tracker.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import menggp.dto.*;

/**
 *  Сервис эмулирующий работу GPS
 *      - генерирует координаты GPS, азимут и мгновенную скорость - по расписанию
 *          каждые 5 секунд
 */
@Service
public class GpsService {

//    @Value("${gpsDataDelay}")
//    private static String gpsDataDelay;


    @Value("${testString.prop}")
    String testString;


    // аттрибуты
    //------------------------------------------------------------------------
    private int lat;
    private int lon;
    private int azimuth;
    private int instantSpeed;

    // сеттеры и геттеры
    //------------------------------------------------------------------------
    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public int getInstantSpeed() {
        return instantSpeed;
    }

    public void setInstantSpeed(int instantSpeed) {
        this.instantSpeed = instantSpeed;
    }

    // методы
    //------------------------------------------------------------------------

    // @Scheduled(cron = "*/5 * * * * *")
    @Scheduled (fixedRateString = "${gpsDataDelay.prop}" )
    private void genreateCoordinates() {
        // rnd = (int)(Math.random()*6) +1;

        // генерация случайных координат, азимута и мгновенной скорости - упрощенных вариант
        lat = (int)(Math.random()*91) +0;
        lon = (int)(Math.random()*91) +0;
        azimuth = (int)(Math.random()*361) +0;
        instantSpeed = (int)(Math.random()*91) +0;

//        System.out.println("lat = " + lat + ",lon = " + lon + ", azimuth = " + azimuth + ",instantSpeed = " + instantSpeed);

        System.out.println(testString);

    }  // end_method genreateCoordinate()

} // end_class
