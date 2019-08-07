package menggp.tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private StoreService storeService;

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

    // метод складывает GPS-данные из трека в очередь, предоставляемую сервисом StoreService
    @Scheduled(fixedRateString = "${gpsDataDelay.prop}", initialDelayString = "${storeInitialDelay.prop}")
    private void putCurrentGpsData() throws  Exception {
        Location currentGpsData = new Location();
        currentGpsData.setLat( (int)(Math.random()*91) +0 );
        currentGpsData.setLon( (int)(Math.random()*91) +0 );
        currentGpsData.setAzimuth( (int)(Math.random()*361) +0 );
        currentGpsData.setInstantSpeed( (int)(Math.random()*91) +0 );
        currentGpsData.setTime( System.currentTimeMillis() );
        currentGpsData.setAutoId("o567gfd");

        storeService.putToQueue( currentGpsData.toJson() );

    } // end_method

} // end_class
