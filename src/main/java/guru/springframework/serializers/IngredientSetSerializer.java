package guru.springframework.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import guru.springframework.entities.Ingredient;

import java.io.IOException;
import java.util.Set;

public class IngredientSetSerializer extends StdSerializer<Set<Ingredient>> {

    public IngredientSetSerializer() {
        this(null);
    }

    public IngredientSetSerializer(Class<Set<Ingredient>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Ingredient> ingredients, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if(ingredients != null) {
            if (ingredients.size() > 0) {
                jsonGenerator.writeStartArray();
                ingredients.forEach(ingredient -> {
                    try {
                        jsonGenerator.writeStartObject();
                        jsonGenerator.writeStringField("description", ingredient.getDescription());
                        jsonGenerator.writeNumberField("amount", ingredient.getAmount());
                        jsonGenerator.writeStringField("uom", ingredient.getUom().getDescription());
                        jsonGenerator.writeEndObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                jsonGenerator.writeEndArray();
            }
        }

    }
}
