package com.kaz.lottery.utils;

import com.kaz.lottery.dto.TicketDTO;
import com.kaz.lottery.dto.TicketLineDTO;
import com.kaz.lottery.model.Ticket;
import com.kaz.lottery.model.TicketLine;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class converting objects between a Model and DTO and vice versa.
 */
@Component
public class EntityConverter {

    /**
     * This method converts Ticket into TicketDTO.
     * @param ticket The Ticket object to convert to TicketDTO.
     * @return The TicketDTO representation of given Ticket.
     */
    public TicketDTO convert(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO, "lines");
        ticketDTO.setLines(convert(ticket.getLines()));
        return ticketDTO;
    }

    /**
     * This method converts list of TicketLine objects into list of TicketLineDTO objects.
     * @param lines The list of TicketLine objects to convert to list TicketLineDTO objects.
     * @return The list of TicketLineDTO object representation of given TicketLine list.
     */
    public List<TicketLineDTO> convert(List<TicketLine> lines) {
        if (lines == null) {
            return null;
        }
        List<TicketLineDTO> linesDTO = new ArrayList<>();
        for (TicketLine ticketLine : lines) {
            TicketLineDTO ticketLineDTO = new TicketLineDTO();
            BeanUtils.copyProperties(ticketLine, ticketLineDTO);
            linesDTO.add(ticketLineDTO);
        }
        return linesDTO;
    }
}
