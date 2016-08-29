package com.kaz.lottery.web;

import com.kaz.lottery.dto.ErrorModel;
import com.kaz.lottery.dto.TicketDTO;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.service.TicketService;
import com.kaz.lottery.utils.EntityConverter;
import io.swagger.annotations.*;
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

    /**
     * This endpoint creates new Ticket.
     * @param noOfLines The number of lines.
     * @return The TicketDTO representing the newly created Ticket.
     */
    @RequestMapping(value = "/tickets", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "Create a new Ticket",
            notes = "This endpoint generates a new lottery ticket.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creation of a new Ticket.", response = TicketDTO.class),
            @ApiResponse(code = 422, message = "errorCode: 'INVALID_NUMBER_OF_LINES'. Invalid number of lines.", response = ErrorModel.class),
            @ApiResponse(code = 500, message = "errorCode: 'UNEXPECTED_ERROR'. Internal server error.", response = ErrorModel.class)
    })
    public TicketDTO createTicket(
            @ApiParam(name = "noOfLines", value = "Integer value greater than 0. Number of lines to generate on the Ticket.", required = false)
            @RequestParam(value = "noOfLines", required = false) Integer noOfLines) {
        LOGGER.info("Create a ticket, noOfLines = {}", noOfLines);

        if (noOfLines == null) {
            noOfLines = defaultNoOfLines;
        }

        Ticket ticket = getTicketService().createTicket(noOfLines);
        return entityConverter.convert(ticket);
    }


    /**
     * This endpoint adds lines to the existing Ticket.
     * @param ticketId The ticket id to add lines to.
     * @param noOfLines The number of lines to add.
     * @return The TicketDTO representing the amended Ticket.
     */
    @RequestMapping(value = "/tickets/{ticketId}/lines", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "Add lines to a Ticket.", notes = "This endpoint adds lines to an existing Ticket.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful addition of new lines to a Ticket.", response = TicketDTO.class),
            @ApiResponse(code = 403, message = "errorCode: 'OPERATION_NOT_ALLOWED'. Ticket amendments are not allowed any more.", response = ErrorModel.class),
            @ApiResponse(code = 404, message = "errorCode: 'ITEM_NOT_FOUND'. Ticket not found.", response = ErrorModel.class),
            @ApiResponse(code = 422, message = "errorCode: 'INVALID_NUMBER_OF_LINES'. Invalid number of lines.", response = ErrorModel.class),
            @ApiResponse(code = 500, message = "errorCode: 'UNEXPECTED_ERROR'. Internal server error.", response = ErrorModel.class)
    })
    public TicketDTO addLines(
            @ApiParam(name = "ticketId", value = "The Ticket id.", required = true)
            @PathVariable("ticketId") String ticketId,
            @ApiParam(name = "noOfLines", value = "Integer value greater than 0. Number of lines to generate on the Ticket.", required = true)
            @RequestParam(value = "noOfLines", required = true) Integer noOfLines) {
        LOGGER.info("Add lines to an existing ticket, ticketId = {}, noOfLines = {}", new Object[]{ticketId, noOfLines});

        Ticket ticket = getTicketService().addLines(ticketId, noOfLines);
        return entityConverter.convert(ticket);
    }


    /**
     * This endpoint performs a Ticket check.
     * @param ticketId The ticket id to check.
     * @return The TicketDTO representing the checked Ticket.
     */
    @RequestMapping(value = "/tickets/{ticketId}/check", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "Check a Ticket.", notes = "This endpoint checks the ticket and returns a result.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ticket has been successfully checked.", response = TicketDTO.class),
            @ApiResponse(code = 404, message = "errorCode: 'ITEM_NOT_FOUND'. Ticket not found.", response = ErrorModel.class),
            @ApiResponse(code = 500, message = "errorCode: 'UNEXPECTED_ERROR'. Internal server error.", response = ErrorModel.class)
    })
    public TicketDTO checkTicket(
            @ApiParam(name = "ticketId", value = "The Ticket id.", required = true)
            @PathVariable("ticketId") String ticketId) {
        LOGGER.info("Check a ticket, ticketId = {}", ticketId);

        Ticket ticket = getTicketService().checkTicket(ticketId);
        return entityConverter.convert(ticket);
    }


    /**
     * This endpoint reads the existing Ticket.
     * @param ticketId The ticket id to read.
     * @return The TicketDTO representing the read Ticket.
     */
    @RequestMapping(value = "/tickets/{ticketId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "Read a Ticket",notes = "This endpoint reads a Ticket by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ticket has been successfully read.", response = TicketDTO.class),
            @ApiResponse(code = 404, message = "errorCode: 'ITEM_NOT_FOUND'. Ticket not found.", response = ErrorModel.class),
            @ApiResponse(code = 500, message = "errorCode: 'UNEXPECTED_ERROR'. Internal server error.", response = ErrorModel.class)
    })
    public TicketDTO readTicket(
            @ApiParam(name = "ticketId", value = "The Ticket id.", required = true)
            @PathVariable("ticketId") String ticketId) {
        LOGGER.info("Get a Ticket by Id = {}", ticketId);

        Ticket ticket= getTicketService().readTicket(ticketId);
        return entityConverter.convert(ticket);
    }


    private TicketService getTicketService() {
        return ticketService;
    }
}