package com.kaz.lottery.persistence;

import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.exceptions.entity.ItemAlreadyExistsExceptionEntity;
import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test for TicketInMemoryObjectRepository class.
 */
@RunWith(SpringRunner.class)
public class TicketInMemoryObjectRepositoryTest {

    private TicketObjectRepository ticketObjectRepository;

    private List<String> ticketsToDelete = new ArrayList<>();

    @Before
    public void setUp() {
        ticketObjectRepository = new TicketInMemoryObjectRepository();
    }

    @After
    public void cleanUp() {
        for (String id : ticketsToDelete) {
            try {
                ticketObjectRepository.delete(id);
            } catch (Exception e) {
                //
            }
        }
    }

    @Test
    public void createAndRead_success() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        lines.add(new TicketLine(new int[]{1, 2, 1}));
        ticket.setLines(lines);

        Ticket created = ticketObjectRepository.create(ticket);
        String createdId = created.getId();
        ticketsToDelete.add(created.getId());
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertFalse(created.getId().isEmpty());
        Assert.assertFalse(created.isChecked());
        Assert.assertNotNull(created.getLines());
        Assert.assertEquals(2, created.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine  ticketLine =  created.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));

        ticketLine =  created.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticketLine.getValues()));

        // Read the just created Ticket form storage.
        Ticket read = ticketObjectRepository.read(createdId);
        Assert.assertNotNull(read);
        Assert.assertNotNull(read.getId());
        Assert.assertFalse(read.getId().isEmpty());
        Assert.assertFalse(read.isChecked());
        Assert.assertEquals(createdId, read.getId());
        Assert.assertNotNull(read.getLines());
        Assert.assertEquals(2, read.getLines().size());

        // Verify the lines have not been corrupted.
        ticketLine =  read.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));

        ticketLine =  read.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticketLine.getValues()));
    }

    @Test
    public void createAndRead_idProvided() {
        Ticket ticket = new Ticket();
        final String ticketId = "unit-test-ticket-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        lines.add(new TicketLine(new int[]{1, 2, 1}));
        ticket.setLines(lines);

        Ticket created = ticketObjectRepository.create(ticket);
        ticketsToDelete.add(created.getId());
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        // Check that the id is the one which has been provided.
        Assert.assertEquals(ticketId, created.getId());

        Ticket read = ticketObjectRepository.read(ticketId);
        Assert.assertNotNull(read);
        Assert.assertNotNull(read.getId());
        Assert.assertFalse(read.getId().isEmpty());
        // Check that the id is the one which has been provided.
        Assert.assertEquals(ticketId, read.getId());
    }

    @Test
    public void createAndRead_alreadyExists() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        lines.add(new TicketLine(new int[]{1, 2, 1}));
        ticket.setLines(lines);
        Ticket created = ticketObjectRepository.create(ticket);
        ticketsToDelete.add(created.getId());
        try {
            created = ticketObjectRepository.create(ticket);
            ticketsToDelete.add(created.getId());
            Assert.fail("Expected ServiceException to be thrown");
        } catch (ServiceException e) {
            if ( !(e.getExceptionEntity() instanceof ItemAlreadyExistsExceptionEntity)) {
                Assert.fail("Expected ItemAlreadyExistsExceptionEntity.");
            }
        }
    }

    @Test
    public void read_idNull() {
        Ticket ticket = ticketObjectRepository.read(null);
        Assert.assertNull(ticket);
    }

    @Test
    public void update_success() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Ticket created = ticketObjectRepository.create(ticket);
        String createdId = created.getId();
        ticketsToDelete.add(created.getId());

        // Add two new lines to the Ticket.
        List<TicketLine> newlines = new ArrayList<>();
        newlines.add(new TicketLine(new int[]{1, 0, 0}));
        newlines.add(new TicketLine(new int[]{1, 0, 2}));
        created.addLines(newlines);

        Ticket updated = ticketObjectRepository.update(created);
        Assert.assertNotNull(updated);
        Assert.assertFalse(updated.getId().isEmpty());
        Assert.assertEquals(createdId, updated.getId());
        Assert.assertFalse(updated.isChecked());
        Assert.assertNotNull(updated.getLines());
        Assert.assertEquals(3, updated.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine ticketLine =  updated.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));

        ticketLine =  updated.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 0}, ticketLine.getValues()));

        ticketLine =  updated.getLines().get(2);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 2}, ticketLine.getValues()));

        // Read the just updated Ticket.
        Ticket read = ticketObjectRepository.read(createdId);
        Assert.assertNotNull(read);
        Assert.assertFalse(read.getId().isEmpty());
        Assert.assertFalse(read.isChecked());
        Assert.assertEquals(createdId, read.getId());
        Assert.assertNotNull(read.getLines());
        Assert.assertEquals(3, read.getLines().size());

        // Verify the lines have not been corrupted.
        ticketLine =  read.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));

        ticketLine =  read.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 0}, ticketLine.getValues()));

        ticketLine =  read.getLines().get(2);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 2}, ticketLine.getValues()));
    }

    @Test
    public void update_missingId() {
        try {
            Ticket ticket = new Ticket();
            List<TicketLine> lines = new ArrayList<>();
            lines.add(new TicketLine(new int[]{1, 0, 1}));
            ticket.setLines(lines);

            ticketObjectRepository.update(ticket);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if ( !(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
    }

    @Test
    public void update_notExistingId() {
        try {
            Ticket ticket = new Ticket();
            ticket.setId("unit-test-ticket-id-1");
            List<TicketLine> lines = new ArrayList<>();
            lines.add(new TicketLine(new int[]{1, 0, 1}));
            ticket.setLines(lines);

            ticketObjectRepository.update(ticket);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if ( !(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
    }

    @Test
    public void delete_success() {
        Ticket ticket = new Ticket();
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Ticket created = ticketObjectRepository.create(ticket);
        ticketsToDelete.add(created.getId());

        boolean result = ticketObjectRepository.delete(created.getId());
        Assert.assertTrue(result);

        Ticket read = ticketObjectRepository.read(created.getId());
        Assert.assertNull(read);
    }

    @Test
    public void delete_notExisting() {
        boolean result = ticketObjectRepository.delete("unit-test-ticket-id-1");
        Assert.assertFalse(result);
    }
}
