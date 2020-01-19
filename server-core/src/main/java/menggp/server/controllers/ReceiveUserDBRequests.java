package menggp.server.controllers;

import menggp.dto.UserDBEntry;
import menggp.dto.UserDBList;
import menggp.server.dao.CrudUserMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Контроллер обрабатывающий запросы связыные с базйо пользователей
 *
 */
@RestController
public class ReceiveUserDBRequests {

    private static final Logger Log = LoggerFactory.getLogger(ReceiveUserDBRequests.class);

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    CrudUserMethods crudUserMethods;

    // Методы
    //------------------------------------------------------------------------
    @RequestMapping(value = "/userDBRequest", method = RequestMethod.POST)
    @ResponseBody
    public UserDBList transmitUserDB(@RequestBody String str) {
        UserDBList userDBList = new UserDBList();
        if ( str.equals("readUserDB") ) {
            Log.info(" --- readUserDB operation start ");
            userDBList = crudUserMethods.readUserDBList();
            Log.info( userDBList.getUserDBList().toString() );
        } // end_if
        return userDBList;
    } // end_method

    @RequestMapping(value = "/createUserRequest", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody UserDBEntry newUser) {
        crudUserMethods.create(newUser.getName(), newUser.getPassword(), newUser.getRole(), true );
        String resultStatus = "OK";
        return resultStatus;
    } // end_method

    @RequestMapping(value = "/deleteUserRequest", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestBody String deleteUserID){
        crudUserMethods.deleteWithID( Integer.parseInt(deleteUserID) );
        return "OK";
    } // end_method

    @RequestMapping(value = "/updateUserRequest", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserRequest(@RequestBody UserDBEntry updUser) {
        crudUserMethods.updateUserWithID(updUser);
        return "OK";
    } // end_method



} // end_class
