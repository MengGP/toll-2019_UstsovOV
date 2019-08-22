package menggp.server.controllers;

import org.springframework.web.bind.annotation.*;

/**
 *  Контроллер принимающий запросы на получение списка записей о местоположении из БД
 *
 */
@RestController
public class RecievePointsRequest {

    @RequestMapping(value = "/pointsRequest",method = RequestMethod.POST)
    @ResponseBody
    public String transmitLocations(@RequestBody String str) {
        String result = "start string";

        result = result + " + " + str;

        return result;
    } // end_method



} // end_class
