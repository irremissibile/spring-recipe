package co.winish.recipe.services;

import co.winish.recipe.commands.RecipeCommand;
import co.winish.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
