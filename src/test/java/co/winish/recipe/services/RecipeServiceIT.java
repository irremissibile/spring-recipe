package co.winish.recipe.services;

import co.winish.recipe.commands.RecipeCommand;
import co.winish.recipe.converters.RecipeCommandToRecipe;
import co.winish.recipe.converters.RecipeToRecipeCommand;
import co.winish.recipe.model.Recipe;
import co.winish.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RecipeServiceIT {

    final String NEW_DESCRIPTION = "Some shit here";

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Test
    @Transactional
    public void saveCommandWithDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testCommand = recipeToRecipeCommand.convert(testRecipe);

        testCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(testCommand);

        assertEquals(NEW_DESCRIPTION, savedCommand.getDescription());
        assertEquals(testRecipe.getId(), savedCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedCommand.getIngredients().size());
    }

}