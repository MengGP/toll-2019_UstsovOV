package menggp.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PointsNumberController {

    @RequestMapping("/requestPoints")
    public String pointsNumber(@RequestParam(value="pointsNum",required = false, defaultValue = "0" ) String pointsNum, Model model) {

        int num;
        num = Integer.parseInt(pointsNum);
        String result;

        // проверка на пустое поле - если 0 - это значит значение по умолчанию, т.е. не было введено ничего
        if ( num == 0 ) {
            System.out.println(" ---> получен 0");
            result = "ничего, введите то что ";
        }
        else {
            result = ""+num;
        }

        model.addAttribute("result",result);

        return "pointsNumberPage";
    } // end_method

} // end_class
