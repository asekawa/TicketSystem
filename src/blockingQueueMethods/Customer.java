package blockingQueueMethods;

public class Customer implements Runnable {

    private TicketPoolBlocking ticketPool;
    private volatile boolean running = true;

    public Customer(TicketPoolBlocking ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            Ticket ticket = ticketPool.purchaseTicket();
            if (ticket != null) {
                System.out.println(Thread.currentThread().getName() + " purchased TicketID: " + ticket.getTicketID() +
                        " from " + ticket.getVendorName() + " for " + ticket.getEvent());
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
