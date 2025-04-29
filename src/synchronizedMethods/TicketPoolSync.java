package synchronizedMethods;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPoolSync {

	private Queue<Ticket> ticketQueue = new LinkedList<>();
	private int maxSize;
	private int noOfTicketsOffered = 0;
	private int noOfTicketsBought = 0;

	public TicketPoolSync(int maxSize) {
		this.maxSize = maxSize;
	}

	public synchronized void addTicket(Ticket ticket) {
		while (ticketQueue.size() == maxSize) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		ticketQueue.offer(ticket);
		noOfTicketsOffered++;
		System.out.println(Thread.currentThread().getName() + " added TicketID: " + ticket.getTicketID()
				+ " for the Concert Event");
		notifyAll();
	}

	public synchronized Ticket purchaseTicket() {
		while (ticketQueue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		Ticket ticket = ticketQueue.poll();
		noOfTicketsBought++;
		notifyAll();
		return ticket;
	}

	public synchronized void printTicketPoolStatus() {
		System.out.println("Tickets added: " + noOfTicketsOffered + ", Tickets bought: " + noOfTicketsBought
				+ ", Available: " + ticketQueue.size());
	}

	public synchronized boolean isEmpty() {
		return ticketQueue.isEmpty();
	}

	public Integer availableTickets() {
		return ticketQueue.size();
	}
}
