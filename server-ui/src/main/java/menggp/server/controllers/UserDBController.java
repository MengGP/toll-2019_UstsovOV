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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class UserDBController {

    private static final Logger Log = LoggerFactory.getLogger(UserDBController.class);
    private static final String URL_SERVER_CORE_USERDB_REQUEST = "http://localhost:8080/userDBRequest";
    private static final String URL_SERVER_CORE_CREATE_USER_REQUEST = "http://localhost:8080/createUserRequest";

    @Autowired
    RestTemplate restTemplate;


    /**
     *  Метод описывает запрос на полученеи таблицы пользователей из удаленной БД
     *
     * @param model
     * @return
     */
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

//        int[] intArray = {1,2,3,4,5};
//        model.addAttribute("intArray",intArray);


        return "userDBPage";
    } // end_method

    /**
     *   Метод описывает запрос на создание пользоватлея в удаленной БД
     *
     * @param name
     * @param password
     * @param role
     * @param model
     * @return
     */
    @RequestMapping("/createUser")
    public String requestCreateUser(
            @RequestParam(value="name",required = false, defaultValue = "0") String name,
            @RequestParam(value="password", required = false) String password,
            @RequestParam(value="role", required = false,defaultValue = "0") String role,
            Model model) {
        int result=1;
        String resultUser = "";
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        if ( name.equals("0") || role.equals("0") ) result = 0;
        else if ( !(role.equals("ROLE_CLIENT") || role.equals("ROLE_MANAGER") || role.equals("ROLE_ROOT")) ) result = 3;
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();

            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                Log.info("Status code: "+responseResult.getStatusCode());
                Log.info( responseResult.getBody().toString() ) ;

                userDB = responseResult.getBody().getUserDBList();

            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                result = 4;
            }

            for (UserDBEntry iterator : userDB) {
                if ( name.equals(iterator.getName()) ) result = 2;
            }
        } // end_else

        if ( result == 1 ) {
            result = 4;
            // запрос на создание пользователя
            UserDBEntry newUser = new UserDBEntry();
            newUser.setId(1); // не принципиально, т.к. при записи в базу ID назначается автоматически
            newUser.setName(name);
            newUser.setPassword(password);
            newUser.setRole(role);
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(newUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_CREATE_USER_REQUEST, requestBody, String.class );
                Log.info("Status code: "+responseResult.getStatusCode());
                Log.info( responseResult.getBody() ) ;
                result = 1;
                if ( responseResult.getBody().equals("OK")) {
                    result = 1;
                    resultUser = " USER: "+name+" "+password+" "+role;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch


        }

        model.addAttribute("resultUser",resultUser);
        model.addAttribute("result",result);
        return "createUserPage";
    } // end_class


} // end_class
