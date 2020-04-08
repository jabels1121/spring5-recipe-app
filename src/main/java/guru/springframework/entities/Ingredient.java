package guru.springframework.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
/*@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})*/
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class Ingredient {

    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
}
