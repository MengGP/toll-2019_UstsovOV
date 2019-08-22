package menggp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@Controller
public class PointsNumberController {

    private static final Logger Log = LoggerFactory.getLogger(PointsNumberController.class);
    private static final String URL_SERVER_CORE_POINT_REQUEST = "http://localhost:8080/pointsRequest";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/requestPoints")
    public String pointsNumber(@RequestParam(value="pointsNum",required = false, defaultValue = "0" ) String pointsNum, Model model) {

        int num;
        num = Integer.parseInt(pointsNum);
        String result;

        // проверка на пустое поле - если 0 - это значит значение по умолчанию, т.е. не было введено ничего
        if ( num == 0 ) result = "ничего, введите то что ";
        else {

            result = ""+num;

            // POST-запрос на сервер server-ui
            try {
                HttpEntity<String> requestBody = new HttpEntity<>( pointsNum );
                ResponseEntity<String> responseResult = restTemplate.postForEntity(URL_SERVER_CORE_POINT_REQUEST, requestBody, String.class);
                // выводим в лог возвращенный код и дннные
                Log.info("Status code: "+responseResult.getStatusCode());
                Log.info( responseResult.getBody() ) ;
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info(ex.getMessage());
            } // end_try_catch

        } // end_if_else





        model.addAttribute("result",result);

        return "pointsNumberPage";
    } // end_method

} // end_class
