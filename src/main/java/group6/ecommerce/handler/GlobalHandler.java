package group6.ecommerce.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception exc) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.BAD_REQUEST, exc.getLocalizedMessage());
        return problemDetail;
    }

    private ProblemDetail generateProblemDetail(HttpStatus httpStatus, String errorMessage) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                errorMessage);
        problemDetail.setProperty("Timestamp", System.currentTimeMillis());
        return problemDetail;
    }
}
