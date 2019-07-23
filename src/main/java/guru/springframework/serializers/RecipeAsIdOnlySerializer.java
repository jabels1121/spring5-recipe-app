package guru.springframework.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import guru.springframework.entities.Recipe;

import java.io.IOException;
import java.util.List;

public class RecipeAsIdOnlySerializer extends StdSerializer<List<Recipe>> {

    public RecipeAsIdOnlySerializer() {
        this(null);
    }

    public RecipeAsIdOnlySerializer(Class<List<Recipe>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Recipe> recipes, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

    }
}
