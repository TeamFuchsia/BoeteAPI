package nl.fuchsia.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponse handleMethodArgumentNotValidException (MethodArgumentNotValidException exception ) {
//        return new ErrorResponse(exception.getMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> invoerException (MethodArgumentNotValidException exception) {
        List<String> list = new ArrayList<>();
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            String defaultMessage = objectError.getDefaultMessage();
            list.add(defaultMessage);
        }
        return list;
    }
}
