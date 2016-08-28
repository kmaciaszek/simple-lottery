package com.kaz.lottery.exceptions.entity;

import com.kaz.lottery.exceptions.ExceptionCode;

import java.io.Serializable;

/**
 * This is a generic exception entity.
 */
public class GenericExceptionEntity extends AbstractServiceExceptionEntity implements Serializable {

    private static final long serialVersionUID = 731368480619801589L;

    private int httpStatus = 500;

    public GenericExceptionEntity() {
        super("", null);
    }

    public GenericExceptionEntity(String errorCode, String error) {
        super(errorCode, error);
    }

    public GenericExceptionEntity(String errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    public GenericExceptionEntity(ExceptionCode errorCode, String error) {
        super(errorCode, error);
    }

    public GenericExceptionEntity(ExceptionCode errorCode, String error, String errorDetail) {
        super(errorCode, error, errorDetail);
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

}