package fr.genetic.server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.UUID;

/**
 * {@see https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc }
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionData> defaultErrorHandler(Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        ExceptionData data = new ExceptionData("42", e.getMessage());
        LOGGER.error("Exception " + data.getInstanceId(), e);
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionData> handleBadRequests(IllegalArgumentException e) throws IOException {
        ExceptionData data = new ExceptionData("gen-validation", e.getMessage());
        LOGGER.warn("Exception {} : {}", data.getInstanceId(), e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionData> handleBadRequests(MethodArgumentNotValidException e) throws IOException {
        ExceptionData data = new ExceptionData("spring-validation", e.getMessage());
        LOGGER.warn("Exception " + data.getInstanceId(), e);
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    public static class ExceptionData {
        private String instanceId;
        private String code;
        private String message;

        public ExceptionData(String code, String message) {
            this.instanceId = UUID.randomUUID().toString();
            this.code = code;
            this.message = message;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
