package blockingQueueMethods;

public class Vendor implements Runnable {

    private TicketPoolBlocking ticketPool;
    private String vendorName;
    private int ticketLimit;
    private volatile boolean running = true;

    public Vendor(TicketPoolBlocking ticketPool, String vendorName, int ticketLimit) {
        this.ticketPool = ticketPool;
        this.vendorName = vendorName;
        this.ticketLimit = ticketLimit;
    }

    @Override
    public void run() {
        for (int i = 1; i <= ticketLimit && running && !Thread.currentThread().isInterrupted(); i++) {
            Ticket ticket = new Ticket("" + i, vendorName, "Concert Event");
            ticketPool.addTicket(ticket);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " exiting Vendor after producing tickets.");
    }

    public void stop() {
        running = false;
    }
}
