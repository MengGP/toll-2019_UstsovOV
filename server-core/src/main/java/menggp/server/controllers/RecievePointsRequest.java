package menggp.server.controllers;

import menggp.server.dao.CrudMethods;
import menggp.server.dao.CrudUserMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  Контроллер принимающий запросы на получение списка записей о местоположении из БД
 *
 */
@RestController
public class RecievePointsRequest {

    @Autowired
    private CrudMethods crudMethods;

    @RequestMapping(value = "/pointsRequest",method = RequestMethod.POST)
    @ResponseBody
    public String transmitLocations(@RequestBody String str) {

        int num;
        String result = "start string";

        num=Integer.parseInt(str);

        if ( crudMethods.tableSize() >= num )
            result = crudMethods.readWithReturn(num);
        else
            result = "В таблице только " +crudMethods.tableSize()+ " записей, вы запросили больше чем есть";

        return result;
    } // end_method



} // end_class
