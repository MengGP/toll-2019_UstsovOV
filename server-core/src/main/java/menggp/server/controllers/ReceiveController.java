package menggp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 *  Контроллер принимающий REST-запросы на сервере
 *
 */

@RestController
public class ReceiveController {

     /*
     * тест доступности контекста
    @RequestMapping("/location")
    public String receiveLocation() {
        System.out.println(" --- context available --- ");
        return "It's os good";
    }*/

    @RequestMapping(value="/location",method = RequestMethod.POST)
    @ResponseBody
    public String receiveLocation(@RequestBody String str) {
        System.out.println(" --- OK --- ");
        return str;
    } // end_method








} // end_class




