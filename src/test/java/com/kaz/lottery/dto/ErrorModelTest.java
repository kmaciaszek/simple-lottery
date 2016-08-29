package com.kaz.lottery.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by kaz on 29/08/2016.
 */
@RunWith(SpringRunner.class)
public class ErrorModelTest {

    @Test
    public void testHttpStatus() {
        ErrorModel errorModel = new ErrorModel();
        Assert.assertNull(errorModel.getHttpStatus());
        errorModel.setHttpStatus(404);
        Assert.assertNotNull(errorModel.getHttpStatus());
        Assert.assertEquals(404, errorModel.getHttpStatus().intValue());
    }

    @Test
    public void testErrorCode() {
        ErrorModel errorModel = new ErrorModel();
        Assert.assertNull(errorModel.getErrorCode());
        errorModel.setErrorCode("ERROR-CODE");
        Assert.assertNotNull(errorModel.getErrorCode());
        Assert.assertEquals("ERROR-CODE", errorModel.getErrorCode());
    }

    @Test
    public void testError() {
        ErrorModel errorModel = new ErrorModel();
        Assert.assertNull(errorModel.getError());
        errorModel.setError("ERROR");
        Assert.assertNotNull(errorModel.getError());
        Assert.assertEquals("ERROR", errorModel.getError());
    }

    @Test
    public void testErrorDetail() {
        ErrorModel errorModel = new ErrorModel();
        Assert.assertNull(errorModel.getErrorDetail());
        errorModel.setErrorDetail("ERROR-DETAIL");
        Assert.assertNotNull(errorModel.getErrorDetail());
        Assert.assertEquals("ERROR-DETAIL", errorModel.getErrorDetail());
    }
}
