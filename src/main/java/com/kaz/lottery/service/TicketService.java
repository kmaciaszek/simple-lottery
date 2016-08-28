package com.kaz.lottery.service;

import com.kaz.lottery.exceptions.ServiceException;
import com.kaz.lottery.model.Ticket;

/**
 * This is a common interface defining all operations on a Ticket.
 */
public interface TicketService {

    /**
     * This method creates/generates a new Ticket.
     * @param noOfLines The number of lines on the ticket.
     * @return The newely generated ticket.
     */
    public Ticket createTicket(int noOfLines);

    /**
     * This method reads a ticket by Id and returns it if found, otherwise throws a ServiceException.
     * @param ticketId The ticket Id.
     * @return The Ticket matching given Id.
     */
    public Ticket readTicket(String ticketId) throws ServiceException;

    /**
     * This method adds more lines to the ticket if the ticked is not marked as checked, otherwise throws a ServiceException
     * @param ticketId The ticket Id to which the lines are being added.
     * @param noOfLines The number of lines to add.
     * @return The updated Ticket.
     */
    public Ticket addLines(String ticketId, int noOfLines) throws ServiceException;

    /**
     * This method checks the ticket and returns values for each line. After that operation lines cannot be added
     * to the Ticket.
     * @param ticketId The Ticket id.
     * @return The checked Ticket.
     */
    public Ticket checkTicket(String ticketId);

}
