package blockingQueueMethods;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketPoolBlocking {

    private BlockingQueue<Ticket> ticketQueue;
    private int offered = 0;
    private int purchased = 0;

    public TicketPoolBlocking(int capacity) {
        ticketQueue = new LinkedBlockingQueue<>(capacity);
    }

    public void addTicket(Ticket ticket) {
        try {
            ticketQueue.put(ticket);
            offered++;
            System.out.println(Thread.currentThread().getName() + " added TicketID: " + ticket.getTicketID() + " for the Concert Event");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Ticket purchaseTicket() {
        try {
            Ticket ticket = ticketQueue.take();
            purchased++;
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public int availableTickets() {
        return ticketQueue.size();
    }

    public boolean isEmpty() {
        return ticketQueue.isEmpty();
    }

    public void printTicketPoolStatus() {
        System.out.println("Tickets added: " + offered + ", Tickets bought: " + purchased + ", Available: " + ticketQueue.size());
    }
}

