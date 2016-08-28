package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.AbstractServiceExceptionEntity;

/**
 * This exception is a wrapper for a more specific exception.
 */
public class ServiceException extends RuntimeException {

    private AbstractServiceExceptionEntity exceptionEntity;

    public ServiceException(AbstractServiceExceptionEntity exceptionEntity) {
        this.exceptionEntity = exceptionEntity;
    }

    public AbstractServiceExceptionEntity getExceptionEntity() {
        return exceptionEntity;
    }
}
