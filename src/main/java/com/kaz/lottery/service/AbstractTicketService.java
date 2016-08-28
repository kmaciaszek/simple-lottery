package com.kaz.lottery.service;

import com.kaz.lottery.exceptions.ExceptionCode;
import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.exceptions.entity.ForbiddenOperationExceptionEntity;
import com.kaz.lottery.exceptions.entity.InvalidInputExceptionEntity;
import com.kaz.lottery.exceptions.entity.ItemNotFoundExceptionEntity;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import com.kaz.lottery.persistence.TicketObjectRepository;
import com.kaz.lottery.rules.TicketLineRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a base class for all implementations of TicketService.
 */
public abstract class AbstractTicketService implements TicketService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractTicketService.class);

    @Override
    public Ticket createTicket(int noOfLines) {
        // Make sure the noOfLines is greater than 0.
        validateTicketInput(noOfLines);
        Ticket ticket = new Ticket();
        ticket.setLines(getTicketLineRules().randomTicketLines(noOfLines));
        return getTicketObjectRepository().create(ticket);
    }

    @Override
    public Ticket readTicket(String ticketId) throws ServiceException {
        Ticket ticket = getTicketObjectRepository().read(ticketId);
        if (ticket == null) {
             throw new ServiceException(new ItemNotFoundExceptionEntity(ExceptionCode.ITEM_NOT_FOUND,
                    "The Ticket with given id = " + ticketId + " has not been found."));
        }
        getTicketLineRules().sortTicketLines(ticket.getLines());
        return ticket;
    }

    @Override
    public Ticket addLines(String ticketId, int noOfLines) throws ServiceException {
        validateTicketInput(noOfLines);
        Ticket ticket = readTicket(ticketId);
        if (ticket.isChecked()) {
            throw new ServiceException(new ForbiddenOperationExceptionEntity(ExceptionCode.OPERATION_NOT_ALLOWED,
                    "The Ticket has already been checked and amendments are not allowed any more"));
        }
        ticket.addLines(getTicketLineRules().randomTicketLines(noOfLines));
        return getTicketObjectRepository().update(ticket);
    }

    @Override
    public Ticket checkTicket(String ticketId) {
        Ticket ticket = readTicket(ticketId);
        for (TicketLine ticketLine : ticket.getLines()) {
            getTicketLineRules().calculateTicketLineResult(ticketLine);
        }
        ticket.setChecked(true);
        getTicketObjectRepository().update(ticket);
        return readTicket(ticketId);
    }

    /**
     * This method validates the Ticket input.
     * @param noOfLines The number of lines.
     */
    private void validateTicketInput(int noOfLines) {
        if (noOfLines <= 0) {
            throw new ServiceException(new InvalidInputExceptionEntity(ExceptionCode.INVALID_NUMBER_OF_LINES,
                    "The number of list must be greater than 0.",
                    "To successfully randomTicketLine a ticket the number of lines has to be greater then 0." +
                            " Please ensure the number of lines is greater than 0 in your request."));
        }
    }

    protected abstract TicketObjectRepository getTicketObjectRepository();

    protected abstract TicketLineRules getTicketLineRules();
}
