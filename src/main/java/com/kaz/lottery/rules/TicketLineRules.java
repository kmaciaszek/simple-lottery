package com.kaz.lottery.rules;

import com.kaz.lottery.model.TicketLine;

import java.util.List;

/**
 * This is a common interface for all rules implementations.
 */
public interface TicketLineRules {

    /**
     * This method randomly generates a new TicketLine.
     * @return The generated TicketLine.
     */
    public TicketLine randomTicketLine();

    /**
     * This method randomly generates number of new TicketLine.
     * @return The generated list of TicketLines.
     */
    public List<TicketLine> randomTicketLines(int noOfLines);

    /**
     * This method applies a specific rules to the line and calculates the result value.
     * @param ticketLine The TicketLine for calculating the result.
     */
    public void calculateTicketLineResult(TicketLine ticketLine);

    /**
     * This method sorts the lines.
     * @param lines The lines to sortTicketLines.
     */
    public void sortTicketLines(List<TicketLine> lines);
}
