package com.kaz.lottery.utils;

import com.kaz.lottery.dto.TicketDTO;
import com.kaz.lottery.dto.TicketLineDTO;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaz on 28/08/2016.
 */
@RunWith(SpringRunner.class)
public class EntityConverterTest {

    private EntityConverter entityConverter;

    @Before
    public void setUp() {
        entityConverter = new EntityConverter();
    }

    @Test
    public void testConvert_TicketToTicketDTO() {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        TicketLine ticketLine = new TicketLine(new int[]{0, 2, 0});
        ticketLine.setResult(10);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 0, 1});
        ticketLine.setResult(10);
        lines.add(ticketLine);
        ticket.setLines(lines);
        ticket.setChecked(true);

        TicketDTO ticketDTO = entityConverter.convert(ticket);

        Assert.assertNotNull(ticketDTO);
        Assert.assertEquals(ticketId, ticketDTO.getId());
        Assert.assertTrue(ticketDTO.isChecked());
        Assert.assertNotNull(ticketDTO.getLines());
        Assert.assertEquals(2, ticketDTO.getLines().size());

        TicketLineDTO ticketLineDTO = ticketDTO.getLines().get(0);
        Assert.assertEquals(10, ticketLineDTO.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

        ticketLineDTO = ticketDTO.getLines().get(1);
        Assert.assertEquals(10, ticketLineDTO.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));
    }

    @Test
    public void testConvert_nullToTicketDTO() {
        TicketDTO ticketDTO = entityConverter.convert((Ticket) null);
        Assert.assertNull(ticketDTO);
    }

    @Test
    public void testConvert_TicketLinesToTicketLinesDTO() {
        List<TicketLine> lines = new ArrayList<>();
        TicketLine ticketLine = new TicketLine(new int[]{0, 2, 0});
        ticketLine.setResult(10);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 0, 1});
        ticketLine.setResult(10);
        lines.add(ticketLine);

        List<TicketLineDTO> linesDTO = entityConverter.convert(lines);

        Assert.assertNotNull(linesDTO);
        Assert.assertEquals(2, linesDTO.size());

        TicketLineDTO ticketLineDTO = linesDTO.get(0);
        Assert.assertEquals(10, ticketLineDTO.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

        ticketLineDTO = linesDTO.get(1);
        Assert.assertEquals(10, ticketLineDTO.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));
    }

    @Test
    public void testConvert_nullToTicketLinesDTO() {
        List<TicketLineDTO> linesDTO = entityConverter.convert((List<TicketLine>) null);
        Assert.assertNull(linesDTO);
    }
}
