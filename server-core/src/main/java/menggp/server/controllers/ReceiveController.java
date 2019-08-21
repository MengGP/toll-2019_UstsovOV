package menggp.server.controllers;

import menggp.server.dao.CrudMethods;
import menggp.server.dao.LocationEntity;
import menggp.server.dao.repo.LocationsRepository;
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

    @Autowired
    CrudMethods crudMethods;

    @Autowired
    LocationsRepository locationsRepository;

     /*
     * тест доступности контекста
    @RequestMapping("/location")
    public String receiveLocation() {
        System.out.println(" --- context available --- ");
        return "It's os good";
    }*/

    // Методы
    //------------------------------------------------------------------------

    // метод обрабатывающих входящие POST-запросы с координатами от tracker-core
    @RequestMapping(value="/location",method = RequestMethod.POST)
    @ResponseBody
    public String receiveLocation(@RequestBody String str) {

        writeLocationService.writeAll(str);
        crudMethods.create(str);

        // возвращаем полученные в запросе данные
        return str;
    } // end_method

} // end_class




