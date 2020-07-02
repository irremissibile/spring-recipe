package co.winish.recipe.services;

import co.winish.recipe.model.Recipe;
import co.winish.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found, id: " + recipeId));

            byte[] primitives = file.getBytes();
            Byte[] image = new Byte[primitives.length];

            for (int i = 0; i < primitives.length; i++)
                image[i] = primitives[i];

            recipe.setImage(image);
            recipeRepository.save(recipe);
        } catch (IOException e) {

        }
    }
}
