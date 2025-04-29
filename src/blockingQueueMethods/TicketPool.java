package blockingQueueMethods;

public interface TicketPool {
    void addTicket(Ticket ticket);
    Ticket purchaseTicket();
    int availableTickets();
    void printTicketPoolStatus(); 
}
