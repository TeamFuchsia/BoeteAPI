package nl.fuchsia.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Door onderstaande annotatie vangt deze classe posts op doe niet door de validatie komen.
 */
@RestControllerAdvice
public class ExceptionHandlers {

    /**
     * @ResponseStatus zorgt er voor dat de foutcode 400 wordt gegeven mitst de invoer niet valide is.
     * @ExceptionHandler zorgt er voor dat de foute invoer uit de Json wordt opgevangen.
     * @param exception wordt gegooid en deze is van de class MethodArgumentNotValidException, speciaal voor validatiefouten.
     * In de @return wordt de message opgehaald die bij de validatieeisen in de Feit class zijn gedefinieerd.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ){
        return new ErrorResponse(exception.getMessage());
    }
}
