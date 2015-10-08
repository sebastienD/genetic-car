package fr.genetic.client.java.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ApiResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResponseErrorHandler.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String responseBody = getResponseBody(response);

        if (response != null && responseBody.contains("instanceId")) {
            ExceptionData exceptionData = mapper.readValue(responseBody, ExceptionData.class);
            throw new RestClientException(String.format("(%s) %s", exceptionData.instanceId, exceptionData.message));
        }
        throw new RestClientException("response status: " + response.getStatusText() + ", body: "+responseBody);
    }

    private String getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return new String(FileCopyUtils.copyToByteArray(responseBody), StandardCharsets.UTF_8);
            }
        }
        catch (IOException ex) {
            // ignore
        }
        return null;
    }

    public static class ExceptionData {
        public String instanceId;
        public String code;
        public String message;
    }
}
