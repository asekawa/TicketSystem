package ticketSystemTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import blockingQueueMethods.TicketPoolBlocking;
import blockingQueueMethods.Ticket;


public class TicketPoolBlockingTest {

    TicketPoolBlocking ticketPool;

    @BeforeEach
    void setup() {
        ticketPool = new TicketPoolBlocking(3);
    }

    @Test
    void testAddAndTakeTicket() throws InterruptedException {
        Ticket t = new Ticket("1", "Vendor-1", "Concert");
        ticketPool.addTicket(t);
        Ticket taken = ticketPool.purchaseTicket();
        assertEquals("1", taken.getTicketID());
    }

    @Test
    void testCapacityHandling() throws InterruptedException {
        ticketPool.addTicket(new Ticket("1", "V1", "E1"));
        ticketPool.addTicket(new Ticket("2", "V2", "E2"));
        ticketPool.addTicket(new Ticket("3", "V3", "E3"));
        assertEquals(3, ticketPool.availableTickets());
    }

    @Test
    void testFIFOOrder() throws InterruptedException {
        ticketPool.addTicket(new Ticket("1", "V1", "E1"));
        ticketPool.addTicket(new Ticket("2", "V2", "E2"));
        assertEquals("1", ticketPool.purchaseTicket().getTicketID());
        assertEquals("2", ticketPool.purchaseTicket().getTicketID());
    }

    @Test
    void testQueueEmptyInitially() {
        assertEquals(0, ticketPool.availableTickets());
    }

    @Test
    void testQueueEmptyAfterTake() throws InterruptedException {
        ticketPool.addTicket(new Ticket("1", "V1", "E1"));
        ticketPool.purchaseTicket();
        assertEquals(0, ticketPool.availableTickets());
    }
}

