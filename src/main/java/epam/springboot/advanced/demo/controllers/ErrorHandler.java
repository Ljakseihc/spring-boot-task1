package epam.springboot.advanced.demo.controllers;

import epam.springboot.advanced.demo.models.SimpleErrorResponse;
import epam.springboot.advanced.demo.models.exceptions.ConflictWithExistingResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public SimpleErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex.getLocalizedMessage());
        return new SimpleErrorResponse(
                ex.getLocalizedMessage(),
                "404"
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictWithExistingResourceException.class)
    public SimpleErrorResponse handleConflictWithExistingResourceException(ConflictWithExistingResourceException ex) {
        log.error(ex.getLocalizedMessage());
        return new SimpleErrorResponse(
                ex.getLocalizedMessage(),
                "409"
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public SimpleErrorResponse handleGlobalException(Exception ex) {
        log.error(ex.getLocalizedMessage());
        return new SimpleErrorResponse(
                "Internal error",
                "500"
        );
    }
}
