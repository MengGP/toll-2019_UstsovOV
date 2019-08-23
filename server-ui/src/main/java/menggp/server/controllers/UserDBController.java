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
    private static final String URL_SERVER_CORE_DELETE_USER_REQUEST = "http://localhost:8080/deleteUserRequest";
    private static final String URL_SERVER_CORE_UPDATE_USER_REQUEST = "http://localhost:8080/updateUserRequest";

    @Autowired
    RestTemplate restTemplate;


    /**
     *  Метод описывает запрос на полученеи таблицы пользователей из удаленной БД
     *
     */
    @RequestMapping("/userDBPage")
    public String requestUserDB(Model model) {

        List<UserDBEntry> userDB = new UserDBList().getUserDBList();

        // запрос к БД - получить лист с пользователями
        try {
            HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
            ResponseEntity<UserDBList> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
            Log.info("Status code: "+responseResult.getStatusCode());
//            Log.info( responseResult.getBody().toString() ) ;
//            Log.info( responseResult.getBody().getUserDBList().get(0).getName() );
//            Log.info( responseResult.getBody().getUserDBList().get(1).getName() );

            userDB = responseResult.getBody().getUserDBList();
        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        model.addAttribute("userDB",userDB);
        return "userDBPage";
    } // end_method

    /**
     *   Метод описывает запрос на создание пользоватлея в удаленной БД
     *
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
                else if (name.equals("client") || name.equals("manager") || name.equals("root")) result = 2;
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
//                result = 1;
                if ( responseResult.getBody().equals("OK")) {
                    result = 1;
                    resultUser = " login:"+name+", password:"+password+", role:"+role;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("resultUser",resultUser);
        model.addAttribute("result",result);
        return "createUserPage";
    } // end_class

    @RequestMapping("/deleteUserRequest")
    public String requestDeleteUser(@RequestParam(value = "selectUserID") String selectUserID, Model model) {
        String str = " --- User ID = "+selectUserID;
        Log.info(str);

        // запрос на удаление пользователя
        try {
            HttpEntity<String> requestBody = new HttpEntity<>(selectUserID);
            ResponseEntity<String> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_DELETE_USER_REQUEST, requestBody, String.class );
            Log.info("Status code: "+responseResult.getStatusCode());
            Log.info( responseResult.getBody() ) ;
        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        requestUserDB(model);
        return "userDBPage";
    } // end_method

    @RequestMapping("/updateUserPage")
    public String updateUserPage(
            @RequestParam(value="selectUserID",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="selectUserName",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="selectUserPassword", required = false) String currentPassword,
            @RequestParam(value="selectUserRole", required = false,defaultValue = "0") String currentRole,
            Model model)
    {
        model.addAttribute("currentID",currentID);
        model.addAttribute("currentName",currentName);
        model.addAttribute("currentPassword",currentPassword);
        model.addAttribute("currentRole",currentRole);
        return "updateUserPage";
    } // end_method

    /**
     *   Метод реализующий обновление информации о пользователе в удаленной БД
     *
    */
    @RequestMapping("/updateUserRequest")
    public String updateUserRequest(
            @RequestParam(value="currentIDUpd",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="currentNameUpd",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="currentPasswordUpd", required = false) String currentPassword,
            @RequestParam(value="currentRoleUpd", required = false,defaultValue = "0") String currentRole,

            @RequestParam(value="newID",required = false, defaultValue = "0") String newID,
            @RequestParam(value="newName",required = false, defaultValue = "0") String newName,
            @RequestParam(value="newPassword", required = false) String newPassword,
            @RequestParam(value="newRole", required = false,defaultValue = "0") String newRole,
            Model model)
    {
        int resultCode = 1;
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        // проверка на заполнение полей
        if ( newName.equals("0") || newRole.equals("0") ) resultCode = 0;
        // проверка на корректность роли
        else if ( !(newRole.equals("ROLE_CLIENT") || newRole.equals("ROLE_MANAGER") || newRole.equals("ROLE_ROOT")) ) resultCode = 3;
        // проверяем уникальность имени и подключение к БД
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();
            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                Log.info("Status code: "+responseResult.getStatusCode());
                userDB = responseResult.getBody().getUserDBList();
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                resultCode = 4;
            }
            for (UserDBEntry iterator : userDB) {
                if ( iterator.getName().equals(newName) && (iterator.getId() != Integer.parseInt(currentID)) )
                    resultCode = 2;
                //if ( newName.equals(iterator.getName()) && !currentID.equals(iterator.getId()) ) resultCode = 2;
                else if (newName.equals("client") || newName.equals("manager") || newName.equals("root")) resultCode = 2;
            }
        } // end_else

        if ( resultCode == 1 ) {
            resultCode = 4;
            // запрос на изменение пользователя
            UserDBEntry updUser = new UserDBEntry();
            updUser.setId( Integer.parseInt(currentID) );
            updUser.setName( newName );
            updUser.setPassword( newPassword );
            updUser.setRole( newRole );
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(updUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_UPDATE_USER_REQUEST, requestBody, String.class );
//                resultCode = 1;
                if ( responseResult.getBody().equals("OK")) {
                    resultCode = 1;
                    currentName = newName;
                    currentPassword = newPassword;
                    currentRole = newRole;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("currentID", currentID);
        model.addAttribute("currentName", currentName);
        model.addAttribute("currentPassword", currentPassword);
        model.addAttribute("currentRole", currentRole);

        model.addAttribute("resultCode", resultCode);
        return "updateUserPage";
    } // end_method

    /**
     *  Метод описывает запрос на полученеи таблицы пользователей c ролью ROLE_CLIENT из удаленной БД
     *
    */
    @RequestMapping("/registerClient")
    public String registerClient(Model model) {

        List<UserDBEntry> userDB = new UserDBList().getUserDBList();

        // запрос к БД - получить лист с пользователями
        try {
            HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
            ResponseEntity<UserDBList> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
            Log.info("Status code: "+responseResult.getStatusCode());

            userDB = responseResult.getBody().getUserDBList();

            // оставляем лдя вывода только записи с ролью CLIENT_ROLE
            for (int i=0; i<userDB.size(); i++) {
                if ( !userDB.get(i).getRole().equals("ROLE_CLIENT") ) {
                    userDB.remove(i);
                    i--;
                } // end_if
            } // end_for

        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        model.addAttribute("userDB",userDB);
        return "registerClient";
    } // end_method


    /**
     *   Метод описывает запрос на создание пользоватлея c ролью ROLE_CLIENT в удаленной БД
     *
     */
    @RequestMapping("/createClient")
    public String requestCreateClient(
            @RequestParam(value="name",required = false, defaultValue = "0") String name,
            @RequestParam(value="password", required = false, defaultValue = "0") String password,
            Model model) {
        int resultCode=1;
        String resultUser = "";
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        if ( name.equals("0") || password.equals("0") ) resultCode = 0;
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();

            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                userDB = responseResult.getBody().getUserDBList();

                // проверка уникальности имени
                for (UserDBEntry iterator : userDB) {
                    if ( name.equals(iterator.getName()) ) resultCode = 2;
                    else if (name.equals("client") || name.equals("manager") || name.equals("root")) resultCode = 2;
                } // end_for
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                resultCode = 4;
            }
        } // end_else

        if ( resultCode == 1 ) {
            resultCode = 4;
            // запрос на создание пользователя
            UserDBEntry newUser = new UserDBEntry();
            newUser.setId(1); // не принципиально, т.к. при записи в базу ID назначается автоматически
            newUser.setName(name);
            newUser.setPassword(password);
            newUser.setRole("ROLE_CLIENT");
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(newUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_CREATE_USER_REQUEST, requestBody, String.class );
                if ( responseResult.getBody().equals("OK")) {
                    resultCode = 1;
                    resultUser = " login:"+name;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("resultUser",resultUser);
        model.addAttribute("result",resultCode);
        return "createClientPage";
    } // end_method

    /**
     *  Метод реализует удаление пользователя с ролью ROLE_CLIENT из удаленной базы данных
     *
     */
    @RequestMapping("/deleteClientRequest")
    public String requestDeleteClient(@RequestParam(value = "selectUserID") String selectUserID, Model model) {
        // запрос на удаление пользователя
        try {
            HttpEntity<String> requestBody = new HttpEntity<>(selectUserID);
            ResponseEntity<String> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_DELETE_USER_REQUEST, requestBody, String.class );
        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        registerClient(model);
        return "registerClient";
    } // end_method

    @RequestMapping("/updateClientPage")
    public String updateClientPage(
            @RequestParam(value="selectUserID",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="selectUserName",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="selectUserPassword", required = false) String currentPassword,
            Model model)
    {
        model.addAttribute("currentID",currentID);
        model.addAttribute("currentName",currentName);
        model.addAttribute("currentPassword",currentPassword);
        return "updateClientPage";
    } // end_method

    /**
     *  Метод реализующий обновление информации о пользователе c ролью ROLE_CLIENT в удаленной БД
     *
   */
    @RequestMapping("/updateClientRequest")
    public String updateClientRequest(
            @RequestParam(value="currentIDUpd",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="currentNameUpd",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="currentPasswordUpd", required = false) String currentPassword,

            @RequestParam(value="newID",required = false, defaultValue = "0") String newID,
            @RequestParam(value="newName",required = false, defaultValue = "0") String newName,
            @RequestParam(value="newPassword", required = false, defaultValue = "0") String newPassword,
            Model model)
    {
        int resultCode = 1;
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        // проверка на заполнение полей
        if ( newName.equals("0") || newPassword.equals("0") ) resultCode = 0;
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();
            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                userDB = responseResult.getBody().getUserDBList();

                for (UserDBEntry iterator : userDB) {
                    if ( iterator.getName().equals(newName) && (iterator.getId() != Integer.parseInt(currentID)) )
                        resultCode = 2;
                    else if (newName.equals("client") || newName.equals("manager") || newName.equals("root")) resultCode = 2;
                } // end_for
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                resultCode = 4;
            } // end_try_catch

        } // end_else

        if ( resultCode == 1 ) {
            resultCode = 4;
            // запрос на изменение пользователя
            UserDBEntry updUser = new UserDBEntry();
            updUser.setId( Integer.parseInt(currentID) );
            updUser.setName( newName );
            updUser.setPassword( newPassword );
            updUser.setRole( "ROLE_CLIENT" );
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(updUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_UPDATE_USER_REQUEST, requestBody, String.class );
                if ( responseResult.getBody().equals("OK")) {
                    resultCode = 1;
                    currentName = newName;
                    currentPassword = newPassword;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("currentID", currentID);
        model.addAttribute("currentName", currentName);
        model.addAttribute("currentPassword", currentPassword);

        model.addAttribute("resultCode", resultCode);
        return "updateClientPage";
    } // end_method


    /**
     *  Метод описывает запрос на полученеи таблицы пользователей c ролью ROLE_MANAGER из удаленной БД
     *
     */
    @RequestMapping("/registerManager")
    public String registerManager(Model model) {

        List<UserDBEntry> userDB = new UserDBList().getUserDBList();

        // запрос к БД - получить лист с пользователями
        try {
            HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
            ResponseEntity<UserDBList> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
            Log.info("Status code: "+responseResult.getStatusCode());

            userDB = responseResult.getBody().getUserDBList();

            // оставляем лдя вывода только записи с ролью CLIENT_ROLE
            for (int i=0; i<userDB.size(); i++) {
                if ( !userDB.get(i).getRole().equals("ROLE_MANAGER") ) {
                    userDB.remove(i);
                    i--;
                } // end_if
            } // end_for

        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        model.addAttribute("userDB",userDB);
        return "registerManager";
    } // end_method

    /**
     *   Метод описывает запрос на создание пользоватлея c ролью ROLE_CLIENT в удаленной БД
     *
     */
    @RequestMapping("/createManagerRequest")
    public String createManagerRequest(
            @RequestParam(value="name",required = false, defaultValue = "0") String name,
            @RequestParam(value="password", required = false, defaultValue = "0") String password,
            Model model) {
        int resultCode=1;
        String resultUser = "";
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        if ( name.equals("0") || password.equals("0") ) resultCode = 0;
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();

            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                userDB = responseResult.getBody().getUserDBList();

                // проверка уникальности имени
                for (UserDBEntry iterator : userDB) {
                    if ( name.equals(iterator.getName()) ) resultCode = 2;
                    else if (name.equals("client") || name.equals("manager") || name.equals("root")) resultCode = 2;
                } // end_for
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                resultCode = 4;
            }
        } // end_else

        if ( resultCode == 1 ) {
            resultCode = 4;
            // запрос на создание пользователя
            UserDBEntry newUser = new UserDBEntry();
            newUser.setId(1); // не принципиально, т.к. при записи в базу ID назначается автоматически
            newUser.setName(name);
            newUser.setPassword(password);
            newUser.setRole("ROLE_MANAGER");
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(newUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_CREATE_USER_REQUEST, requestBody, String.class );
                if ( responseResult.getBody().equals("OK")) {
                    resultCode = 1;
                    resultUser = " login:"+name;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("resultUser",resultUser);
        model.addAttribute("result",resultCode);
        return "createManagerPage";
    } // end_method

    /**
     *  Метод реализует удаление пользователя с ролью ROLE_MANAGER из удаленной базы данных
     *
    */
    @RequestMapping("/deleteManagerRequest")
    public String deleteManagerRequest(@RequestParam(value = "selectUserID") String selectUserID, Model model) {
        // запрос на удаление пользователя
        try {
            HttpEntity<String> requestBody = new HttpEntity<>(selectUserID);
            ResponseEntity<String> responseResult =
                    restTemplate.postForEntity(URL_SERVER_CORE_DELETE_USER_REQUEST, requestBody, String.class );
        }
        catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
            Log.info( ex.getMessage() );
        } // end_try_catch

        registerManager(model);
        return "registerManager";
    } // end_method

    @RequestMapping("/updateManagerPage")
    public String updateManagerPage(
            @RequestParam(value="selectUserID",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="selectUserName",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="selectUserPassword", required = false) String currentPassword,
            Model model)
    {
        model.addAttribute("currentID",currentID);
        model.addAttribute("currentName",currentName);
        model.addAttribute("currentPassword",currentPassword);
        return "updateManagerPage";
    } // end_method

    /**
     *  Метод реализующий обновление информации о пользователе c ролью ROLE_MANAGER в удаленной БД
     *
     */
    @RequestMapping("/updateManagerRequest")
    public String updateManagerRequest(
            @RequestParam(value="currentIDUpd",required = false, defaultValue = "0") String currentID,
            @RequestParam(value="currentNameUpd",required = false, defaultValue = "0") String currentName,
            @RequestParam(value="currentPasswordUpd", required = false) String currentPassword,

            @RequestParam(value="newID",required = false, defaultValue = "0") String newID,
            @RequestParam(value="newName",required = false, defaultValue = "0") String newName,
            @RequestParam(value="newPassword", required = false, defaultValue = "0") String newPassword,
            Model model)
    {
        int resultCode = 1;
        // 0 - заполнены не все поля
        // 1 - успех
        // 2 - неуникальное имя
        // 3 - неверно указана роль
        // 4 - ошибка БД

        // проверка на заполнение полей
        if ( newName.equals("0") || newPassword.equals("0") ) resultCode = 0;
        else {
            List<UserDBEntry> userDB = new UserDBList().getUserDBList();
            // запрос к БД - получить лист с пользователями
            try {
                HttpEntity<String> requestBody = new HttpEntity<>("readUserDB");
                ResponseEntity<UserDBList> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_USERDB_REQUEST, requestBody, UserDBList.class );
                userDB = responseResult.getBody().getUserDBList();

                for (UserDBEntry iterator : userDB) {
                    if ( iterator.getName().equals(newName) && (iterator.getId() != Integer.parseInt(currentID)) )
                        resultCode = 2;
                    else if (newName.equals("client") || newName.equals("manager") || newName.equals("root")) resultCode = 2;
                } // end_for
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
                resultCode = 4;
            } // end_try_catch

        } // end_else

        if ( resultCode == 1 ) {
            resultCode = 4;
            // запрос на изменение пользователя
            UserDBEntry updUser = new UserDBEntry();
            updUser.setId( Integer.parseInt(currentID) );
            updUser.setName( newName );
            updUser.setPassword( newPassword );
            updUser.setRole( "ROLE_MANAGER" );
            try {
                HttpEntity<UserDBEntry> requestBody = new HttpEntity<>(updUser);
                ResponseEntity<String> responseResult =
                        restTemplate.postForEntity(URL_SERVER_CORE_UPDATE_USER_REQUEST, requestBody, String.class );
                if ( responseResult.getBody().equals("OK")) {
                    resultCode = 1;
                    currentName = newName;
                    currentPassword = newPassword;
                } // end_if
            }
            catch ( ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex ) {
                Log.info( ex.getMessage() );
            } // end_try_catch
        } // end_if

        model.addAttribute("currentID", currentID);
        model.addAttribute("currentName", currentName);
        model.addAttribute("currentPassword", currentPassword);

        model.addAttribute("resultCode", resultCode);
        return "updateManagerPage";
    } // end_method


} // end_class
