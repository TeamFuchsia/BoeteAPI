package nl.fuchsia.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

/**
 * Een stricte datum deserializer. Hiermee wordt een binnenkomende datum geparsed.
 * Zie {@link JsonDate} voor hoe het gebruikt wordt.
 * <p>
 * Het implementeren van {@link JsonDeserializer} zorgt ervoor dat Jackson hier mee kan werken.
 */
public class JsonDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd-MM-yyyy") // Dit is het patroon wat we willen parsen
            .parseDefaulting(ChronoField.ERA, 1) // Omdat we strict zijn, moeten we de default ERA instellen. 1 staat voor Na Christus.
            .toFormatter() // Maak de formatter
            .withResolverStyle(ResolverStyle.STRICT); // En maak de formatter strict

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return LocalDate.parse(value, FORMATTER);
    }
}
