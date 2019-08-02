package menggp.tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  Сервис хранения данных GPS (очередь)
 *      - "ставит" в очередь данные геерируемые GpsService
 *      - класс связан с классом GpsService с помощью аннотации @Autwoired
 */

@Service
public class StoreService {

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private GpsService gpsService;

    // аттрибуты
    //------------------------------------------------------------------------

    private static final Logger Log = LoggerFactory.getLogger(StoreService.class);

    private BlockingDeque<String> queue = new LinkedBlockingDeque<>(1000);

    private int putCount;


    @Scheduled(fixedRateString = "${gpsDataDelay.prop}", initialDelayString = "${storeInitialDelay.prop}")
    private void putToQueue() throws InterruptedException {
        // 1 - положить в очередь
        // 2 - вывесьт в лог

        gpsService.genreateCoordinates();

        int i = putCount++;

        Log.info("put count = " +i);
        Log.info("\t ---> " + gpsService.getLat());
        Log.info("\t ---> " + gpsService.getLon());
        Log.info("\t ---> " + gpsService.getInstantSpeed());
        Log.info("\t ---> " + gpsService.getAzimuth());
        /*
        System.out.println("put count = " +i);
        System.out.println("\t ---> " + gpsService.getLat());
        System.out.println("\t ---> " + gpsService.getLon());
        System.out.println("\t ---> " + gpsService.getAzimuth());
        System.out.println("\t ---> " + gpsService.getInstantSpeed());
        */




    } // end_method putToQueue



} // end_class
