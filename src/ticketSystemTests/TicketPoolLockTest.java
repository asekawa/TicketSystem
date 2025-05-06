package ticketSystemTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import reentrantLockMethods.TicketPoolLock;
import reentrantLockMethods.Ticket;



public class TicketPoolLockTest {

    TicketPoolLock ticketPool;

    @BeforeEach
    void setup() {
        ticketPool = new TicketPoolLock(3);
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
    void testTicketOrder() {
        ticketPool.addTicket(new Ticket("1", "V1", "E1"));
        ticketPool.addTicket(new Ticket("2", "V2", "E2"));
        assertEquals("1", ticketPool.purchaseTicket().getTicketID());
        assertEquals("2", ticketPool.purchaseTicket().getTicketID());
    }

    @Test
    void testEmptyAtStart() {
        assertEquals(0, ticketPool.availableTickets());
    }

    @Test
    void testEmptyAfterConsumption() {
        ticketPool.addTicket(new Ticket("1", "V1", "E1"));
        ticketPool.purchaseTicket();
        assertEquals(0, ticketPool.availableTickets());
    }
    
    @Test
	void testConcurrentAccess() throws InterruptedException {
	    Thread producer = new Thread(() -> {
	        for (int i = 0; i < 3; i++) {
	            ticketPool.addTicket(new Ticket("" + i, "Vendor", "Concert"));
	        }
	    });

	    Thread consumer = new Thread(() -> {
	        for (int i = 0; i < 3; i++) {
	            ticketPool.purchaseTicket();
	        }
	    });

	    producer.start();
	    consumer.start();

	    producer.join();
	    consumer.join();

	    assertTrue(ticketPool.isEmpty());
	}
}
