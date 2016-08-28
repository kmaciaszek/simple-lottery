package com.kaz.lottery.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaz.lottery.dto.TicketDTO;
import com.kaz.lottery.dto.TicketLineDTO;
import com.kaz.lottery.exceptions.ExceptionCode;
import com.kaz.lottery.exceptions.RestResponseEntityExceptionHandler;
import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.exceptions.entity.ForbiddenOperationExceptionEntity;
import com.kaz.lottery.exceptions.entity.GenericExceptionEntity;
import com.kaz.lottery.exceptions.entity.InvalidInputExceptionEntity;
import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import com.kaz.lottery.service.TicketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaz on 28/08/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Resource
    private TicketController ticketController;

    @Resource
    private ObjectMapper objectMapper;

    @Value("${ticket.defaultNoOfLines}")
    private int defaultNoOfLines;

    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(ticketController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
        Whitebox.setInternalState(ticketController, "ticketService", ticketService);
    }

    @Test
    public void createTicket_defaultNoOfLines() throws Exception {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{0, 2, 0}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketService.createTicket(defaultNoOfLines)).thenReturn(ticket);

        String path = "/v1/tickets";

        mvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(mvcResult -> {
            TicketDTO ticketDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TicketDTO.class);
            Assert.assertNotNull(ticketDTO);
            Assert.assertEquals(ticketId, ticketDTO.getId());
            Assert.assertFalse(ticketDTO.isChecked());
            Assert.assertNotNull(ticketDTO.getLines());
            Assert.assertEquals(2, ticketDTO.getLines().size());

            TicketLineDTO ticketLineDTO = ticketDTO.getLines().get(0);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

            ticketLineDTO = ticketDTO.getLines().get(1);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));
        });

        Mockito.verify(ticketService, Mockito.times(1)).createTicket(defaultNoOfLines);
    }

    @Test
    public void createTicket_givenNoOfLines() throws Exception {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{0, 2, 0}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        lines.add(new TicketLine(new int[]{0, 1, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketService.createTicket(3)).thenReturn(ticket);

        String path = "/v1/tickets?noOfLines=3";

        mvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(mvcResult -> {
            TicketDTO ticketDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TicketDTO.class);
            Assert.assertNotNull(ticketDTO);
            Assert.assertEquals(ticketId, ticketDTO.getId());
            Assert.assertFalse(ticketDTO.isChecked());
            Assert.assertNotNull(ticketDTO.getLines());
            Assert.assertEquals(3, ticketDTO.getLines().size());

            TicketLineDTO ticketLineDTO = ticketDTO.getLines().get(0);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

            ticketLineDTO = ticketDTO.getLines().get(1);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));

            ticketLineDTO = ticketDTO.getLines().get(2);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{0, 1, 1}, ticketLineDTO.getValues()));
        });

        Mockito.verify(ticketService, Mockito.times(1)).createTicket(3);
    }

    @Test
    public void addLines_add2() throws Exception {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{0, 2, 0}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketService.addLines(ticketId, 2)).thenReturn(ticket);

        String path = "/v1/tickets/" + ticketId + "/lines?noOfLines=2";

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(mvcResult -> {
            TicketDTO ticketDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TicketDTO.class);
            Assert.assertNotNull(ticketDTO);
            Assert.assertEquals(ticketId, ticketDTO.getId());
            Assert.assertFalse(ticketDTO.isChecked());
            Assert.assertNotNull(ticketDTO.getLines());
            Assert.assertEquals(2, ticketDTO.getLines().size());

            TicketLineDTO ticketLineDTO = ticketDTO.getLines().get(0);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

            ticketLineDTO = ticketDTO.getLines().get(1);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));
        });

        Mockito.verify(ticketService, Mockito.times(1)).addLines(ticketId, 2);
    }

    @Test
    public void addLines_missingNoOfLines() throws Exception {

        String ticketId = "unit-test-id-1";

        String path = "/v1/tickets/" + ticketId + "/lines";

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verifyNoMoreInteractions(ticketService);
    }

    @Test
    public void addLines_invalidNoOfLines() throws Exception {

        String ticketId = "unit-test-id-1";
        int noOfLines = -309;

        Mockito.when(ticketService.addLines(ticketId, noOfLines)).thenThrow(
                new ServiceException(new InvalidInputExceptionEntity(ExceptionCode.INVALID_NUMBER_OF_LINES, "error message", "error details"))
        );

        String path = "/v1/tickets/" + ticketId + "/lines?noOfLines=" + noOfLines;

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity()).andDo(mvcResult -> {
            GenericExceptionEntity serviceException =
                    objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenericExceptionEntity.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(ExceptionCode.INVALID_NUMBER_OF_LINES.toString(), serviceException.getErrorCode());
            Assert.assertEquals("error message", serviceException.getError());
            Assert.assertEquals("error details", serviceException.getErrorDetail());
        });

        Mockito.verify(ticketService, Mockito.times(1)).addLines(ticketId, noOfLines);
    }

    @Test
    public void addLines_ticketIdNotFound() throws Exception {

        String ticketId = "unit-test-id-1";
        int noOfLines = 2;

        Mockito.when(ticketService.addLines(ticketId, noOfLines)).thenThrow(
                new ServiceException(new ItemNotFoundExceptionEntity(ExceptionCode.ITEM_NOT_FOUND, "error message", "error details"))
        );

        String path = "/v1/tickets/" + ticketId + "/lines?noOfLines=" + noOfLines;

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(mvcResult -> {
            GenericExceptionEntity serviceException =
                    objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenericExceptionEntity.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(ExceptionCode.ITEM_NOT_FOUND.toString(), serviceException.getErrorCode());
            Assert.assertEquals("error message", serviceException.getError());
            Assert.assertEquals("error details", serviceException.getErrorDetail());
        });

        Mockito.verify(ticketService, Mockito.times(1)).addLines(ticketId, noOfLines);
    }

    @Test
    public void addLines_ticketAlreadyChecked() throws Exception {

        String ticketId = "unit-test-id-1";
        int noOfLines = 2;

        Mockito.when(ticketService.addLines(ticketId, noOfLines)).thenThrow(
                new ServiceException(new ForbiddenOperationExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED, "error message", "error details"))
        );

        String path = "/v1/tickets/" + ticketId + "/lines?noOfLines=" + noOfLines;

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden()).andDo(mvcResult -> {
            GenericExceptionEntity serviceException =
                    objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenericExceptionEntity.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(ExceptionCode.OPERATION_NOT_ALLOWED.toString(), serviceException.getErrorCode());
            Assert.assertEquals("error message", serviceException.getError());
            Assert.assertEquals("error details", serviceException.getErrorDetail());
        });

        Mockito.verify(ticketService, Mockito.times(1)).addLines(ticketId, noOfLines);
    }

    @Test
    public void checkTicket_success() throws Exception {
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

        Mockito.when(ticketService.checkTicket(ticketId)).thenReturn(ticket);

        String path = "/v1/tickets/" + ticketId + "/check";

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(mvcResult -> {
            TicketDTO ticketDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TicketDTO.class);
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
        });

        Mockito.verify(ticketService, Mockito.times(1)).checkTicket(ticketId);
    }

    @Test
    public void checkTicket_ticketIdNotFound() throws Exception {

        String ticketId = "unit-test-id-1";
        int noOfLines = 2;

        Mockito.when(ticketService.checkTicket(ticketId)).thenThrow(
                new ServiceException(new ItemNotFoundExceptionEntity(ExceptionCode.ITEM_NOT_FOUND, "error message", "error details"))
        );

        String path = "/v1/tickets/" + ticketId + "/check";

        mvc.perform(MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(mvcResult -> {
            GenericExceptionEntity serviceException =
                    objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenericExceptionEntity.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(ExceptionCode.ITEM_NOT_FOUND.toString(), serviceException.getErrorCode());
            Assert.assertEquals("error message", serviceException.getError());
            Assert.assertEquals("error details", serviceException.getErrorDetail());
        });

        Mockito.verify(ticketService, Mockito.times(1)).checkTicket(ticketId);
    }

    @Test
    public void readTicket_success() throws Exception {
        Ticket ticket = new Ticket();
        String ticketId = "unit-test-id-1";
        ticket.setId(ticketId);
        List<TicketLine> lines = new ArrayList<>();
        lines.add(new TicketLine(new int[]{0, 2, 0}));
        lines.add(new TicketLine(new int[]{1, 0, 1}));
        ticket.setLines(lines);

        Mockito.when(ticketService.readTicket(ticketId)).thenReturn(ticket);

        String path = "/v1/tickets/" + ticketId;

        mvc.perform(MockMvcRequestBuilders.get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(mvcResult -> {
            TicketDTO ticketDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TicketDTO.class);
            Assert.assertNotNull(ticketDTO);
            Assert.assertEquals(ticketId, ticketDTO.getId());
            Assert.assertFalse(ticketDTO.isChecked());
            Assert.assertNotNull(ticketDTO.getLines());
            Assert.assertEquals(2, ticketDTO.getLines().size());

            TicketLineDTO ticketLineDTO = ticketDTO.getLines().get(0);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{0, 2, 0}, ticketLineDTO.getValues()));

            ticketLineDTO = ticketDTO.getLines().get(1);
            Assert.assertEquals(0, ticketLineDTO.getResult());
            Assert.assertTrue(Arrays.equals(new int[]{1, 0, 1}, ticketLineDTO.getValues()));
        });

        Mockito.verify(ticketService, Mockito.times(1)).readTicket(ticketId);
    }

    @Test
    public void readTicket_ticketIdNotFound() throws Exception {

        String ticketId = "unit-test-id-1";

        Mockito.when(ticketService.readTicket(ticketId)).thenThrow(
                new ServiceException(new ItemNotFoundExceptionEntity(ExceptionCode.ITEM_NOT_FOUND, "error message", "error details"))
        );

        String path = "/v1/tickets/" + ticketId;

        mvc.perform(MockMvcRequestBuilders.get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(mvcResult -> {
            GenericExceptionEntity serviceException =
                    objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenericExceptionEntity.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(ExceptionCode.ITEM_NOT_FOUND.toString(), serviceException.getErrorCode());
            Assert.assertEquals("error message", serviceException.getError());
            Assert.assertEquals("error details", serviceException.getErrorDetail());
        });

        Mockito.verify(ticketService, Mockito.times(1)).readTicket(ticketId);
    }
}
