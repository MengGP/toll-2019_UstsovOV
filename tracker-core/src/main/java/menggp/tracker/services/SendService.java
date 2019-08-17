package menggp.tracker.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 *  Класса - реализует отправку сообщений в лог
 *      - в нашем случеа отправлет, извлекаемую из очереди, строку формата JSON в лог
 */
@Service
public class SendService {

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private StoreService storeService;

    @Autowired
    RestTemplate restTemplate;

    // аттрибуты
    //------------------------------------------------------------------------
    private static final Logger Log = LoggerFactory.getLogger(SendService.class);

    // методы
    //------------------------------------------------------------------------

    // метод будет заменен на метод отправляющий координаты на сервере в виде REST запроса
    @Scheduled (fixedDelayString = "${takeQueueDelay.prop}")
    public void sendLocations() throws InterruptedException {
        while ( storeService.sizeOfQueue() > 0 ) Log.info( storeService.takeFromQueue() );
    } // end_method

    @Scheduled (fixedDelayString = "${takeQueueDelay.prop}", initialDelayString = "${initialDelayPOST.prop}")
    public void sentLocationPOSTRequst() throws  InterruptedException {
        while ( storeService.sizeOfQueue() > 0 ) {
            storeService.takeFromQueue();
            Log.info( "Здесь должен быть ваш POST-request" );
        } // end_while
    } // end_method

} //end_class
