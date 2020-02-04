package nl.fuchsia.exceptionhandlers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class NotFoundException extends RuntimeException {

    private List<String> errors = new ArrayList<>();

    public NotFoundException(String msg) {
        super(msg);
    }
}
