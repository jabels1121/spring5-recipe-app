package guru.springframework.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import guru.springframework.entities.Category;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CategorySetToCategoryDescriptionList extends StdConverter<Set<Category>, List<String>> {

    @Override
    public List<String> convert(Set<Category> categories) {
        return categories.stream().map(Category::getDescription).collect(Collectors.toList());
    }
}
