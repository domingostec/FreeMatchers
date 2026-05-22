package org.example.freematchers.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.freematchers.exceptions.EmailAlreadyExistsException;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.exceptions.LackRequiredHoursException;
import org.example.freematchers.exceptions.ProjectInactiveException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.annotation.Nonnull;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @Nonnull MethodArgumentNotValidException ex,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode statusCode,
            @Nonnull WebRequest webRequest
    ) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Objects.toString(fieldError.getDefaultMessage(), "Invalid value."),
                        (existing, replacement) -> existing
                ));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                statusCode,
                "One or more fields have invalid values."
        );

        problemDetail.setInstance(URI.create(webRequest.getDescription(false).replace("uri=", "")));

        problemDetail.setProperty("timestamp", java.time.Instant.now());

        problemDetail.setProperty("invalid_fields", errors);

        return ResponseEntity.status(statusCode).body(problemDetail);
    }



    @ExceptionHandler(IdNotFoundException.class)
    public ProblemDetail IdNotFound(IdNotFoundException ex, HttpServletRequest http){
        return buildProblemDetail(HttpStatus.NOT_FOUND, "The described ID was not found.", ex.getMessage(), http);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail emailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest http){
       return buildProblemDetail(HttpStatus.CONFLICT, "The email address provided already exists.", ex.getMessage(), http);
    }

    @ExceptionHandler(ProjectInactiveException.class)
    public ProblemDetail projectInactive(ProjectInactiveException ex, HttpServletRequest http){

        return buildProblemDetail(HttpStatus.UNPROCESSABLE_CONTENT, "The requested project is inactive.", ex.getMessage(), http);
    }

    @ExceptionHandler(LackRequiredHoursException.class)
    public ProblemDetail lackRequiredHours(LackRequiredHoursException ex, HttpServletRequest http){
        return buildProblemDetail(HttpStatus.UNPROCESSABLE_CONTENT,"Invalid hours requested.", ex.getMessage(), http);
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String title, String detail, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }


}
