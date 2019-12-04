package nl.fuchsia.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Deze klasse handelt de validatie exceptions af.
 */
@RestControllerAdvice
public class ExceptionHandlers {

    /**
     * De annotatie {@link ResponseStatus} geeft de waarde 400 terug via {@link HttpStatus#BAD_REQUEST} methode
     *
     * @param exception - de exception klasse die de invoer validate afhandelt.
     * @return - de holder {@link ErrorResponse} van de exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invoerException(MethodArgumentNotValidException exception) {
        List<String> list = new ArrayList<>();
        // per error wordt de defaultMessage eruit gefilterd.
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            String defaultMessage = objectError.getDefaultMessage();
            list.add(defaultMessage);
        }
        return new ErrorResponse(list);
    }
}