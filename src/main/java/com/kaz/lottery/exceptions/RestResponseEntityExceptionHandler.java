package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.AbstractServiceExceptionEntity;
import com.kaz.lottery.exceptions.entity.GenericExceptionEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ControllerAdvice to handle errors thrown during request processing (both expected and unexpected exceptions are handled).
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE = "An error was encountered while processing this request: ";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<AbstractServiceExceptionEntity> handleServiceException(ServiceException serviceException) {
        AbstractServiceExceptionEntity excEntity = serviceException.getExceptionEntity();
        HttpStatus status = HttpStatus.valueOf(excEntity.getHttpStatus());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(excEntity, responseHeaders, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AbstractServiceExceptionEntity> handleGeneralException(Exception ex) {
        LOGGER.error("Controller encountered an error processing request", ex);
        return handleServiceException(new ServiceException(
                new GenericExceptionEntity(ExceptionCode.UNEXPECTED_ERROR,
                        "An unexpected error occurred: " + ex.getClass().getSimpleName() + ", message: " + ex.getMessage(),
                        ExceptionUtils.getStackTrace(ex))));
    }

    /**
     * Customize the response body of all Exception types.
     * This method returns {@code null} by default.
     *
     * @param ex the exception
     * @param body the body to use for the response
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ERROR_MESSAGE, ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }
        GenericExceptionEntity excEntity = new GenericExceptionEntity(ex.getClass().getSimpleName(), ex.getMessage());
        excEntity.setHttpStatus(status.value());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(excEntity, headers, status);
    }
}
