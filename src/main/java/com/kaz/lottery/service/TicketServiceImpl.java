package com.kaz.lottery.service;

import com.kaz.lottery.persistence.TicketObjectRepository;
import com.kaz.lottery.rules.TicketLineRules;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * This is an implementation of a TicketService.
 */
@Service("ticketService")
public class TicketServiceImpl extends AbstractTicketService {

    @Resource(name = "ticketInMemoryObjectRepository")
    private TicketObjectRepository ticketInMemoryObjectRepository;

    @Resource(name = "sum2WinsTicketLineRules")
    private TicketLineRules sum2WinsTicketLineRules;

    public TicketObjectRepository getTicketObjectRepository() {
        return ticketInMemoryObjectRepository;
    }

    @Override
    public TicketLineRules getTicketLineRules() {
        return sum2WinsTicketLineRules;
    }
}
