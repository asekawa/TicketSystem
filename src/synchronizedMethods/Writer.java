package synchronizedMethods;

public class Writer implements Runnable {

	private TicketPoolSync ticketPool;
	private int specialTicketLimit;
	private volatile boolean running = true;

	public Writer(TicketPoolSync ticketPool, int specialTicketLimit) {
		this.ticketPool = ticketPool;
		this.specialTicketLimit = specialTicketLimit;
	}

	@Override
	public void run() {
		int specialCounter = 1;
		String writerName = Thread.currentThread().getName();

		while (specialCounter <= specialTicketLimit && running && !Thread.currentThread().isInterrupted()) {
			Ticket specialTicket = new Ticket("" + specialCounter, writerName, "Special Event");
			ticketPool.addTicket(specialTicket);
			specialCounter++;

			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println(writerName + " exiting Writer after producing special tickets.");
	}

	public void stop() {
		running = false;
	}
}
