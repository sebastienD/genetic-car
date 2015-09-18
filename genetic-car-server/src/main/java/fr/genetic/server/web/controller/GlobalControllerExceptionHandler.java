package fr.genetic.server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /*
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict() {
        // Nothing to do
    }
    */

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
    void handleBadRequests(HttpServletResponse response, IllegalArgumentException e) throws IOException {
        LOGGER.error("IllegalArgumentException", e);
        // TODO a revoir
        response.sendError(HttpStatus.BAD_REQUEST.value());
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
