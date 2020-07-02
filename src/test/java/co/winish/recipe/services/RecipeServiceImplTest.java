package co.winish.recipe.services;

import co.winish.recipe.model.Recipe;
import co.winish.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }


    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());
        assertEquals(recipe, recipes.iterator().next());
        verify(recipeRepository, times(1)).findAll();
    }
}