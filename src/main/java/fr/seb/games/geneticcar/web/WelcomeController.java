package fr.seb.games.geneticcar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sebastien on 18/01/2015.
 */
@Controller
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ModelAndView home() {
        ModelAndView home = new ModelAndView("/index.html");
        return home;
    }

}
