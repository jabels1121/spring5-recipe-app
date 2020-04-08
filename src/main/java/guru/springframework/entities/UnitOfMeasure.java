package guru.springframework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@NoArgsConstructor
@Document
public class UnitOfMeasure {

    @Id
    private String id;
    private String description;

    public static List<UnitOfMeasure> createCollectionsOfUOMWithoutId(String... description) {
        return Arrays.stream(description)
                .map(s -> {
                    var unitOfMeasure = new UnitOfMeasure();
                    unitOfMeasure.setDescription(s);
                    return unitOfMeasure;
                }).collect(Collectors.toList());
    }

}
