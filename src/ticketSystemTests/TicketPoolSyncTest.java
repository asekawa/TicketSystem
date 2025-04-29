package ticketSystemTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import synchronizedMethods.TicketPoolSync;
import synchronizedMethods.Ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketPoolSyncTest {

	TicketPoolSync ticketPool;

	@BeforeEach
	void setup() {
		ticketPool = new TicketPoolSync(3);
	}

	@Test
	void testAddAndPurchaseTicket() {
		Ticket t = new Ticket("1", "Vendor-1", "Concert");
		ticketPool.addTicket(t);
		Ticket purchased = ticketPool.purchaseTicket();
		assertEquals("1", purchased.getTicketID());
	}

	@Test
	void testCapacityLimit() {
		ticketPool.addTicket(new Ticket("1", "V1", "E1"));
		ticketPool.addTicket(new Ticket("2", "V2", "E2"));
		ticketPool.addTicket(new Ticket("3", "V3", "E3"));
		assertEquals(3, ticketPool.availableTickets());
	}

	@Test
	void testQueueIsEmptyInitially() {
		assertTrue(ticketPool.isEmpty());
	}

	@Test
	void testQueueEmptyAfterPurchase() {
		ticketPool.addTicket(new Ticket("1", "V1", "E1"));
		ticketPool.purchaseTicket();
		assertTrue(ticketPool.isEmpty());
	}

	@Test
	void testTicketOrder() {
		ticketPool.addTicket(new Ticket("1", "V1", "E1"));
		ticketPool.addTicket(new Ticket("2", "V2", "E2"));
		assertEquals("1", ticketPool.purchaseTicket().getTicketID());
		assertEquals("2", ticketPool.purchaseTicket().getTicketID());
	}
}
