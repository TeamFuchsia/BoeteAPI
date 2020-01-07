package nl.fuchsia.util;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Dit is een zelfgemaakte annotatie die Jackson annotaties bevat.
 * Dat geef ik aan met {@link JacksonAnnotationsInside}.
 * Als je dat gebruikt, kun je annotaties van Jackson groeperen. Ik gebruik dat hier om de annotaties
 * {@link JsonDeserialize} en {@link JsonSerialize} samen te voegen tot één annotatie.
 * Nu, waar wij stricte datums willen (waarbij 30-02-2019 dus gewoon FOUT is) kunnen we deze annotatie gebruiken.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonDeserialize(using = JsonDateDeserializer.class)
@JsonSerialize(using = JsonDateSerializer.class)
public @interface JsonDate {
}
