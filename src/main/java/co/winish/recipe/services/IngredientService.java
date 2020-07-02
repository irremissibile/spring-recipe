package co.winish.recipe.services;

import co.winish.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteByRecipeIdAndIngredientId(Long recipeId, Long id);

}
