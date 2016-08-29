package com.kaz.lottery.persistence;

import com.kaz.lottery.model.Ticket;
import org.springframework.stereotype.Repository;

/**
 * This is an implementation of Ticket object repository which is only an in-memory storage.
 */
@Repository("ticketInMemoryObjectRepository")
public class TicketInMemoryObjectRepository extends AbstractInMemoryObjectRepository<Ticket> implements TicketObjectRepository {

}
