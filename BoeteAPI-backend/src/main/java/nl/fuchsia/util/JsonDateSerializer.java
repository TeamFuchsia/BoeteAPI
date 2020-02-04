package nl.fuchsia.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Dit is om een {@link LocalDate} in een POJO om te zetten naar een {@link String} voor de JSON representatie.
 * <p>
 * Het implementeren van {@link JsonSerializer} zorgt ervoor dat Jackson dit kan gebruiken.
 */
public class JsonDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
