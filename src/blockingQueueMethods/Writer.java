package blockingQueueMethods;

public class Writer implements Runnable {

    private TicketPoolBlocking ticketPool;
    private int ticketLimit;
    private volatile boolean running = true;

    public Writer(TicketPoolBlocking ticketPool, int ticketLimit) {
        this.ticketPool = ticketPool;
        this.ticketLimit = ticketLimit;
    }

    @Override
    public void run() {
        for (int i = 1; i <= ticketLimit && running && !Thread.currentThread().isInterrupted(); i++) {
            Ticket special = new Ticket("-" + i, Thread.currentThread().getName(), "Special Event");
            ticketPool.addTicket(special);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " exiting Writer after producing special tickets.");
    }

    public void stop() {
        running = false;
    }
}
