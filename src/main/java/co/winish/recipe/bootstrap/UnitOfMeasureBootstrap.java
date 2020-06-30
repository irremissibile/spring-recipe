package co.winish.recipe.bootstrap;

import co.winish.recipe.model.UnitOfMeasure;
import co.winish.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Order(1)
public class UnitOfMeasureBootstrap implements ApplicationRunner {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureBootstrap(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.debug("Bootstrapping UOM data");
        unitOfMeasureRepository.saveAll(getUnitsOfMeasure());
    }

    private List<UnitOfMeasure> getUnitsOfMeasure() {
        List<UnitOfMeasure> unitsOfMeasure = new ArrayList<>(8);

        unitsOfMeasure.add(new UnitOfMeasure("Teaspoon"));
        unitsOfMeasure.add(new UnitOfMeasure("Tablespoon"));
        unitsOfMeasure.add(new UnitOfMeasure("Cup"));
        unitsOfMeasure.add(new UnitOfMeasure("Pinch"));
        unitsOfMeasure.add(new UnitOfMeasure("Ounce"));
        unitsOfMeasure.add(new UnitOfMeasure("Each"));
        unitsOfMeasure.add(new UnitOfMeasure("Dash"));
        unitsOfMeasure.add(new UnitOfMeasure("Pint"));

        return unitsOfMeasure;
    }
}
