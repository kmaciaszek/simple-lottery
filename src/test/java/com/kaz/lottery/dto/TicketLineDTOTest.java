package com.kaz.lottery.dto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * This is a unit test for TicketLineDTO class.
 */
public class TicketLineDTOTest {

    @Test
    public void testConstructor() {
        TicketLineDTO ticketLineDTO = new TicketLineDTO(new int[]{1, 2, 0});
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 0}, ticketLineDTO.getValues()));
        Assert.assertEquals(0, ticketLineDTO.getResult());
    }

    @Test
    public void testValues() {
        TicketLineDTO ticketLineDTO = new TicketLineDTO();
        ticketLineDTO.setValues(new int[]{1, 2, 0});
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 0}, ticketLineDTO.getValues()));
        Assert.assertEquals(0, ticketLineDTO.getResult());
    }

    @Test
    public void testResult() {
        TicketLineDTO ticketLineDTO = new TicketLineDTO();
        Assert.assertEquals(0, ticketLineDTO.getResult());
        ticketLineDTO.setResult(10);
        Assert.assertEquals(10, ticketLineDTO.getResult());
    }
}
