package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.InvalidInputExceptionEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a unit test for InvalidInputExceptionEntity class.
 */
public class InvalidInputExceptionEntityTest {

    @Test
    public void testConstructor1() {
        InvalidInputExceptionEntity unit = new InvalidInputExceptionEntity("errorCode", "error");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor2() {
        InvalidInputExceptionEntity unit = new InvalidInputExceptionEntity("errorCode",
                "error", "errorDetail");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor3() {
        InvalidInputExceptionEntity unit = new InvalidInputExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor4() {
        InvalidInputExceptionEntity unit = new InvalidInputExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                "error", "errorDetail");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }
}
