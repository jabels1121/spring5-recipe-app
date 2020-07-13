package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.entities.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        if (!StringUtils.isEmpty(source.getId())) {
            ingredient.setId(source.getId());
        } else {
            source.setId(ingredient.getId());
        }
        ingredient.setAmount(source.getAmount());
        ingredient.setRecipeId(source.getRecipeId());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(null != source ? source.getUom() : null));
        return ingredient;
    }
}
