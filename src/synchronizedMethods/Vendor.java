package synchronizedMethods;

public class Vendor implements Runnable {

	private TicketPoolSync ticketPool;
	private String vendorName;
	private int ticketLimit;
	private volatile boolean running = true;

	public Vendor(TicketPoolSync ticketPool, String vendorName, int ticketLimit) {
		this.ticketPool = ticketPool;
		this.vendorName = vendorName;
		this.ticketLimit = ticketLimit;
	}

	@Override
	public void run() {
		int ticketNumber = 1;
		while (ticketNumber <= ticketLimit && running && !Thread.currentThread().isInterrupted()) {
			Ticket ticket = new Ticket("" + ticketNumber, vendorName, "Concert Event");

			ticketPool.addTicket(ticket);
			ticketNumber++;

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
