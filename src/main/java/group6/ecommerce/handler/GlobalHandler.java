package group6.ecommerce.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import group6.ecommerce.exception.NotFoundException;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception exc) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.BAD_REQUEST, exc.getLocalizedMessage());
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail handleNotFoundException(NotFoundException exc) {
        ProblemDetail problemDetail = generateProblemDetail(HttpStatus.BAD_REQUEST, exc.getMessage());
        return problemDetail;
    }

    private ProblemDetail generateProblemDetail(HttpStatus httpStatus, String errorMessage) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                httpStatus,
                errorMessage);
        problemDetail.setProperty("Timestamp", System.currentTimeMillis());
        return problemDetail;
    }
}
