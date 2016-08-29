package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kaz on 29/08/2016.
 */
public class ItemNotFoundExceptionEntityTest {

    @Test
    public void testConstructor1() {
        ItemNotFoundExceptionEntity unit = new ItemNotFoundExceptionEntity("errorCode", "error");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(404, unit.getHttpStatus());
    }

    @Test
    public void testConstructor2() {
        ItemNotFoundExceptionEntity unit = new ItemNotFoundExceptionEntity("errorCode",
                "error", "errorDetail");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(404, unit.getHttpStatus());
    }

    @Test
    public void testConstructor3() {
        ItemNotFoundExceptionEntity unit = new ItemNotFoundExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(404, unit.getHttpStatus());
    }

    @Test
    public void testConstructor4() {
        ItemNotFoundExceptionEntity unit = new ItemNotFoundExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                "error", "errorDetail");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(404, unit.getHttpStatus());
    }
}
