package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

/**
 * This exception is thrown when user is trying to perform an operation is is forbidden.
 */
public class ForbiddenOperationExceptionEntity extends AbstractServiceExceptionEntity {

    public ForbiddenOperationExceptionEntity(String errorCode, String error) {
        super(errorCode, error);
    }

    public ForbiddenOperationExceptionEntity(ExceptionCode errorCode, String error) {
        super(errorCode, error);
    }

    public ForbiddenOperationExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    public ForbiddenOperationExceptionEntity(String errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    @Override
    public int getHttpStatus() {
        return 403;
    }
}
