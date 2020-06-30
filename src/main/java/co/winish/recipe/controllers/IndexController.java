package co.winish.recipe.controllers;

import co.winish.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping(method = RequestMethod.GET, path = {"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
