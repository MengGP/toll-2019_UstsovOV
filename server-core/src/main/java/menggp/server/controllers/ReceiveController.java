package menggp.server.controllers;

import menggp.server.services.WriteLocationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // связанные классы
    //------------------------------------------------------------------------
    @Autowired
    private WriteLocationService writeLocationService;

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

        writeLocationService.writeAll(str);

        // возвращаем полученные в запросе данные
        return str;
    } // end_method








} // end_class




