package com.kaz.lottery.dto;

import com.kaz.lottery.model.TicketLine;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by kaz on 29/08/2016.
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
