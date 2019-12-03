package nl.fuchsia.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlers {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invoerException (MethodArgumentNotValidException exception) {
        List<String> list = new ArrayList<>();
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            String defaultMessage = objectError.getDefaultMessage();
            list.add(defaultMessage);
        }
        return new ErrorResponse(list);
    }
}
