package com.kaz.lottery.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test for Ticket class.
 */
@RunWith(SpringRunner.class)
public class TicketTest {

    @Test
    public void testChecked() {
        Ticket ticket = new Ticket();
        ticket.setChecked(true);
        Assert.assertEquals(true, ticket.isChecked());
        ticket.setChecked(false);
        Assert.assertEquals(false, ticket.isChecked());

    }

    @Test
    public void testId() {
        Ticket ticket = new Ticket();
        ticket.setId("unit-test-id");
        Assert.assertEquals("unit-test-id", ticket.getId());
    }

    @Test
    public void testLines() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 1, 1}));
        lines.add(new TicketLine(new int[]{2, 0, 1}));
        ticket.setLines(lines);

        Assert.assertNotNull(ticket.getLines());
        Assert.assertEquals(2, ticket.getLines().size());
        Assert.assertEquals(0, ticket.getLines().get(0).getResult());
        Assert.assertEquals(0, ticket.getLines().get(1).getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 1, 1}, ticket.getLines().get(0).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{2, 0, 1}, ticket.getLines().get(1).getValues()));
    }

    @Test
    public void testAddLines() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 1, 1}));
        lines.add(new TicketLine(new int[]{2, 0, 1}));
        ticket.setLines(lines);

        List<TicketLine> more = new ArrayList<>();
        more.add(new TicketLine(new int[]{1, 2, 1}));
        more.add(new TicketLine(new int[]{2, 1, 1}));
        ticket.addLines(more);

        Assert.assertNotNull(ticket.getLines());
        Assert.assertEquals(4, ticket.getLines().size());
        Assert.assertEquals(0, ticket.getLines().get(0).getResult());
        Assert.assertEquals(0, ticket.getLines().get(1).getResult());
        Assert.assertEquals(0, ticket.getLines().get(2).getResult());
        Assert.assertEquals(0, ticket.getLines().get(3).getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 1, 1}, ticket.getLines().get(0).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{2, 0, 1}, ticket.getLines().get(1).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticket.getLines().get(2).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{2, 1, 1}, ticket.getLines().get(3).getValues()));
    }

    @Test
    public void testAddLinesInitialize() {
        Ticket ticket = new Ticket();

        List<TicketLine> more = new ArrayList<>();
        more.add(new TicketLine(new int[]{1, 2, 1}));
        more.add(new TicketLine(new int[]{2, 1, 1}));
        ticket.addLines(more);

        Assert.assertNotNull(ticket.getLines());
        Assert.assertEquals(2, ticket.getLines().size());
        Assert.assertEquals(0, ticket.getLines().get(0).getResult());
        Assert.assertEquals(0, ticket.getLines().get(1).getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticket.getLines().get(0).getValues()));
        Assert.assertTrue(Arrays.equals(new int[]{2, 1, 1}, ticket.getLines().get(1).getValues()));
    }
}
