package menggp.tracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import menggp.dto.Location;

import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class SendServiceTest {

    private static final Logger Log = LoggerFactory.getLogger(SendServiceTest.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL_SERVER_CORE = "http://localhost:8080/location";


    // интеграционных тест:
    //      - запрос к запущенному приложению server-core из junit-тестов приложения tracker-core при отправке координат, с верификацией возвращаемого JSON
    @Test
    public void sentLocationPOSTRequstIntegrationTest() throws Exception {

        // создаем тестовый объект Location
        Location testLocation = new Location();
        testLocation.setLat(60);
        testLocation.setLon(40);
        testLocation.setAutoId("test_autoId");
        testLocation.setTime(1566029503251L);
        testLocation.setAzimuth(180);
        testLocation.setInstantSpeed(80);

        String testString = testLocation.toJson();

        int resultHttpStatusCode = 0;
        Location resulLocation = new Location();

        try {
             // POST запрос на сервер server-core с координатами из очереди
            HttpEntity<String> requestBody = new HttpEntity<>(testString);
            ResponseEntity<String> result = restTemplate.postForEntity(URL_SERVER_CORE, requestBody, String.class);
            // выводим в лог возвращенный код и данные объекта
            Log.info("Status code: "+result.getStatusCode());
            resultHttpStatusCode = result.getStatusCodeValue();
            Log.info(result.getBody());

            ObjectMapper mapper = new ObjectMapper();
            resulLocation = mapper.readValue(result.getBody(), Location.class);
        }
        catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            Log.info(ex.getMessage());
            //storeService.takeFromQueue();   // извлечение из очереди
        }

        // проверка успешной отправки запроса
        assertEquals("HTTP satatus code check ",200,resultHttpStatusCode);
        // проверка корректнной десириализации полученного ответа из JSON в объект Location
        assertEquals(testLocation.toString(),resulLocation.toString());


    } // end_method

} // end_tests

