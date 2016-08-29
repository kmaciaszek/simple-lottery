package com.kaz.lottery.exceptions;

import com.kaz.lottery.exceptions.entity.ForbiddenOperationExceptionEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kaz on 29/08/2016.
 */
public class ForbiddenOperationExceptionEntityTest {

    @Test
    public void testConstructor1() {
        ForbiddenOperationExceptionEntity unit = new ForbiddenOperationExceptionEntity("errorCode", "error");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(403, unit.getHttpStatus());
    }

    @Test
    public void testConstructor2() {
        ForbiddenOperationExceptionEntity unit = new ForbiddenOperationExceptionEntity("errorCode",
                "error", "errorDetail");
        assertEquals("errorCode", unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(403, unit.getHttpStatus());
    }

    @Test
    public void testConstructor3() {
        ForbiddenOperationExceptionEntity unit = new ForbiddenOperationExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals(null, unit.getErrorDetail());
        assertEquals(403, unit.getHttpStatus());
    }

    @Test
    public void testConstructor4() {
        ForbiddenOperationExceptionEntity unit = new ForbiddenOperationExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                "error", "errorDetail");
        assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), unit.getErrorCode());
        assertEquals("error", unit.getError());
        assertEquals("errorDetail", unit.getErrorDetail());
        assertEquals(403, unit.getHttpStatus());
    }
}
