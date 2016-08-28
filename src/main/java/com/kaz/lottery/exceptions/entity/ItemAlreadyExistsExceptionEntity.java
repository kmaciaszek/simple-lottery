package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

/**
 * This exception is thrown when a resource which is being persisted already exists.
 */
public class ItemAlreadyExistsExceptionEntity extends AbstractServiceExceptionEntity {

    public ItemAlreadyExistsExceptionEntity(String errorCode, String error) {
        super(errorCode, error);
    }

    public ItemAlreadyExistsExceptionEntity(String errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    public ItemAlreadyExistsExceptionEntity(ExceptionCode errorCode, String error) {
        super(errorCode, error);
    }

    public ItemAlreadyExistsExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    @Override
    public int getHttpStatus() {
        return 422;
    }
}
