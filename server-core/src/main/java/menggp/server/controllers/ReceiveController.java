package menggp.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 *
 *  Контроллер принимающий REST-запросы на сервере
 *
 */

@RestController
public class ReceiveController {

    // Константы
    //------------------------------------------------------------------------
    private static final Logger Log = LoggerFactory.getLogger(ReceiveController.class);

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

        Log.info(str);

        // возвращаем полученные в запросе данные
        return str;
    } // end_method








} // end_class




