package co.winish.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, path = {"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {

        return "index";
    }
}
