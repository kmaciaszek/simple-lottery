package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.ItemAlreadyExistsExceptionEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a unit test for ItemAlreadyExistsExceptionEntity class.
 */
public class ItemAlreadyExistsExceptionEntityTest {

    @Test
    public void testConstructor1() {
        ItemAlreadyExistsExceptionEntity unit = new ItemAlreadyExistsExceptionEntity("errorCode", "error");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor2() {
        ItemAlreadyExistsExceptionEntity unit = new ItemAlreadyExistsExceptionEntity("errorCode",
                "error", "errorDetail");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor3() {
        ItemAlreadyExistsExceptionEntity unit = new ItemAlreadyExistsExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }

    @Test
    public void testConstructor4() {
        ItemAlreadyExistsExceptionEntity unit = new ItemAlreadyExistsExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                "error", "errorDetail");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(422, unit.getHttpStatus());
    }
}
