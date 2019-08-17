package menggp.tracker.services;

import menggp.dto.Location;
import de.micromata.opengis.kml.v_2_2_0.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


/**
 *  Сервис эмулирующий работу GPS
 *      - парсит KML файл с треком
 *      - каждую секунду кладет в очередь очередную порцию в очерель
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

    private ArrayList<Location> trackPoints = new ArrayList<>();
    private int trackPointIterator = 0;

    @Value("${pathToTrackFile.prop}")
    String pathToTrackFile;

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


    // метод пасит файл с GPS треком формата KML
    @PostConstruct
    private void initParseKml() throws Exception {
        // создаем объект KML
        final Kml kml = Kml.unmarshal(new File( pathToTrackFile) );
        final Document document = (Document) kml.getFeature();

        List<Feature> placemarks = document.getFeature();

        // цикл по массиву треков в KML-файле
        for (Object sectionOfTrack : placemarks ) {

            // снимаем слой с итератора - приводим к типу Placemark и получаем полу geometry
            Geometry geometry = ((Placemark)sectionOfTrack).getGeometry();

            // получаем из поля geomrtry массив координат - т.е. текущий трек из файла
            List<Coordinate> coordinates = ((LineString)geometry).getCoordinates();

            // цикл по точкам трека
            for (Coordinate pointOfTrack : coordinates ) {

                // для начальной точки трека присваиваем текущее время, скорость = 0 и азимут = 0
                // для последующих - вчисляем азимут и случайно изменяем скорость в пределах 1 км/ч
                // время рассчитываем исходя из скорости и пройденного пути

                Location currentGpsData = new Location();

                if ( trackPoints.size() == 0 ) {
                    currentGpsData.setLat( pointOfTrack.getLatitude() );
                    currentGpsData.setLon( pointOfTrack.getLongitude() );
                    currentGpsData.setAzimuth( 0 );
                    currentGpsData.setInstantSpeed( 1 );
                    currentGpsData.setTime( System.currentTimeMillis() );
                    currentGpsData.setAutoId("o567gfd");
                }
                else {
                    double previouseLat = trackPoints.get(trackPoints.size()-1).getLat();
                    double previouseLon = trackPoints.get(trackPoints.size()-1).getLon();
                    int previouseInstantSpeed = trackPoints.get(trackPoints.size()-1).getInstantSpeed();
                    long previouseTime = trackPoints.get(trackPoints.size()-1).getTime();
                    double currentLat = pointOfTrack.getLatitude();
                    double currentLon = pointOfTrack.getLongitude();

                    currentGpsData.setLat( currentLat );
                    currentGpsData.setLon( currentLon );
                    currentGpsData.setAzimuth( GpsService.calcAzimuth( previouseLat, previouseLon, currentLat, currentLon ) );
                    currentGpsData.setInstantSpeed( GpsService.calcInstantSpeed(previouseInstantSpeed) );
                    currentGpsData.setTime( GpsService.calcTime(previouseTime,previouseInstantSpeed, previouseLat,previouseLon,currentLat,currentLon) );
                    currentGpsData.setAutoId("o567gfd");

                } // end_if

                trackPoints.add(currentGpsData);

            } // end_for

        } // end_for


    } // end_method



    // метод складывает GPS-данные из трека в очередь, предоставляемую сервисом StoreService
    @Scheduled(fixedRateString = "${gpsDataDelay.prop}", initialDelayString = "${storeInitialDelay.prop}")
    private void putCurrentGpsData() throws Exception {
        /*Location currentGpsData = new Location();
        currentGpsData.setLat( (int)(Math.random()*91) +0 );
        currentGpsData.setLon( (int)(Math.random()*91) +0 );
        currentGpsData.setAzimuth( (int)(Math.random()*361) +0 );
        currentGpsData.setInstantSpeed( (int)(Math.random()*91) +0 );
        currentGpsData.setTime( System.currentTimeMillis() );
        currentGpsData.setAutoId("o567gfd");
        */

        // storeService.putToQueue( currentGpsData.toJson() );

        if ( trackPointIterator < trackPoints.size() ) {
           storeService.putToQueue( trackPoints.get(trackPointIterator).toJson() );
           trackPointIterator++;
        }  // end_if


    } // end_method

    // статический метод - вычисляет азимут по 2м точкам координат на плоскости
    static private double calcAzimuth(double lat1, double lon1, double lat2, double lon2) {

        // lon - долгота идет с запада на восток - ось Х
        // lat - широта идет с севера на юг - ось У

        double azimuth = 0;

        // внезапно точки не отличаются
        if ( lat1 == lat2 && lon1 == lon2 ) azimuth = 0;

        // мы мидем на СЕВЕР - точки на одной долготе, широта больше
        else if ( lat1 < lat2 && lon1 == lon2 ) azimuth = 0;

        // мы мидем на ЮГ - точки на одной долготе, широта меньше
        else if ( lat1 > lat2 && lon1 == lon2 ) azimuth = 180;

        // мы мидем на ВОСТОК - точки на одной широте, долгота больше
        else if ( lat1 == lat2 && lon1 < lon2 ) azimuth = 90;

        // мы мидем на ЗАПАД - точки на одной широте, долгота меньше
        else if ( lat1 == lat2 && lon1 > lon2 ) azimuth = 270;

        // точка B выше и правее точки А - dLon < 0, dLat < 0
        else if ( (lon1-lon2)<0 && (lat1-lat2)<0 ) {
            double dLon = lon2-lon1;
            double hypotenuse = Math.sqrt( Math.pow( (lon1-lon2), 2) +  Math.pow( (lat1-lat2), 2));
            azimuth = 90 - Math.toDegrees( Math.acos( dLon / hypotenuse ) );
        }

        // точка В ниже и правее точки А - dLon < 0, dLat > 0
        else if ( (lon1-lon2)<0 && (lat1-lat2)>0 ) {
            double dLon = lon2-lon1;
            double hypotenuse = Math.sqrt( Math.pow( (lon1-lon2), 2) +  Math.pow( (lat1-lat2), 2));
            azimuth = 90 + Math.toDegrees( Math.acos( dLon / hypotenuse ) );
        }

        // точка В ниже и левее точки А - dLon > 0, dLat > 0
        else if ( (lon1-lon2)>0 && (lat1-lat2)>0 ) {
            double dLon = lon1-lon2;
            double hypotenuse = Math.sqrt( Math.pow( (lon1-lon2), 2) +  Math.pow( (lat1-lat2), 2));
            azimuth = 270 - Math.toDegrees( Math.acos( dLon / hypotenuse ) );
        }

        // точка В выше и левее точки А - dLon > 0, dLat < 0
        else if ( (lon1-lon2)>0 && (lat1-lat2)<0 ) {
            double dLon = lon1-lon2;
            double hypotenuse = Math.sqrt( Math.pow( (lon1-lon2), 2) +  Math.pow( (lat1-lat2), 2));
            azimuth = 270 + Math.toDegrees( Math.acos( dLon / hypotenuse ) );
        }

        return azimuth;
    } // end_method

    // статический метод расчета мгновенной скорости
    static private int calcInstantSpeed(int speed) {
        // шаг изменения скорости +- 2
        // "рабочий" диапазон от 50 до 90

        boolean incorrectChangeSpeed = true;

        while ( incorrectChangeSpeed ) {

            if (speed < 50) {
                // слуайное изменение в диапазоне от -1 до 2
                speed = speed + (int)(Math.random()*3) -1;
            }
            else if ( speed > 90 ) {
                // слуайное изменение в диапазоне от -2 до 1
                speed = speed + (int)(Math.random()*3) -3;
            }
            else {
                // слуайное изменение в диапазоне от -2 до 2
                speed = speed + (int)(Math.random()*4) -1;
            } // end_if

            if ( speed > 0) incorrectChangeSpeed = false;
        } // end_while

        return speed;
    } // end_method

    // статический метод расчета времени на преодоление расстояния между ткущей и предыдущей точками, с учетом скорости
    static  private long calcTime(long time, int speed, double lat1, double lon1, double lat2, double lon2) {

        // если путь не пройден
        if ( lat1 == lat2 && lon1 == lon2 ) return time;

        // рассчитываем пройденное расстояние
        double earthRadius = 6378.1370D;
        double degreesToRadians = (Math.PI / 180D);

        double dLon = (lon2 - lon1)*degreesToRadians;
        double dLat = (lat2 - lat1)*degreesToRadians;
        double a = Math.pow( Math.sin(dLat / 2D), 2D )
                + Math.cos(lat1 * degreesToRadians)
                * Math.cos(lat2 * degreesToRadians)
                * Math.pow(Math.sin(dLon / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double distance = earthRadius * c;

        // рассчитываем прирощение времени в милисекундах
        long dTime = (long)( 3600*1000*distance/speed );

        return  time + dTime;
    } // end_method


} // end_class
