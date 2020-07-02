package co.winish.recipe.services;

import co.winish.recipe.commands.IngredientCommand;
import co.winish.recipe.converters.IngredientCommandToIngredient;
import co.winish.recipe.converters.IngredientToIngredientCommand;
import co.winish.recipe.model.Ingredient;
import co.winish.recipe.model.Recipe;
import co.winish.recipe.repositories.RecipeRepository;
import co.winish.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found, id: " + recipeId));

        IngredientCommand ingredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found, id: " + id));

        return ingredientCommand;
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeRepository.findById(command.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found, id: " + command.getRecipeId()));

        recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .ifPresentOrElse(ingredient -> {
                    ingredient.setDescription(command.getDescription());
                    ingredient.setAmount(command.getAmount());
                    ingredient.setUnitOfMeasure(unitOfMeasureRepository
                            .findById(command.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("UnitOfMeasure not found, id: "
                                    + command.getUnitOfMeasure().getId())));
                }, () -> recipe.addIngredient(ingredientCommandToIngredient.convert(command)));

        Recipe savedRecipe = recipeRepository.save(recipe);

        Ingredient savedIngredient = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .or(() -> savedRecipe.getIngredients().stream()
                                .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                                .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                                .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                                .findFirst())
                .orElseThrow(() -> new RuntimeException("Saved ingredient not found, id: " + command.getId()));

        return ingredientToIngredientCommand.convert(savedIngredient);
    }


    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found, id: " + recipeId));

        Ingredient ingredientToDelete = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found, id: " + id));

        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDelete);

        recipeRepository.save(recipe);
    }
}
