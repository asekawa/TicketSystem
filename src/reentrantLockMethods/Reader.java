package reentrantLockMethods;

public class Reader implements Runnable {

    private TicketPoolLock ticketPool;
    private volatile boolean running = true;

    public Reader(TicketPoolLock ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            ticketPool.printTicketPoolStatus();

            if (ticketPool.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " Ticket pool empty. Exiting Reader.");
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
