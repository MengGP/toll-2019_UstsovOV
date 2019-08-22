package menggp.server.controllers;

import menggp.dto.UserDBList;
import menggp.server.dao.CrudUserMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ReceiveUserDBRequests {

    private static final Logger Log = LoggerFactory.getLogger(ReceiveUserDBRequests.class);

    @Autowired
    CrudUserMethods crudUserMethods;

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
    }



} // end_class
