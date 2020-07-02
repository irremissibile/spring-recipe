package co.winish.recipe.bootstrap;

import co.winish.recipe.model.Category;
import co.winish.recipe.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
//@Order(2)
public class CategoryBootstrap implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    public CategoryBootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.debug("Bootstrapping categories data");
        categoryRepository.saveAll(getCategories());
    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>(4);

        categories.add(new Category("American"));
        categories.add(new Category("Italian"));
        categories.add(new Category("Mexican"));
        categories.add(new Category("Fast Food"));

        return categories;
    }
}

