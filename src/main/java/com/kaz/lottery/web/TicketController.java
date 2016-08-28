package com.kaz.lottery.web;

import com.kaz.lottery.dto.TicketDTO;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.service.TicketService;
import com.kaz.lottery.utils.EntityConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * This is a controller defining all API endpoints for a Ticket resource.
 */
@Api(value = "Ticket API")
@RestController
@RequestMapping("/v1")
public class TicketController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Value("${ticket.defaultNoOfLines}")
    private int defaultNoOfLines;

    @Resource(name = "ticketService")
    private TicketService ticketService;
    @Resource
    private EntityConverter entityConverter;

    @RequestMapping(value = "/tickets", method = RequestMethod.POST, produces = "application/json; charset=UTF-8",
            consumes = "application/json")
    @ApiOperation(value = "This endpoint generates a new lottery ticket. Request parameters:" +
            "<br>- noOfLines (optional): if not provided then the default values is 2." +
            "<br>Errors:" +
            "<br>- 422 and errorCode = 'INVALID_NUMBER_OF_LINES' : The noOfLines parameter is <= 0.", notes = "")
    public TicketDTO createTicket(@RequestParam(value = "noOfLines", required = false) Integer noOfLines) {
        LOGGER.info("Create a ticket, noOfLines = {}", noOfLines);

        if (noOfLines == null) {
            noOfLines = defaultNoOfLines;
        }

        Ticket ticket = getTicketService().createTicket(noOfLines);
        return entityConverter.convert(ticket);
    }

    @RequestMapping(value = "/tickets/{ticketId}/lines", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8",
            consumes = "application/json")
    @ApiOperation(value = "This add lines to an existing lottery ticket.", notes = "")
    public TicketDTO addLines(@PathVariable("ticketId") String ticketId,
                              @RequestParam(value = "noOfLines", required = true) Integer noOfLines) {
        LOGGER.info("Add lines to an existing ticket, ticketId = {}, noOfLines = {}", new Object[]{ticketId, noOfLines});

        Ticket ticket = getTicketService().addLines(ticketId, noOfLines);
        return entityConverter.convert(ticket);
    }

    @RequestMapping(value = "/tickets/{ticketId}/check", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8",
            consumes = "application/json")
    @ApiOperation(value = "This checks an existing lottery ticket.", notes = "")
    public TicketDTO checkTicket(@PathVariable("ticketId") String ticketId) {
        LOGGER.info("Check a ticket, ticketId = {}", ticketId);

        Ticket ticket = getTicketService().checkTicket(ticketId);
        return entityConverter.convert(ticket);
    }

    @RequestMapping(value = "/tickets/{ticketId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "Gets a Ticket by Id",notes = "")
    public TicketDTO readTicket(@PathVariable("ticketId") String ticketId) {
        LOGGER.info("Get a Ticket by Id = {}", ticketId);

        Ticket ticket= getTicketService().readTicket(ticketId);
        return entityConverter.convert(ticket);
    }

    private TicketService getTicketService() {
        return ticketService;
    }
}