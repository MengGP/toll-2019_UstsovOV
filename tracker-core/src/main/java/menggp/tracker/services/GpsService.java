package menggp.tracker.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *  Сервис эмулирующий работу GPS
 *      - генерирует координаты GPS по расписанию
 *          каждые 5 секунд
 */
@Service
public class GpsService {

    @Scheduled(cron = "*/5 * * * * *")
    private void genreateCoordinate() {
        // rnd = (int)(Math.random()*6) +1;

        // генерация случайных координат, азимута и мгновенной скорости - упрощенных вариант
        int lat, lon, azimuth, instantSpeed;

        lat = (int)(Math.random()*91) +0;
        lon = (int)(Math.random()*91) +0;
        azimuth = (int)(Math.random()*361) +0;
        instantSpeed = (int)(Math.random()*91) +0;

        System.out.println("lat = " + lat + ",lon = " + lon + ", azimuth = " + azimuth + ",instantSpeed = " + instantSpeed);


    }  // end_method genreateCoordinate()

} // end_class
