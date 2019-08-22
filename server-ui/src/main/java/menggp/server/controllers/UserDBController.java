package menggp.server.controllers;

import menggp.dto.UserDBEntry;
import menggp.dto.UserDBList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class UserDBController {

    private static final Logger Log = LoggerFactory.getLogger(UserDBController.class);
    private static final String URL_SERVER_CORE_USERDB_REQUEST = "http://localhost:8080/userDBRequest";

    @Autowired
    RestTemplate restTemplate;



    @RequestMapping("/requestUserDB")
    public  String requestUserDB(Model model) {

        List<UserDBEntry> userDB = new UserDBList().getUserDBList();

        // запрос к БД - получить лист с пользователями

        try {
            HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
            ResponseEntity<UserDBList> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
            Log.info("Status code: "+responseResult.getStatusCode());
            Log.info( responseResult.getBody().toString() ) ;
            Log.info( responseResult.getBody().getUserDBList().get(0).getName() );
            Log.info( responseResult.getBody().getUserDBList().get(1).getName() );

            userDB = responseResult.getBody().getUserDBList();

        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        }

        model.addAttribute("userDB",userDB);

        int[] intArray = {1,2,3,4,5};
        model.addAttribute("intArray",intArray);


        return "userDBPage";
    } // end_method


} // end_class
