package menggp.tracker.services;

import menggp.dto.Location;
import menggp.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



/**
 *  Сервис хранения данных GPS (очередь)
 *      - кладет в очередь данные геерируемые GpsService
 *      - отправляет данные из очереди с помощью SendService
 *      - класс связан с классами GpsService и SendService с помощью аннотации @Autwoired
 */

@Service
public class StoreService {

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private GpsService gpsService;
    //@Autowired
    //private SendService sendService;

    // аттрибуты
    //------------------------------------------------------------------------
//    private static final Logger Log = LoggerFactory.getLogger(StoreService.class);
    private BlockingDeque<String> queue = new LinkedBlockingDeque<>(1000);
    private int putCount;


    // методы
    //------------------------------------------------------------------------

    // складывем по расписанию сгенерированные координаты в очередь
    @Scheduled(fixedRateString = "${gpsDataDelay.prop}", initialDelayString = "${storeInitialDelay.prop}")
    private void putToQueue() throws InterruptedException, IOException {

        // генерируем координаты
        gpsService.genreateCoordinates();

        // создаем экземплар класса Location (класс-оболочка для класса Point) и заполняем его поля
        Location locationToQueue = new Location();
        locationToQueue.setLat( gpsService.getLat() );
        locationToQueue.setLon( gpsService.getLon() );
        locationToQueue.setAzimuth( gpsService.getAzimuth() );
        locationToQueue.setInstantSpeed( gpsService.getInstantSpeed() );
        locationToQueue.setTime( System.currentTimeMillis() );
        locationToQueue.setAutoId( "o567gfd" );

        // складываем полученный объект в очередь в формате JSON
        queue.put( locationToQueue.toJson() );

    } // end_method putToQueue

    /*
    // извлекаем из очереди положенные ранее туда объекты - используем извлечение с блокировкой
    @Scheduled (fixedDelayString = "${takeQueueDelay.prop}", initialDelayString = "${storeInitialDelay.prop}")
    private void takeFromQueue() throws InterruptedException {
        // отправка очередной строки данных из очереди с помошью метода класса SendService
        sendService.sendLocation( queue.take() );
    } // end_method
    */

    // метод возврящает размер очереди
    public int sizeOfQueue() throws InterruptedException {
        return queue.size();
    } // end_method

    // метод реализующий извлечение объектов из очереди - извлечение с блокировкой
    public String takeFromQueue() throws InterruptedException {
        return queue.take();
    } // end_method


} // end_class
