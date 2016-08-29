package com.kaz.lottery.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by kaz on 29/08/2016.
 */
public class TicketLineTest {

    @Test
    public void testConstructor() {
        TicketLine ticketLine = new TicketLine(new int[]{1, 2, 0});
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 0}, ticketLine.getValues()));
        Assert.assertEquals(0, ticketLine.getResult());
    }

    @Test
    public void testValues() {
        TicketLine ticketLine = new TicketLine();
        ticketLine.setValues(new int[]{1, 2, 0});
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 0}, ticketLine.getValues()));
        Assert.assertEquals(0, ticketLine.getResult());
    }

    @Test
    public void testResult() {
        TicketLine ticketLine = new TicketLine();
        Assert.assertEquals(0, ticketLine.getResult());
        ticketLine.setResult(10);
        Assert.assertEquals(10, ticketLine.getResult());
    }
}
