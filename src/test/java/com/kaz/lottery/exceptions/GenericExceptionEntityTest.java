package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.GenericExceptionEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a unit test for GenericExceptionEntity class.
 */
public class GenericExceptionEntityTest {

    @Test
    public void testConstructor1() {
        GenericExceptionEntity unit = new GenericExceptionEntity("errorCode", "error");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(500, unit.getHttpStatus());
    }

    @Test
    public void testConstructor2() {
        GenericExceptionEntity unit = new GenericExceptionEntity("errorCode",
                "error", "errorDetail");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(500, unit.getHttpStatus());
    }

    @Test
    public void testConstructor3() {
        GenericExceptionEntity unit = new GenericExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(500, unit.getHttpStatus());
    }

    @Test
    public void testConstructor4() {
        GenericExceptionEntity unit = new GenericExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                "error", "errorDetail");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(500, unit.getHttpStatus());
    }
}
