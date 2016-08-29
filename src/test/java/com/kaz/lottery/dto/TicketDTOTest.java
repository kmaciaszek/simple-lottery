package com.kaz.lottery.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test for TicketDTO class.
 */
@RunWith(SpringRunner.class)
public class TicketDTOTest {

    @Test
    public void testChecked() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setChecked(true);
        Assert.assertEquals(true, ticketDTO.isChecked());
        ticketDTO.setChecked(false);
        Assert.assertEquals(false, ticketDTO.isChecked());

    }

    @Test
    public void testId() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId("unit-test-id");
        Assert.assertEquals("unit-test-id", ticketDTO.getId());
    }

    @Test
    public void testLines() {
        TicketDTO ticketDTO = new TicketDTO();
        List<TicketLineDTO> lines = new ArrayList<>();
        lines.add(new TicketLineDTO(new int[]{1, 1, 1}));
        lines.add(new TicketLineDTO(new int[]{2, 0, 1}));
        ticketDTO.setLines(lines);

        Assert.assertNotNull(ticketDTO.getLines());
        Assert.assertEquals(2, ticketDTO.getLines().size());
        Assert.assertEquals(0, ticketDTO.getLines().get(0).getResult());
        Assert.assertEquals(0, ticketDTO.getLines().get(1).getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 1, 1}, ticketDTO.getLines().get(0).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{2, 0, 1}, ticketDTO.getLines().get(1).getValues()));
    }
}
