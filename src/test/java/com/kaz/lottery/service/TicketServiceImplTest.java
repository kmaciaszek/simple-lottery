package com.kaz.lottery.service;

import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.exceptions.entity.ForbiddenOperationExceptionEntity;
import com.kaz.lottery.exceptions.entity.InvalidInputExceptionEntity;
import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import com.kaz.lottery.persistence.TicketObjectRepository;
import com.kaz.lottery.rules.TicketLineRules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test for TicketServiceImpl class.
 */
@RunWith(SpringRunner.class)
public class TicketServiceImplTest {

    private TicketService ticketService;

    @Mock
    private TicketObjectRepository ticketObjectRepository;

    @Mock
    private TicketLineRules ticketLineRules;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketService = new TicketServiceImpl();
        Whitebox.setInternalState(ticketService, "ticketInMemoryObjectRepository", ticketObjectRepository);
        Whitebox.setInternalState(ticketService, "sum2WinsTicketLineRules", ticketLineRules);
    }

    @Test
    public void createTicket_success() {

        Mockito.when(ticketLineRules.randomTicketLines(2))
                .thenReturn(Arrays.asList(new TicketLine(new int[]{1, 2, 1}), new TicketLine(new int[]{1, 0, 1})));

        ticketService.createTicket(2);

        Mockito.verify(ticketLineRules, Mockito.times(1)).randomTicketLines(2);
        Mockito.verifyNoMoreInteractions(ticketLineRules);
        ArgumentCaptor<Ticket> ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).create(ticketArgumentCaptor.capture());
        Mockito.verifyNoMoreInteractions(ticketObjectRepository);

        Ticket captured = ticketArgumentCaptor.getValue();
        Assert.assertNotNull(captured);
        Assert.assertNull(captured.getId());
        Assert.assertFalse(captured.isChecked());
        Assert.assertNotNull(captured.getLines());
        Assert.assertEquals(2, captured.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine  ticketLine =  captured.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticketLine.getValues()));

        ticketLine =  captured.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));
    }

    @Test
    public void createTicket_invalidNoOfLines() {
        for (int noOfLines : new int[]{-1, -11, 0, -221, -1000}) {
            try {
                ticketService.createTicket(noOfLines);
                Assert.fail("Expected ServiceException.");
            } catch (ServiceException e) {
                if (!(e.getExceptionEntity() instanceof InvalidInputExceptionEntity)) {
                    Assert.fail("Expected InvalidInputExceptionEntity.");
                }
            }
        }
    }

    @Test
    public void readTicket_success() {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        ticket.setLines(Arrays.asList(new TicketLine(new int[]{1, 2, 1}), new TicketLine(new int[]{1, 0, 1})));

        Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(ticket);

        Ticket read = ticketService.readTicket(ticketId);

        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(ticketId);

        Assert.assertNotNull(read);
        Assert.assertEquals(ticketId, read.getId());
        Assert.assertFalse(read.isChecked());
        Assert.assertNotNull(read.getLines());
        Assert.assertEquals(2, read.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine  ticketLine =  read.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticketLine.getValues()));

        ticketLine =  read.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));
    }

    @Test
    public void readTicket_nullId() {
        try {
            Mockito.when(ticketObjectRepository.read(null)).thenReturn(null);
            ticketService.readTicket(null);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(null);
    }

    @Test
    public void readTicket_idNotFound() {
        String ticketId = "unit-test-id-1";
        try {
            Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(null);
            ticketService.readTicket(ticketId);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(ticketId);
    }

    @Test
    public void addLines_success() {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 2, 1}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(ticket);
        Mockito.when(ticketLineRules.randomTicketLines(2)).thenReturn(Arrays.asList(new TicketLine(new int[]{0, 2, 0}), new TicketLine(new int[]{0, 0, 2})));

        Mockito.when(ticketObjectRepository.update(Mockito.any(Ticket.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Ticket updated = ticketService.addLines(ticketId, 2);

        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(ticketId);
        Mockito.verify(ticketLineRules, Mockito.times(1)).randomTicketLines(2);

        Assert.assertNotNull(updated);
        Assert.assertEquals(ticketId, updated.getId());
        Assert.assertFalse(updated.isChecked());
        Assert.assertNotNull(updated.getLines());
        Assert.assertEquals(4, updated.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine  ticketLine =  updated.getLines().get(0);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 2, 1}, ticketLine.getValues()));

        ticketLine =  updated.getLines().get(1);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));

        ticketLine =  updated.getLines().get(2);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLine.getValues()));

        ticketLine =  updated.getLines().get(3);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{0, 0, 2}, ticketLine.getValues()));
    }

    @Test
    public void addLines_invalidNoOfLines() {
        String ticketId = "unit-test-id-1";
        for (int noOfLines : new int[]{-4, -19, 0, -121, -9540000}) {
            try {
                ticketService.addLines(ticketId, noOfLines);
                Assert.fail("Expected ServiceException.");
            } catch (ServiceException e) {
                if (!(e.getExceptionEntity() instanceof InvalidInputExceptionEntity)) {
                    Assert.fail("Expected InvalidInputExceptionEntity.");
                }
            }
        }
    }

    @Test
    public void addLines_ticketIdNotFound() {
        String ticketId = "unit-test-id-1";
        try {
            Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(null);
            ticketService.addLines(ticketId, 2);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(ticketId);
    }

    @Test
    public void addLines_ticketIdNull() {
        try {
            Mockito.when(ticketObjectRepository.read(null)).thenReturn(null);
            ticketService.addLines(null, 2);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(null);
    }

    @Test
    public void addLines_ticketChecked() {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{1, 2, 1}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);
        ticket.setChecked(true);

        Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(ticket);

        try {
            ticketService.addLines(ticketId, 2);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ForbiddenOperationExceptionEntity)) {
                Assert.fail("Expected ForbiddenOperationExceptionEntity.");
            }
        }
    }

    @Test
    public void checkTicket_success() {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{0, 2, 0}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(ticket);
        Mockito.doAnswer(invocationOnMock -> {
            ((TicketLine)invocationOnMock.getArguments()[0]).setResult(10);
            return null;
        }).when(ticketLineRules).calculateTicketLineResult(Mockito.any(TicketLine.class));

        Ticket checked = ticketService.checkTicket(ticketId);

        Assert.assertNotNull(checked);
        Assert.assertEquals(ticketId, checked.getId());
        Assert.assertTrue(checked.isChecked());
        Assert.assertNotNull(checked.getLines());
        Assert.assertEquals(2, checked.getLines().size());

        // Verify the lines have not been corrupted.
        TicketLine  ticketLine =  checked.getLines().get(0);
        Assert.assertEquals(10, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLine.getValues()));

        ticketLine =  checked.getLines().get(1);
        Assert.assertEquals(10, ticketLine.getResult());
        Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLine.getValues()));
    }

    @Test
    public void checkTicket_ticketIdNotFound() {
        String ticketId = "unit-test-id-1";
        try {
            Mockito.when(ticketObjectRepository.read(ticketId)).thenReturn(null);
            ticketService.checkTicket(ticketId);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(ticketId);
    }

    @Test
    public void checkTicket_ticketIdNull() {
        try {
            Mockito.when(ticketObjectRepository.read(null)).thenReturn(null);
            ticketService.checkTicket(null);
            Assert.fail("Expected ServiceException.");
        } catch (ServiceException e) {
            if (!(e.getExceptionEntity() instanceof ItemNotFoundExceptionEntity)) {
                Assert.fail("Expected ItemNotFoundExceptionEntity.");
            }
        }
        Mockito.verify(ticketObjectRepository, Mockito.times(1)).read(null);
    }
}
