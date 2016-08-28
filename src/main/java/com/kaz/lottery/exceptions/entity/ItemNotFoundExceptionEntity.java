package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

/**
 * This exception is thrown when a requested resource has not been found.
 */
public class ItemNotFoundExceptionEntity extends AbstractServiceExceptionEntity {

    public ItemNotFoundExceptionEntity(String errorCode, String error) {
        super(errorCode, error);
    }

    public ItemNotFoundExceptionEntity(String errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    public ItemNotFoundExceptionEntity(ExceptionCode errorCode, String error) {
        super(errorCode, error);
    }

    public ItemNotFoundExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    @Override
    public int getHttpStatus() {
        return 404;
    }
}
