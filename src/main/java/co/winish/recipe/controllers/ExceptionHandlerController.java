package co.winish.recipe.controllers;

import co.winish.recipe.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Model model, Exception exception) {
        log.error("Handling NotFoundException");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "404error";
    }


    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(Model model, Exception exception) {
        log.error("Handling NumberFormatException");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "400error";
    }

}
