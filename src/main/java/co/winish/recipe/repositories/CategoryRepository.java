package co.winish.recipe.repositories;

import co.winish.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
