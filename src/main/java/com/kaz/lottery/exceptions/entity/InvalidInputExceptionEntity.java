package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

/**
 * This exception is thrown when a user provided an invalid due to which the request cannot be processed.
 */
public class InvalidInputExceptionEntity extends AbstractServiceExceptionEntity {

    public InvalidInputExceptionEntity(String errorCode, String error) {
        super(errorCode, error);
    }

    public InvalidInputExceptionEntity(String errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    public InvalidInputExceptionEntity(ExceptionCode errorCode, String error) {
        super(errorCode, error);
    }

    public InvalidInputExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    @Override
    public int getHttpStatus() {
        return 422;
    }

}
