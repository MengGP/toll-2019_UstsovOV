package menggp.tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ConnectException;

/**
 *  Класса - реализует отправку сообщений в лог
 *      - в нашем случеа отправлет, извлекаемую из очереди, строку формата JSON в лог
 */
@Service
public class SendService {

    // Константы
    //------------------------------------------------------------------------
    private static final String URL_SERVER_CORE = "http://localhost:8080/location";
    private static final Logger Log = LoggerFactory.getLogger(SendService.class);

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private StoreService storeService;

    @Autowired
    private RestTemplate restTemplate;

    // аттрибуты
    //------------------------------------------------------------------------


    // методы
    //------------------------------------------------------------------------
    /*
    *    метод заменен на метод отправляющий координаты на сервере в виде REST запроса
    @Scheduled (fixedDelayString = "${takeQueueDelay.prop}")
    public void sendLocations() throws InterruptedException {
        while ( storeService.sizeOfQueue() > 0 ) Log.info( storeService.takeFromQueue() );
    } // end_method
    */

    @Scheduled (fixedDelayString = "${takeQueueDelay.prop}", initialDelayString = "${initialDelayPOST.prop}")
    public void sentLocationPOSTRequst() throws InterruptedException {
        // продолжаем отправку запросов, пока в очереди есть координаты
        while ( storeService.sizeOfQueue() > 0 ) {

            try {
                // POST запрос на сервер server-core с координатами из очереди

                HttpEntity<String> requestBody = new HttpEntity<>(storeService.takeFromQueue());
                ResponseEntity<String> result = restTemplate.postForEntity(URL_SERVER_CORE, requestBody, String.class);
                // выводим в лог возвращенный код и данные объекта
                Log.info("Status code: "+result.getStatusCode());
                Log.info(result.getBody());
            }
            catch (ResourceAccessException|HttpClientErrorException| HttpServerErrorException ex) {
                Log.info(ex.getMessage());
                //storeService.takeFromQueue();   // извлечение из очереди
            }

        } // end_while
    } // end_method

} //end_class
