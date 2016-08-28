package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

/**
 * Subclasses of this class are used as the Response entity for ServiceException.
 * Each subclass is responsible for providing the response code, which should be
 * a valid HTTP status code.
 *
 */
public abstract class AbstractServiceExceptionEntity {

    /**
     * If left as min value (unset), getErrorCode defaults to the same value as the HTTP status specified in subclasses
     */
    private String errorCode;

    /**
     * Exception message, short description of the error.
     */
    private String error;

    /**
     * More details about the problem or it can be a stack trace generated at the time of the exception.
     */
    private String errorDetail;

    /**
     *
     */
    public AbstractServiceExceptionEntity(String errorCode, String error) {
        this(errorCode, error, null);
    }

    public AbstractServiceExceptionEntity(ExceptionCode errorCode, String error) {
        this(errorCode.toString(), error, null);
    }
    public AbstractServiceExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        this(errorCode.toString(), error, errorDetail);
    }

    public AbstractServiceExceptionEntity(String errorCode, String error, String errorDetail) {
        this.errorCode = errorCode;
        this.error = error;
        this.errorDetail = errorDetail;
    }

    public abstract int getHttpStatus();

    public String getError() {
        return error;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public String getErrorCode() {
        return errorCode == null ? String.valueOf(getHttpStatus()) : errorCode;
    }
}

