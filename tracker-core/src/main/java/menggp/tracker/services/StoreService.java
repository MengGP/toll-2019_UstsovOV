package menggp.tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *  Сервис хранения данных GPS (очередь)
 *      - "ставит" в очередь данные геерируемые GpsService
 *      - класс связан с классом GpsService с помощью аннотации @Autwoired
 */

@Service
public class StoreService {

    @Autowired
    private GpsService gpsService;



} // end_class
