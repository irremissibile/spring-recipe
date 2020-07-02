package co.winish.recipe.services;

import co.winish.recipe.commands.RecipeCommand;
import co.winish.recipe.converters.RecipeCommandToRecipe;
import co.winish.recipe.converters.RecipeToRecipeCommand;
import co.winish.recipe.model.Recipe;
import co.winish.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }


    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting all the recipes");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found, id: " + id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe converted = recipeCommandToRecipe.convert(recipeCommand);

        Recipe saved = recipeRepository.save(converted);
        log.debug("Saved Recipe, id: " + saved.getId());

        return recipeToRecipeCommand.convert(saved);
    }
}
